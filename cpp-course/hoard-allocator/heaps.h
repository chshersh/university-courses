#pragma once

#include "tracing.h"

#include <thread>
#include <mutex>

// общие константы; суперблок, узел списка суперблоков, преблок и функции для работы с памятью вне кучи 
namespace {
    const double f = 0.25; // min coef for usage
    const size_t k = 4; // min blocks in usage
    const size_t s = 4 * 1024; // page size in bytes
    const size_t part = 3 * 1024; // граница свободного места, больше которой нужно переносить суперблоки в глобальную кучу
    const size_t b = 2; // power of size class
    
    const size_t class_numbers = 9;
    constexpr size_t size_classes[] = {8, 16, 32, 64, 128, 256, 512, 1024, 2048};
    const size_t maxThreads = 20;
    const size_t DEFAULT_ALIGNMENT = 8;
    size_t consecutive[s / size_classes[0]]; // последовательные числа
    
    const size_t DARK_MAGIC = 0xBEBEBEBE;    // используется в SuperNode для определения, нужно ли выделять munmap'ом или смотреть в куче
    const size_t LIGHT_MAGIC = 0xDEADC0DE;   // используется в blockHeader для проверки корректности данных

    struct blockHeader {
        void* start_address;
        size_t total_size;
        size_t data_size;
        size_t magic;
    };

    size_t roundup(size_t n, size_t alignment) {
        return (n + alignment - 1) / alignment * alignment;
    }

    blockHeader* blockByPtr(void* p) {
        void* ptr = (char*) p - sizeof (blockHeader);
        blockHeader* blk = (blockHeader*) ptr;
        
        /*if (blk->magic != LIGHT_MAGIC) {
            hoard::print("bad light magic in block ", ptr, "\n");
            std::abort();
        }*/
        
        return blk;
    }
}

struct SuperBlock {
    short blkNum[s / size_classes[0]];  // стек блоков
    size_t stackHead;                   // индекс головы стека в массиве блоков
    size_t blocksInUse;                 // количество использованных блоков за жизнь суперблока в куче

    void* blocks;                       // выдаваемая память

    size_t blockSize;                   // размер блока в суперблоке
    size_t freeSize;                    // размер свободного места в суперблоке
};

struct SuperNode { // для работы со списком суперблоков
    SuperBlock* data;

    SuperNode* next;
    SuperNode* prev;

    size_t heapIndex; // какой куче принадлежит нода
    size_t magic;
};

struct ProcessHeap {
    size_t u = 0, a = 0;
    size_t heapNum = 0; // индекс данной кучи в массиве куч процессов                    
    size_t freeBlocks[class_numbers] = {};     // количество свободных суперблоков каждого размера - от superHeads до superTails
    SuperNode* superEmpty[class_numbers] = {}; // список пустых суперблоков
    SuperNode* superHeads[class_numbers] = {}; // указатель на начало списка свободных суперблоков
    SuperNode* superFull[class_numbers] = {}; // указатели на начало списков заполненных суперблоков                             
    std::thread::id id; // id потока, радотающего с данной кучей                             
    std::mutex mtx;

    std::thread::id getID() const;
    void createSuperNode(size_t ind, size_t newsz); // если в глобальной куче есть суперблоки, то переносит суперблок (с блоками размера newsz) оттуда или создает новый - суперблок помещается в суперголову
    
    void* getMemoryBlock(size_t sz);
    void freeMemoryBlock(void* ptr);
};

struct GlobalHeap {
    size_t procNum = 0;
    SuperNode* head = nullptr; // указатель на голову списка свободных суперблоков
    SuperNode* emptyHeads[class_numbers] = {}; // указатели на почти пустые суперблоки каждого класса
    ProcessHeap processHeaps[maxThreads];
    std::mutex mtx;

    GlobalHeap();
    SuperNode* getNewSuperNode(size_t blockSize, size_t ind, size_t fromHeapIndex); // берет супернод из головы кучи или создает новый, если в глобальной куче нет суперблоков
    void addSuperNode(SuperNode* sn, size_t ind); // добавляет суперблок в глобальную кучу
    void addNewHeap(std::thread::id threadID);
    
    static SuperNode* nodeByPtr(void* ptr); // возвращает ноду по адресу
    void freeBlockFromHeap(void* ptr); // освободить суперблок из глобальной кучи или из кучи потока: разбираемся по обстоятельствам
};

extern GlobalHeap globalHeap;