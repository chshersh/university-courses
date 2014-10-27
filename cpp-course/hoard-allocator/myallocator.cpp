#include "heaps.h"
#include "hoard.h"
#include "tracing.h"

#include <string.h>
#include <unistd.h>
#include <sys/mman.h>
#include <mutex>
#include <iostream>
using namespace hoard;

GlobalHeap globalHeap;
std::mutex mtx;
namespace {
    __thread bool inside_malloc = false;

    struct recuirsion_guard {

        recuirsion_guard() {
            if (inside_malloc) {
                print("recursive call\n");
                std::abort();
            }

            inside_malloc = true;
        }

        ~recuirsion_guard() {
            inside_malloc = false;
        }

    private:
        recuirsion_guard(recuirsion_guard const&);
        recuirsion_guard& operator=(recuirsion_guard const&);
    };
}

extern "C"
void* malloc(size_t size) noexcept {
    recuirsion_guard rg;
    //std::lock_guard<std::mutex> lock(mtx);
    
    trace("malloc ", size, " ", "\n");
    void *p = hoard_alloc(size, DEFAULT_ALIGNMENT);
    //trace("malloc ", size, " ", p, " ", (size_t)p % s, "\n");
    return p;
}

extern "C"
void* calloc(size_t n, size_t size) noexcept {
    recuirsion_guard rg;
    //std::lock_guard<std::mutex> lock(mtx);

    trace("calloc ", n, " ", size, "\n");
    void* p = hoard_alloc(n * size, DEFAULT_ALIGNMENT);
    memset(p, 0, n * size);
    //trace("calloc ", n, " ", size, " ", p, "\n");
    return p;
}

extern "C"
void free(void *ptr) noexcept {
    recuirsion_guard rg;
    //std::lock_guard<std::mutex> lock(mtx);

    trace("free ", ptr, "\n");
    hoard_free(ptr);
    //trace("free ", ptr, "\n");
}

extern "C"
void* realloc(void *ptr, size_t size) noexcept {
    recuirsion_guard rg;
    //std::lock_guard<std::mutex> lock(mtx);

    trace("realloc ", ptr, " ", size, "\n");
    void* p = hoard_realloc(ptr, size);
    //trace("realloc ", ptr, " ", size, " ", p, "\n");
    return p;
}

extern "C"
int posix_memalign(void** memptr, size_t alignment, size_t size) noexcept {
    recuirsion_guard rg;
    //std::lock_guard<std::mutex> lock(mtx);

    *memptr = nullptr;
    if (!is_valid_alignment(alignment)) {
        return EINVAL;
    }
    void* p = hoard_alloc(size, alignment);
    trace("posix_memalign ", alignment, " ", size, " ", p, "\n");
    if (!p) {
        return ENOMEM;
    }
    *memptr = p;
    return 0;
}