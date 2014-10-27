#pragma once

#include <cstdlib>

namespace hoard {  
    bool is_valid_alignment(size_t alignment);
    
    void* hoard_alloc (size_t size, size_t alignment);
    void hoard_free (void* ptr);
    void* hoard_realloc (void *ptr, size_t size);
}