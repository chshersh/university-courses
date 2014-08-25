#include "vector.h"

Vector::Vector(size_t n, ui64 el) {
	length = n;
	if (n <= 1) {
		is_long = 0;
		small_a = el;
	} else {
		a.resize(n);
		is_long = true;
		for (size_t i = 0; i < n; ++i) {
			a[i] = el;
		}
	}
}

Vector::Vector(Vector const &other) {
	*this = other;
}

void Vector::resize(size_t n) {
	if (is_long || (n > 1)) {
		a.resize(n);
		is_long = 1;
	} else {
		a.clear();
		is_long = 0;
		small_a = 0;
	}
	length = n;
}

void Vector::push_back(ui64 x) {
	if (!is_long) {
		if (length) {
			a.clear();
			a.push_back(small_a);
			a.push_back(x);
			is_long = 1;
			small_a = 0;
		} else {
			small_a = x;
		}
	} else {
		a.push_back(x);
	}
	length++;
}

ui64 Vector::pop_back() {
	ui64 t = this->back();
	if (is_long) {
		a.pop_back();
		if (a.size() == 1) {
			small_a = a[0];
			is_long = 0;
			a.clear();
		}
	}
	length--;
	return t;
}

ui64& Vector::back() {
	return (is_long ? a.back() : small_a);
}

void Vector::reverse() {
	if (is_long) std::reverse(a.begin(), a.end());
}

bool Vector::empty() {
	return (length == 0);
}

size_t Vector::size() {
	return length;
}

Vector& Vector::operator=(Vector const &other) {
	a = other.a;
	is_long = other.is_long;
	small_a = other.small_a;
	length = other.length;
	return *this;
}

ui64& Vector::operator[](const size_t k) {
	return (is_long ? a[k] : small_a);
}

ui64 Vector::operator[](const size_t k) const {
	return (is_long ? a[k] : small_a);
}
