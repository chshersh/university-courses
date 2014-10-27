#include <cstdlib>
#include <chrono>
#include <iostream>
#include <random>
#include <ctime>

const size_t MAX_ITER = 10;
const size_t SIZE = 10000;
const size_t MAX_ALLOC = 1024;

int main() {
	srand(time(0));
	
        for (int j = 0; j < MAX_ITER; ++j) {
		auto start = std::chrono::high_resolution_clock::now();
		void* a[SIZE];
		for (size_t i = 0; i < SIZE; ++i) {
			a[i] = malloc(rand() % MAX_ALLOC);
		}
		for (size_t i = 0; i < SIZE; ++i) {
			free(a[i]);
		}
		auto end = std::chrono::high_resolution_clock::now();
        	std::cout << std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count()
                 	 << " milliseconds\n";
	}

    return EXIT_SUCCESS;
}
