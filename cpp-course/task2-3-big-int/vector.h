#pragma once

#include <vector>
#include <algorithm>
typedef unsigned __int64 ui64;

struct Vector {
	Vector(size_t n = 0, ui64 el = 0);
	Vector(Vector const &other);

	void resize(size_t n);
	void push_back(ui64 x);
	ui64 pop_back();
	ui64& back();
	void reverse();
	bool empty();
	size_t size();
	
	Vector& operator=(Vector const &vec);
	ui64& operator[](const size_t k);
	ui64 operator[](const size_t k) const;

private:
	std::vector<ui64> a;
	ui64 small_a;
	bool is_long;
	size_t length;
};