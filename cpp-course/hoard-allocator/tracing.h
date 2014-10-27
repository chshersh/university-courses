#pragma once

#include <cstdlib>
#include <limits>

namespace hoard
{
    void print_object(char const*);
    void print_object(void* px);
    void print_object(size_t n);
    
    void print();

    template <typename T, typename ... Ts>
    void print(T obj, Ts ... objs)
    {
        print_object(obj);
        print(objs...);
    }

    bool trace_enabled();

    template <typename ... Ts>
    void trace(Ts ... objs)
    {
        if (!trace_enabled())
            return;
        
        print(objs...);
    }
}