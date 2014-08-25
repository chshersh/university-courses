// big_integer.cpp
// compile with: /W4

#include "big_integer.h"
#include <sstream>


big_integer::big_integer(const big_integer &other) {
	box = other.box;
	box->cnt++;
}

big_integer::big_integer(ui64 a) {
	box = new copy_on_write();
	box->v.push_back(a);
	box->v.push_back(0);
	box->cnt = 1;
}

big_integer::big_integer(int a) {
	bool is_negative = (a < 0);
	a = abs(a);
	box = new copy_on_write();
	box->v.push_back(a);
	box->v.push_back(0);
	box->cnt = 1;
	if (is_negative) {
		*this = -(*this);
	}
}

big_integer::big_integer(const std::string &s) {
	const ui64 local_base(1000000000);
	const int base_digits = 9;
	Vector a; 
	int pos = (s[0] == '-' || s[0] == '+') ? 1 : 0;
	for (int i = s.size() - 1; i >= pos; i -= 9) {
		ui64 x = 0;
		for (int j = std::max(pos, i - base_digits + 1); j <= i; j++)
			x = x * 10 + s[j] - '0';
		a.push_back(x);
	}

	while (a.size() > 0 && !a.back()) {
		a.pop_back();
	}
	if (a.empty()) {
		box = new copy_on_write();
		box->v.push_back(0);
		box->v.push_back(0);
		box->cnt = 1;
		return;
	}
	
	box = new copy_on_write();
	while (!a.empty()) {
		ui64 rem = 0;
		for (size_t i = a.size(); i != 0; --i) {
			size_t j = i - 1;
			ui64 cur = a[j] + rem * local_base;
			a[j] = (ui64) (cur / base);
			rem = (ui64) (cur % base);
		}

		box->v.push_back(rem);
		while (!a.empty() && !a.back()) {
			a.pop_back();
		}
	}
	
	while (box->v.size() > 1 && !box->v.back()) {
		box->v.pop_back();
	}
	box->v.push_back(0);
	box->cnt = 1;
	if (s[0] == '-') {
		*this = -(*this);
	}
}

void big_integer::make_new() {
	big_integer t = *this;
	this->delete_copy();
	this->box = new copy_on_write();
	this->box->cnt = 1;
	this->box->v = t.box->v;
}

big_integer& big_integer::operator=(big_integer const &rhs) {
	if (*this == rhs) {
		return *this;
	}

	delete_copy();
	box = rhs.box;
	box->cnt++;
	return *this;
}

big_integer& big_integer::operator+=(big_integer const &rhs) {
	return (*this = *this + rhs);
}

big_integer& big_integer::operator-=(big_integer const &rhs) {
	return (*this = *this - rhs);
}

big_integer& big_integer::operator*=(big_integer const &rhs) {
	return (*this = (*this) * rhs);
}

std::pair<big_integer, big_integer> divmod(big_integer const& a, big_integer const& b) {
	if (b == 0) throw std::exception("division by zero");
	big_integer a1(a), b1(b);
	big_integer res_div, res_mod;
	Vector vec_div;

	ui64 res_sign = (a1.box->v.back() ^ b1.box->v.back());
	ui64 y = b1.box->v.back();
	a1 = abs(a1);
	b1 = abs(b1);

	int col = a1.box->v.size() - b1.box->v.size();
	if (col > 0) {
		b1 <<= col * 32;
	}
	for (int i = 0; i <= col; ++i) {
		if (b1 <= a1) {
			// binary_search
			ui64 l = 0, r = big_integer::base;
			while (r - l > 1) {
				ui64 m = (l + r) >> 1;
				if (b1 * m <= a1) {
					l = m;
				} else {
					r = m;
				}
			}
			vec_div.push_back(l);
			a1 -= b1 * l;
		} else {
			vec_div.push_back(0);
		}
		b1 >>= 32;
	}
	vec_div.reverse();
	res_mod = a1;

	while (vec_div.size() > 1 && !vec_div.back()) {
		vec_div.pop_back();
	}
	if (vec_div.empty()) {
		res_div = 0;
	} else  {
		vec_div.push_back(0);
		res_div.box->v = vec_div;
		if (res_sign) {
			res_div = -res_div;
			if (!y) res_mod = -res_mod;
		}
	}

	trim(res_div);
	trim(res_mod);
	return std::make_pair(res_div, res_mod);
}

big_integer& big_integer::operator/=(int rhs) {
	bool res_sign = (rhs < 0) ^ bool(box->v.back() & 1);
	ui64 urhs = std::abs(rhs), rem = 0;
	*this = abs(*this);

	for (size_t i = this->box->v.size() - 1; i != 0; --i) {
		size_t j = i - 1;
		ui64 cur = this->box->v[j] + rem * base;
        this->box->v[j] = cur / urhs;		
        rem = cur % urhs;
	}

	trim(*this);
	if (res_sign) *this = -(*this);
	return *this;

}

big_integer& big_integer::operator/=(big_integer const &rhs) {
	*this = divmod(*this, rhs).first;
	return *this;
}

big_integer& big_integer::operator%=(big_integer const &rhs) {
	*this = divmod(*this, rhs).second;
	return *this;
}

big_integer& big_integer::operator&=(big_integer const &rhs) {
	return (*this = *this & rhs);
}

big_integer& big_integer::operator|=(big_integer const &rhs) {
	return (*this = *this | rhs);
}

big_integer& big_integer::operator^=(big_integer const &rhs) {
	return (*this = *this ^ rhs);
}

big_integer& big_integer::operator<<=(int rhs) {
	return (*this = *this << rhs);
}

big_integer& big_integer::operator>>=(int rhs) {
	return (*this = *this >> rhs);
}

//префиксный инкремент
big_integer big_integer::operator++() {
	return (*this += 1);
}

//постфиксный инкремент
big_integer big_integer::operator++(int i) {
	big_integer old_value = *this;
	*this += 1;
	return old_value;
}

big_integer big_integer::operator--() {
	return (*this -= 1);
}

big_integer big_integer::operator--(int i) {
	big_integer old_value = *this;
	*this -= 1;
	return old_value;
}

big_integer big_integer::operator+() const {
	return *this;
}

big_integer big_integer::operator-() const {
	big_integer b = *this;
	b = ~(b) + 1;
	return b;
}

void trim(big_integer &a) {
	while (a.box->v.size() > 2 && (a.box->v[a.box->v.size() - 1] == a.box->v[a.box->v.size() - 2])) {
		a.box->v.pop_back();
	}
}

big_integer abs(big_integer const &a) {
	big_integer b = a;
	if (b.box->v.back()) b = -b;
	return b;
}

big_integer big_integer::operator~() const {
	big_integer b;
	b.box->v.resize(this->box->v.size());

	for (size_t i = 0; i < b.box->v.size(); ++i) {
		b.box->v[i] = (~this->box->v[i]) & neg_sign; // b.v[i] = (~b.v[i]) % base;
	}

	trim(b);
	return b;
}

big_integer operator+(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = b, res;

	ui64 t = 0;
	while (aa.box->v.size() < bb.box->v.size()) aa.box->v.push_back(aa.box->v.back());
	while (bb.box->v.size() < aa.box->v.size()) bb.box->v.push_back(bb.box->v.back());
	res.box->v.resize(aa.box->v.size());

	for (size_t i = 0; i < aa.box->v.size(); ++i) {
		t += aa.box->v[i] + bb.box->v[i];
		res.box->v[i] = t & big_integer::neg_sign; // v[i] = t % base
		t >>= 32;					  // t /= base
	}
	
	bool ts = t & 1, as = aa.box->v.back() & 1, bs = bb.box->v.back() & 1;
	res.box->v.push_back(ts ^ as ^ bs ? big_integer::neg_sign : 0);

	trim(aa);
	trim(bb);
	trim(res);

	return res;
}

big_integer operator-(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = -b, res;
	res = aa + bb;
	return res;
}

big_integer operator*(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = b, res;
	ui64 res_sign = (aa.box->v.back() ^ bb.box->v.back());
	aa = abs(aa);
	bb = abs(bb);

	res.box->v.resize(aa.box->v.size() + bb.box->v.size());
	for (size_t i = 0; i < aa.box->v.size(); ++i) {
		ui64 carry = 0;
		for (size_t j = 0; j < bb.box->v.size() || carry; ++j) {
			ui64 cur = res.box->v[i + j] + aa.box->v[i] * (j < bb.box->v.size() ? bb.box->v[j] : 0) + carry;
			res.box->v[i + j] = cur & big_integer::neg_sign;
			carry = cur >> 32;
		}
	}

	while (res.box->v.size() > 1 && !res.box->v.back()) {
		res.box->v.pop_back();
	}
	res.box->v.push_back(0);
	if (res_sign) res = -res;

	return res;
}

big_integer operator/(big_integer const &a, big_integer const &b) {
	big_integer res = divmod(a, b).first;
	return res;
}

big_integer operator%(big_integer const &a, big_integer const &b) {
	big_integer res = divmod(a, b).second;
	return res;
}

big_integer operator&(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = b, res;

	while (aa.box->v.size() < bb.box->v.size()) aa.box->v.push_back(aa.box->v.back());
	while (bb.box->v.size() < aa.box->v.size()) bb.box->v.push_back(bb.box->v.back());
	res.box->v.resize(aa.box->v.size());

	for (size_t i = 0; i < aa.box->v.size(); ++i) {
		res.box->v[i] = aa.box->v[i] & bb.box->v[i];
	}

	trim(aa);
	trim(bb);
	trim(res);

	return res;
}

big_integer operator|(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = b, res;

	while (aa.box->v.size() < bb.box->v.size()) aa.box->v.push_back(aa.box->v.back());
	while (bb.box->v.size() < aa.box->v.size()) bb.box->v.push_back(bb.box->v.back());
	res.box->v.resize(aa.box->v.size());

	for (size_t i = 0; i < aa.box->v.size(); ++i) {
		res.box->v[i] = aa.box->v[i] | bb.box->v[i];
	}

	trim(aa);
	trim(bb);
	trim(res);

	return res;
}

big_integer operator^(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = b, res;

	while (aa.box->v.size() < bb.box->v.size()) aa.box->v.push_back(aa.box->v.back());
	while (bb.box->v.size() < aa.box->v.size()) bb.box->v.push_back(bb.box->v.back());
	res.box->v.resize(aa.box->v.size());

	for (size_t i = 0; i < aa.box->v.size(); ++i) {
		res.box->v[i] = aa.box->v[i] ^ bb.box->v[i];
	}

	trim(aa);
	trim(bb);
	trim(res);

	return res;
}

big_integer operator<<(big_integer const &a, int b) {
	// if rhs < 0 throw "shift exception"
	big_integer aa = a;
	if (aa == 0 || b == 0) {
		return aa;
	}
	
	aa.make_new();

	int quick_shift = b >> 5, slow_shift = 1 << (b & 31); // quick_shift = rhs / 32, slow_shift = 2^(rhs % 32);
	aa.box->v.reverse();
	for (int i = 0; i < quick_shift; ++i) {
		aa.box->v.push_back(0);
	}
	aa.box->v.reverse();
	
	aa *= slow_shift;

	return aa;
}

big_integer operator>>(big_integer const &a, int b) {
	// if rhs < 0 throw "shift exception"
	big_integer aa = a;
	if (aa == 0 || b == 0) {
		return aa;
	}
	if ((b / 32) >= (int) (aa.box->v.size() - 1)) {
		aa = big_integer(0);
		return aa;
	}

	aa.make_new();

	int quick_shift = b >> 5, slow_shift = 1 << (b & 31); // quick_shift = rhs / 32, slow_shift = 2^(rhs % 32);
	aa.box->v.reverse();
	for (int i = 0; i < quick_shift; ++i) {
		aa.box->v.pop_back();
	}
	aa.box->v.reverse();

	aa /= slow_shift;

	return aa;
}

bool operator==(big_integer const &a, big_integer const &b) {
	ui64 as = a.box->v.back(), bs = b.box->v.back(); // as = a.sign, bs = b.sign
	if ((a.box->v.size() != b.box->v.size()) || (as != bs)) {
		return 0;
	}

	bool f = true;
	for (size_t i = 0; i < a.box->v.size() && f; ++i) {
		f = (a.box->v[i] == b.box->v[i]);
	}

	return f;
}

bool operator!=(big_integer const &a, big_integer const &b) {
	return (!(a == b));
}

bool operator<(big_integer const &a, big_integer const &b) {
	big_integer aa = a, bb = b;
	bool as = aa.box->v.back() & 1, bs = bb.box->v.back() & 1;

	if (as ^ bs) { //если разные знаки
		return !bs;
	}
	
	bool cs = as;
	aa = abs(aa);
	bb = abs(bb);

	if (aa.box->v.size() != bb.box->v.size()) {
		return ((aa.box->v.size() < bb.box->v.size()) ^ cs);
	}

	ptrdiff_t i;
	for (i = (ptrdiff_t)(aa.box->v.size() - 1); (i >= 0) && (aa.box->v[i] == bb.box->v[i]); --i) {} 

	if (i < 0) {
		return 0;
	}

	return ((aa.box->v[i] < bb.box->v[i]) ^ cs);
}

bool operator>(big_integer const &a, big_integer const &b) {
	return (!(a < b) && (a != b));
}

bool operator<=(big_integer const &a, big_integer const &b) {
	return ((a < b) || (a == b));
}

bool operator>=(big_integer const &a, big_integer const &b) {
	return ((a > b) || (a == b));
}

Vector trim_to_base(Vector const &vc, const ui64 to_base) {
	Vector vec = vc;
	size_t k = 0;
	while (k < vec.size()) {
		ui64 p = vec[k] / to_base, r = vec[k] % to_base;
		if (k == vec.size() - 1) {
			if (p) vec.push_back(p);
		} else {
			vec[k + 1] += p;
		}
		vec[k++] = r;
	}
	
	while (!vec.empty() && !vec.back()) {
		vec.pop_back();
	}
	return vec;
}

std::string to_string(big_integer const &a) {
	std::string s = "";
	big_integer b(a);
	if (b.box->v.back()) {
		s = "-";
		b = -b;
	}

	const ui64 local_base = 10000;
	const int base_digits = 4;
	Vector sum, pow;

	sum.push_back(b.box->v[0]);
	pow.push_back(1);
	for (size_t j = 1; j < b.box->v.size() - 1; ++j) {
		// pow *= base (base = 2^32)
		for (size_t i = 0; i < pow.size(); ++i) {
			pow[i] *= big_integer::base;
		}	
		pow = trim_to_base(pow, local_base);
		

		//mul = pow * b.v[j];
		Vector mul(pow.size());
		for (size_t i = 0; i < mul.size(); ++i) {
			mul[i] = pow[i] * b.box->v[j];
		}
		mul = trim_to_base(mul, local_base);

		//sum += mul
		for (size_t i = 0; i < mul.size(); ++i) {
			if (sum.size() == i) {
				sum.push_back(mul[i]);
			} else {
				sum[i] += mul[i];
			}
		}
		sum = trim_to_base(sum, local_base);
		
	}
	
	// sum |-> в строку
	std::stringstream ss;
	std::string t;
	ss.clear();
	ss << sum.back();
	ss >> t;
	s += t;
	if (sum.size() > 1) {
		for (size_t i = sum.size() - 1; i != 0; --i) {
			size_t j = i - 1;
			ss.clear();
			ss << sum[j];
			ss >> t;
			while (t.length() < base_digits) {
				t = "0" + t;
			}
			s += t;
		}
	}
	return s;
}
