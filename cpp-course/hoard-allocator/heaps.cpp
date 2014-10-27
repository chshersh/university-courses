#include "heaps.h"
#include "tracing.h"

#include <cassert>
#include <sys/mman.h>
#include <string.h>

const size_t totalBlockSize = s + sizeof (SuperNode) + sizeof (SuperBlock);

// GlobalHeap

GlobalHeap::GlobalHeap() { // заполнение массива последовательных чисел для последующего копирования в стеки
    for (size_t i = 0; i < s / size_classes[0]; ++i)
        consecutive[i] = i;
}

void initSuperBlock(SuperBlock* sb, size_t blockSize) {
    sb->stackHead = sb->blocksInUse = 0;
    sb->blockSize = blockSize;
}

SuperNode* GlobalHeap::getNewSuperNode(size_t blockSize, size_t ind, size_t fromHeadIndex) {
    mtx.lock(); // в этот момент суперблок может освобождаться из глобальной кучи, поэтому надо лочить

    SuperNode* sn;
    if (head) { // в глобальной куче есть пустой суперлок, вернем тот, что в голове
        sn = head;
        head = head->next;
        if (head) head->prev = nullptr;
        initSuperBlock(sn->data, blockSize); // перемещаем голову стека суперблока в начало
    } else if (emptyHeads[ind]) { // есть есть почти пустой блок и с таким же разбиением, то вернём его
        sn = emptyHeads[ind];
        emptyHeads[ind] = emptyHeads[ind]->next;
        if (sn->next) sn->next->prev = nullptr;
        // здесь инициализировать суперблок не надо, потому что он уже имеет нетривиальное разбиение
    } else { // если в глобальной куче нет суперблоков, создаем новый: s под указатели на суперод, s на сами данные, суперблок, супернода
        void* mem = mmap(NULL, totalBlockSize, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
        assert(mem != nullptr && "Have no memory\n");

        SuperBlock* sb = (SuperBlock*) ((char*) mem + s + sizeof (SuperNode));
        sb->blocks = mem;
        sb->freeSize = s;

        sn = (SuperNode*) ((char*) mem + s);
        sn->data = sb;
        sn->magic = DARK_MAGIC;
        initSuperBlock(sn->data, blockSize); // тут снова надо проинициализировать суперблок
    }
    sn->next = sn->prev = nullptr;
    sn->heapIndex = fromHeadIndex;

    mtx.unlock();
    return sn;
}

void GlobalHeap::addSuperNode(SuperNode* sn, size_t ind) { // добавление супернода в голову глобальной кучи
    mtx.lock();

    sn->prev = nullptr;
    if (sn->data->freeSize == s) { // если суперблок пустой, то добавляем в отдельный список head
        sn->next = head;
        if (head) head->prev = sn;
        head = sn;
    } else { // иначе добавляем в соответствующий список с разбиением
        sn->next = emptyHeads[ind];
        if (emptyHeads[ind]) emptyHeads[ind]->prev = sn;
        emptyHeads[ind] = sn;
    }
    sn->heapIndex = (size_t) - 1;

    mtx.unlock();
}

void GlobalHeap::addNewHeap(std::thread::id threadID) { // добавление кучи процесса/потока
    mtx.lock();

    processHeaps[procNum].heapNum = procNum;
    processHeaps[procNum].id = threadID;
    procNum++;

    mtx.unlock();
}

SuperNode* GlobalHeap::nodeByPtr(void* ptr) {
    size_t nodeStart = ((size_t) ptr + s) / s * s;
    SuperNode* node = (SuperNode*) nodeStart;
    return node;
}

//ProcessHeap

std::thread::id ProcessHeap::getID() const {
    return id;
}

void ProcessHeap::createSuperNode(size_t ind, size_t newsz) { // если в куче потока нет суперблоков, то возьмем из глобальной кучи
    SuperNode* sn = globalHeap.getNewSuperNode(newsz, ind, heapNum);
    superEmpty[ind] = sn;
    freeBlocks[ind]++;
    a += s;
    u += s - sn->data->freeSize;
}

void moveToHead(SuperNode* cur, SuperNode* next) { // поместить узел cur перед next
    if (cur->next) cur->next->prev = cur->prev;
    if (cur->prev) cur->prev->next = cur->next;
    if (next) next->prev = cur;
    cur->next = next;
    cur->prev = nullptr;
}

void* ProcessHeap::getMemoryBlock(size_t sz) {
    mtx.lock();

    size_t ind = 0;
    while (size_classes[ind] < sz) {
        ind++;
    }
    size_t newsz = size_classes[ind];

    if (freeBlocks[ind] == 0) { // если в куче потока нет блока с нужным разбиением, добавим его туда
        createSuperNode(ind, newsz);
    }
    SuperNode* sn = (superHeads[ind] ? superHeads[ind] : superEmpty[ind]);
    SuperBlock* sb = sn->data;

    size_t blkNum = (sb->stackHead >= sb->blocksInUse ? consecutive[sb->stackHead++] : sb->blkNum[sb->stackHead++]); // берем из стека номер блока
    void* ptr = (char*) sb->blocks + blkNum * sb->blockSize;

    u += newsz;
    sb->freeSize -= newsz;
    if (sb->freeSize == 0) { // если блок целиком заполнен, перенесем его в superFull, список полностью заполненных
        superHeads[ind] = sn->next;
        moveToHead(sn, superFull[ind]);
        superFull[ind] = sn;
        freeBlocks[ind]--;
    } else if (sb->freeSize <= part && sb->freeSize + sb->blockSize > part) {
        // перемещаем блок из почти пустых в среднезаполненые, если в нём занятно места > part
        superEmpty[ind] = sn->next;
        moveToHead(sn, superHeads[ind]);
        superHeads[ind] = sn;
    }

    mtx.unlock();
    return ptr;
}

void freeBlockFromSuperblock(SuperBlock* sb, void* ptr) { // освобождает блок из суперблока и кладёт его на стек
    size_t blockNum = ((size_t) ptr - (size_t) sb->blocks) / sb->blockSize; // получаем номер блока 
    sb->blocksInUse = (sb->blocksInUse < sb->stackHead ? sb->stackHead : sb->blocksInUse); // обновляем число блоков, которые используем
    sb->blkNum[--sb->stackHead] = blockNum; // запишем номер в стек
    sb->freeSize += sb->blockSize;
}

void GlobalHeap::freeBlockFromHeap(void* ptr) {
    mtx.lock(); // блокируем, чтобы номер кучи в суперноде не менялся во время выполнения операции
    SuperNode* sn = nodeByPtr(ptr); // снова получаем ноду, а то могла поменяться, пока вызывали функцию
    if (sn->heapIndex == (size_t) - 1) { // освобождаем из глобальной кучи
        freeBlockFromSuperblock(sn->data, ptr);
        if (sn->data->freeSize == s) { // если суперблок опустел, то перенесём в список полностью пустых голов
            size_t ind = 0;
            while (size_classes[ind] < sn->data->blockSize) {
                ind++;
            }
            if (sn == emptyHeads[ind]) emptyHeads[ind] = emptyHeads[ind]->next; // если в начале пустых голов, то подвинем указатель на начало
            moveToHead(sn, head);
        }
        mtx.unlock();
    } else {
        mtx.unlock();
        return processHeaps[sn->heapIndex].freeMemoryBlock(ptr); // аккуратно, тут heap->index мог поменяться!
    }
}

void ProcessHeap::freeMemoryBlock(void* ptr) {
    mtx.lock();

    SuperNode* node = GlobalHeap::nodeByPtr(ptr); // снова берём ноду, а то могла поменяться
    if (heapNum != node->heapIndex) { // номер кучи мог поменяться, тогда сделаем retry и попробуем снова освободить поменять
        mtx.unlock();
        globalHeap.freeBlockFromHeap(ptr);
        return;
    }

    SuperBlock* sb = node->data;
    freeBlockFromSuperblock(sb, ptr);
    u -= sb->blockSize;

    size_t ind = 0;
    while (size_classes[ind] < sb->blockSize) {
        ind++;
    }

    if (sb->freeSize == sb->blockSize) {
        // перемещаем супернод из superFull в superHeads, так как он был полным, а потом один блок освободился
        if (node == superFull[ind]) superFull[ind] = node->next; // если узел в начале списка полных, то изменим начало
        moveToHead(node, superHeads[ind]);
        superHeads[ind] = node;
        freeBlocks[ind]++;
    } else if (sb->freeSize > part && sb->freeSize - sb->blockSize <= part) {
        // переносим суперноду из списка среднезаполненных суперблоков в почти пустые, когда в ней много свободного места
        if (node == superHeads[ind]) superHeads[ind] = node->next; // если узел в начале списка, то надо начало изменить
        moveToHead(node, superEmpty[ind]); // он полностью освободился -> перенесем в конец списка
        superEmpty[ind] = node;
    } else if (sb->freeSize - sb->blockSize <= part) { // иначе всегда переносим в начало списка, если суперблок в среднезаполненном списке
        if (node->prev) { // если уже в начале, то всё же не переносим
            moveToHead(node, superHeads[ind]);
            superHeads[ind] = node;
        }
    }

    // если нужно, вернем пустой блок в глобальную кучу
    if ((u + k * s < a) && (u < (1 - f) * a)) {
        SuperNode* sn = nullptr;
        size_t j = 0;
        while (!superEmpty[j]) j++;
        
        if (sn) {
            if (superEmpty[j] == sn) superEmpty[j] = sn->next;
            if (sn->next) sn->next->prev = sn->prev;
            if (sn->prev) sn->prev->next = sn->next;
            freeBlocks[j]--;
            a -= s;
            globalHeap.addSuperNode(sn, j);
        }
    }

    mtx.unlock();
}