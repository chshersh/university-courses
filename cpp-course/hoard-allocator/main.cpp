#include <chrono>
#include <cstdlib>
#include <iostream>
#include <vector>

void batch(std::vector<std::vector<char> >& v)
{
    size_t const N = 1000;
    for (size_t i = 0; i != N; ++i)
    {
        size_t const MAX_SIZE = 40000;
        size_t sz = rand() % MAX_SIZE;
        if (sz < v.size())
        {
            //std::cout << "random-alloc: erase" << std::endl;
            std::swap(v[i], v.back());
            v.pop_back();
        }
        else
        {
            //std::cout << "random-alloc: insert" << std::endl;
            v.push_back(std::vector<char>(rand() % 10000));
        }
    }
}

int main(int argc, char *argv[])
{
    std::vector<std::vector<char> > v;

    for (size_t i = 0; i != 100; ++i)
    {
        auto start = std::chrono::high_resolution_clock::now();
        batch(v);
        auto end = std::chrono::high_resolution_clock::now();
        std::cout << std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count()
                  << " milliseconds\n";
    }

    return EXIT_SUCCESS;
}
