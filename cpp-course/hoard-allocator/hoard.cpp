#include "hoard.h"
#include "heaps.h"
#include "tracing.h"

#include <algorithm>
#include <string.h>
#include <unistd.h>
#include <sys/mman.h>

namespace hoard {

    bool is_power_of_2(size_t n) {
        return ((n != 0) && !(n & (n - 1)));
    }

    bool is_valid_alignment(size_t alignment) {
        if ((alignment % sizeof (void*)) != 0) {
            return false;
        }

        if (!is_power_of_2(alignment)) {
            return false;
        }

        return true;
    }

    std::mutex local_mutex;

    size_t getProcessIndex() {
        auto thisID = std::this_thread::get_id();
        for (size_t i = 0; i < globalHeap.procNum; i++) {
            if (globalHeap.processHeaps[i].getID() == thisID) {
                return i;
            }
        }
        return (size_t) - 1;
    }

    void* hoard_alloc(size_t size, size_t alignment) {
        if (size == 0)
            return nullptr;

        if ((size > s / 2) || (alignment > s / 2)) {
            size = std::max(s + sizeof (SuperNode) + alignment, size); // выделим больше памяти, чтобы точно попасть в доступную память
            size_t data_start_offset = roundup(sizeof (blockHeader), alignment);
            size_t header_start_offset = data_start_offset - sizeof (blockHeader);

            size_t total_size = data_start_offset + size;

            bool big_alignment = alignment > sysconf(_SC_PAGESIZE);
            if (big_alignment)
                total_size += alignment - 1;


            void* ptr = mmap(NULL, total_size, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
            if (!ptr)
                return nullptr;

            if (big_alignment) {
                size_t ptrnum = (size_t) ptr;
                size_t alignment_size = roundup(ptrnum, alignment) - ptrnum;

                data_start_offset += alignment_size;
                header_start_offset += alignment_size;
            }

            blockHeader* blk = (blockHeader*) ((char*) ptr + header_start_offset);

            blk->start_address = ptr;
            blk->total_size = total_size;
            blk->data_size = size;
            blk->magic = LIGHT_MAGIC;

            return (char*) ptr + data_start_offset;
        }

        size_t ind = getProcessIndex();
        if (ind == (size_t) - 1) {
            local_mutex.lock();

            globalHeap.addNewHeap(std::this_thread::get_id());
            ind = globalHeap.procNum - 1;

            local_mutex.unlock();
        }

        if (alignment > size) {
            size = alignment;
        }
        return globalHeap.processHeaps[ind].getMemoryBlock(size);;
    }

    void hoard_free(void* ptr) {
        if (!ptr) return;
        if ((size_t) (ptr) % 2 == 1) {
            trace("free odd ", ptr, "\n");
            std::abort();
        }
        
        SuperNode* node = GlobalHeap::nodeByPtr(ptr);
        if (node->magic == DARK_MAGIC) {
            globalHeap.freeBlockFromHeap(ptr);
        } else {
            blockHeader* blk = blockByPtr(ptr);
            munmap(blk->start_address, blk->total_size);
        }
    }

    void* hoard_realloc(void* ptr, size_t size) {
        if (size == 0) {
            hoard_free(ptr);
            return nullptr;
        }
        if (!ptr) {
            return hoard_alloc(size, DEFAULT_ALIGNMENT);
        }

        void* newptr = hoard_alloc(size, DEFAULT_ALIGNMENT);
        if (!newptr)
            return nullptr;

        SuperNode* node = GlobalHeap::nodeByPtr(ptr);
        size_t dtsz;
        if (node->magic == DARK_MAGIC) {
            dtsz = node->data->blockSize; // тут блокировка неважна, всё равно скопируется меньшее, да и по стандарту это необязательно
        } else {
            blockHeader* blk = blockByPtr(ptr);
            dtsz = blk->data_size;
        }
        memcpy(newptr, ptr, std::min(size, dtsz));
        hoard_free(ptr);

        return newptr;
    }
}