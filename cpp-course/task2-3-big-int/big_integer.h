#pragma once

#ifndef BIG_INTEGER_H
#define BIG_INTEGER_H

#include "vector.h"
#include <string>

struct big_integer {
	static const ui64 base = (_UI64_MAX >> 32) + 1; //2^32
	static const ui64 neg_sign = base - 1;
	
	struct copy_on_write {
		Vector v;
		int cnt;
	};

	void delete_copy() {
		if (box) {
			box->cnt--;
			if (box->cnt <= 0) {
				delete box;
			}
		}
	}

	big_integer(big_integer const &other);
	big_integer(ui64 a);
	big_integer(int a = 0);
	explicit big_integer(std::string const &s);

	void make_new();

	~big_integer() {
		delete_copy();
	}

	big_integer& operator=(big_integer const &rhs);
	big_integer& operator+=(big_integer const &rhs);
	big_integer& operator-=(big_integer const &rhs);
	big_integer& operator*=(big_integer const &rhs);
	friend std::pair<big_integer, big_integer> divmod(big_integer const &a, big_integer const &b);
	big_integer& operator/=(int rhs);
	big_integer& operator/=(big_integer const &rhs);
	big_integer& operator%=(big_integer const &rhs);

	big_integer& operator&=(big_integer const &rhs);
    big_integer& operator|=(big_integer const &rhs);
    big_integer& operator^=(big_integer const &rhs);

	big_integer& operator<<=(int rhs);
    big_integer& operator>>=(int rhs);

	big_integer operator++();      // префиксный инкремент
	big_integer operator++(int i); //постфиксный инкремент
	big_integer operator--();      
	big_integer operator--(int i);

    big_integer operator+() const;
    big_integer operator-() const;
	friend void trim(big_integer &a);
	friend big_integer abs(big_integer const &a);
    big_integer operator~() const;	


	friend big_integer operator+(big_integer const &a, big_integer const &b);
	friend big_integer operator-(big_integer const &a, big_integer const &b);
	friend big_integer operator*(big_integer const &a, big_integer const &b);
	friend big_integer operator/(big_integer const &a, big_integer const &b);
	friend big_integer operator%(big_integer const &a, big_integer const &b);

	friend big_integer operator&(big_integer const &a, big_integer const &b);
	friend big_integer operator|(big_integer const &a, big_integer const &b);
	friend big_integer operator^(big_integer const &a, big_integer const &b);

	friend big_integer operator<<(big_integer const &a, int b);
	friend big_integer operator>>(big_integer const &a, int b);

	friend bool operator==(big_integer const &a, big_integer const &b);
	friend bool operator<(big_integer const &a, big_integer const &b);
	friend bool operator!=(big_integer const &a, big_integer const &b);
	friend bool operator>(big_integer const &a, big_integer const &b);
	friend bool operator<=(big_integer const &a, big_integer const &b);
	friend bool operator>=(big_integer const &a, big_integer const &b);
	
	friend std::string to_string(big_integer const &a);

private:
	copy_on_write *box;
};

#endif
