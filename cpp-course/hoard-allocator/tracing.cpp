#include "tracing.h"

#include <cstring>
#include <unistd.h>
#include <iostream>

void hoard::print_object(char const* message)
{
    ::write(2, message, strlen(message));
}

void hoard::print_object(void* px)
{
    char const* hexdigits = "0123456789abcdef";
    
    char buffer[32];

    size_t n = (size_t)px;
    size_t divisor = 1;
    while (divisor <= (n / 16))
        divisor *= 16;
    
    char* p = buffer;
    *p++ = '0';
    *p++ = 'x';
    
    do
    {
        *p++ = hexdigits[(n / divisor) % 16];
        divisor /= 16;
    }
    while (divisor != 0);

    *p = '\0';
    
    //std::cout << "! " << px << " !" << std::endl; 
    print_object(buffer);
}

void hoard::print_object(size_t n)
{
    char buffer[32];

    size_t divisor = 1;

    while (divisor <= (n / 10))
        divisor *= 10;
    
    char* p = buffer;
    do
    {
        *p++ = ((n / divisor) % 10) + '0';
        divisor /= 10;
    }
    while (divisor != 0);

    *p = '\0';
    
    print_object(buffer);
}

void hoard::print()
{}

bool hoard::trace_enabled()
{
    static bool enabled = (getenv("MALLOC_INTERCEPT_NO_TRACE") == NULL);
    return enabled;
}
