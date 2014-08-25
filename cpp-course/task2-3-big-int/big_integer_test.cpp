#define BOOST_TEST_MODULE big_integer_test
#define BOOST_TEST_DYN_LINK
#define BOOST_TEST_MAIN
#include <boost/test/unit_test.hpp>

#include <algorithm>
#include <cassert>
#include <cstdlib>
#include <vector>
#include <utility>

#include "big_integer.h"

BOOST_AUTO_TEST_CASE(two_plus_two)
{
    BOOST_CHECK(big_integer(2) + big_integer(2) == big_integer(4));
    BOOST_CHECK(big_integer(2) + 2              == 4); // implicit converion from int must work
    BOOST_CHECK(2              + big_integer(2) == 4);
}

BOOST_AUTO_TEST_CASE(default_ctor)
{
    big_integer a;
    big_integer b = 0;
    BOOST_CHECK(a == 0);
    BOOST_CHECK(a == b);
}

BOOST_AUTO_TEST_CASE(copy_ctor)
{
    big_integer a = 3;
    big_integer b = a;

    BOOST_CHECK(a == b);
    BOOST_CHECK(b == 3);
}

BOOST_AUTO_TEST_CASE(copy_ctor_real_copy)
{
    big_integer a = 3;
    big_integer b = a;
    a = 5;

    BOOST_CHECK(b == 3);
}

BOOST_AUTO_TEST_CASE(copy_ctor_real_copy2)
{
    big_integer a = 3;
    big_integer b = a;
    b = 5;

    BOOST_CHECK(a == 3);
}

BOOST_AUTO_TEST_CASE(assignment_operator)
{
    big_integer a = 4;
    big_integer b = 7;
    b = a;

    BOOST_CHECK(a == b);
}

BOOST_AUTO_TEST_CASE(self_assignment)
{
    big_integer a = 5;
    a = a;

    BOOST_CHECK(a == 5);
}

BOOST_AUTO_TEST_CASE(comparisons)
{
    big_integer a = 100;
    big_integer b = 100;
    big_integer c = 200;

    BOOST_CHECK(a == b);
    BOOST_CHECK(a != c);
    BOOST_CHECK(a < c);
    BOOST_CHECK(c > a);
    BOOST_CHECK(a <= a);
    BOOST_CHECK(a <= b);
    BOOST_CHECK(a <= c);
    BOOST_CHECK(c >= a);
}

BOOST_AUTO_TEST_CASE(compare_zero_and_minus_zero)
{
    big_integer a;
    big_integer b = -a;

    BOOST_CHECK(a == b);
}

BOOST_AUTO_TEST_CASE(add)
{
    big_integer a = 5;
    big_integer b = 20;

    BOOST_CHECK(a + b == 25);

    a += b;
    BOOST_CHECK(a == 25);
}

BOOST_AUTO_TEST_CASE(add_signed)
{
    big_integer a = 5;
    big_integer b = -20;

    BOOST_CHECK(a + b == -15);

    a += b;
    BOOST_CHECK(a == -15);
}

BOOST_AUTO_TEST_CASE(sub)
{
    big_integer a = 20;
    big_integer b = 5;

    BOOST_CHECK(a - b == 15);

    a -= b;
    BOOST_CHECK(a == 15);
}

BOOST_AUTO_TEST_CASE(sub_signed)
{
    big_integer a = 5;
    big_integer b = 20;

    BOOST_CHECK(a - b == -15);

    a -= b;
    BOOST_CHECK(a == -15);

    a -= -100;
    BOOST_CHECK(a == 85);
}

BOOST_AUTO_TEST_CASE(mul)
{
    big_integer a = 5;
    big_integer b = 20;

    BOOST_CHECK(a * b == 100);

    a *= b;
    BOOST_CHECK(a == 100);
}

BOOST_AUTO_TEST_CASE(mul_signed)
{
    big_integer a = -5;
    big_integer b = 20;

    BOOST_CHECK(a * b == -100);

    a *= b;
    BOOST_CHECK(a == -100);
}

BOOST_AUTO_TEST_CASE(div_)
{
    big_integer a = 20;
    big_integer b = 5;
    big_integer c = 20;

    BOOST_CHECK(a / b == 4);
    BOOST_CHECK(a % b == 0);

    a /= b;
    BOOST_CHECK(a == 4);

    c %= b;
    BOOST_CHECK(c == 0);
}

BOOST_AUTO_TEST_CASE(div_signed)
{
    big_integer a = -20;
    big_integer b = 5;

    BOOST_CHECK(a / b == -4);
    BOOST_CHECK(a % b == 0);
}

BOOST_AUTO_TEST_CASE(div_rounding)
{
    big_integer a = 23;
    big_integer b = 5;

    BOOST_CHECK(a / b == 4);
    BOOST_CHECK(a % b == 3);
}

BOOST_AUTO_TEST_CASE(div_rounding_negative)
{
    big_integer a = 23;
    big_integer b = -5;
    big_integer c = -23;
    big_integer d = 5;

    BOOST_CHECK(a / b == -4);
    BOOST_CHECK(c / d == -4);
    BOOST_CHECK(a % b == 3);
    BOOST_CHECK(c % d == -3);
}

BOOST_AUTO_TEST_CASE(unary_plus)
{
    big_integer a = 123;
    big_integer b = +a;

    BOOST_CHECK(a == b);

    // this code should not compile:
    // &+a;
}

BOOST_AUTO_TEST_CASE(unary_minus)
{
    big_integer a = 666;
    big_integer b = -a;

    BOOST_CHECK(b == -666);
}

BOOST_AUTO_TEST_CASE(and_)
{
    big_integer a = 0x55;
    big_integer b = 0xaa;

    BOOST_CHECK((a & b) == 0);
    BOOST_CHECK((a & 0xcc) == 0x44);
    a &= b;
    BOOST_CHECK(a == 0);
}

BOOST_AUTO_TEST_CASE(and_signed)
{
    big_integer a = 0x55;
    big_integer b = 0xaa;

    BOOST_CHECK((b & -1) == 0xaa);
    BOOST_CHECK((a & (0xaa - 256)) == 0);
    BOOST_CHECK((a & (0xcc - 256)) == 0x44);
}

BOOST_AUTO_TEST_CASE(or_)
{
    big_integer a = 0x55;
    big_integer b = 0xaa;

    BOOST_CHECK((a | b) == 0xff);
    a |= b;
    BOOST_CHECK(a == 0xff);
}

BOOST_AUTO_TEST_CASE(or_signed)
{
    big_integer a = 0x55;
    big_integer b = 0xaa;

    BOOST_CHECK((a | (b - 256)) == -1);
}

BOOST_AUTO_TEST_CASE(xor_)
{
    big_integer a = 0xaa;
    big_integer b = 0xcc;

    BOOST_CHECK((a ^ b) == 0x66);
}

BOOST_AUTO_TEST_CASE(xor_signed)
{
    big_integer a = 0xaa;
    big_integer b = 0xcc;

    BOOST_CHECK((a ^ (b - 256)) == (0x66 - 256));
}

BOOST_AUTO_TEST_CASE(not_)
{
    big_integer a = 0xaa;

    BOOST_CHECK(~a == (-a - 1));
}

BOOST_AUTO_TEST_CASE(shl_)
{
    big_integer a = 23;

    BOOST_CHECK((a << 5) == 23 * 32);

    a <<= 5;
    BOOST_CHECK(a == 23 * 32);
}

BOOST_AUTO_TEST_CASE(shr_)
{
    big_integer a = 23;

    BOOST_CHECK((a >> 2) == 5);

    a >>= 2;
    BOOST_CHECK(a == 5);
}

// TODO: shr_signed

BOOST_AUTO_TEST_CASE(add_long)
{
    big_integer a("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    big_integer b(                                                     "100000000000000000000000000000000000000");
    big_integer c("10000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000");

    BOOST_CHECK(a + b == c);
}

BOOST_AUTO_TEST_CASE(sub_long)
{
    big_integer a("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    big_integer b(                                                     "100000000000000000000000000000000000000");
    big_integer c( "9999999999999999999999999999999999999999999999999999900000000000000000000000000000000000000");

    BOOST_CHECK(a - b == c);
}

BOOST_AUTO_TEST_CASE(mul_long)
{
    big_integer a("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    big_integer b(                                                     "100000000000000000000000000000000000000");
    big_integer c("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                                                                        "00000000000000000000000000000000000000");

    BOOST_CHECK(a * b == c);
}

BOOST_AUTO_TEST_CASE(div_long)
{
    big_integer a("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    big_integer b(                                                     "100000000000000000000000000000000000000");
    big_integer c("100000000000000000000000000000000000000000000000000000");

    BOOST_CHECK(a / b == c);
}

BOOST_AUTO_TEST_CASE(string_conv)
{
    BOOST_CHECK(to_string(big_integer("100")) == "100");
    BOOST_CHECK(to_string(big_integer("0100")) == "100");
    BOOST_CHECK(to_string(big_integer("0")) == "0");
    BOOST_CHECK(to_string(big_integer("-0")) == "0");
}


namespace
{
    unsigned const number_of_iterations = 1;
    size_t const number_of_multipliers = 1000;

    int myrand()
    {
        int val = rand() - RAND_MAX / 2;
        if (val != 0)
            return val;
        else
            return 1;
    }
}

BOOST_AUTO_TEST_CASE(mul_div_randomized)
{
    for (unsigned itn = 0; itn != number_of_iterations; ++itn)
    {
        std::vector<int> multipliers;

        for (size_t i = 0; i != number_of_multipliers; ++i)
            multipliers.push_back(myrand());

        big_integer accumulator = 1;

        for (size_t i = 0; i != number_of_multipliers; ++i)
            accumulator *= multipliers[i];

        std::random_shuffle(multipliers.begin(), multipliers.end());

        for (size_t i = 1; i != number_of_multipliers; ++i)
            accumulator /= multipliers[i];

        BOOST_CHECK(accumulator == multipliers[0]);
    }
}

namespace
{
    template <typename T>
    void erase_unordered(std::vector<T>& v, typename std::vector<T>::iterator pos)
    {
        std::swap(v.back(), *pos);
        v.pop_back();
    }

    template <typename T>
    T extract_random_element(std::vector<T>& v)
    {
        size_t index = rand() % v.size();
        T copy = v[index];
        erase_unordered(v, v.begin() + index);
        return copy;
    }

    template <typename T>
    void merge_two(std::vector<T>& v)
    {
        assert(v.size() >= 2);

        T a = extract_random_element(v);
        T b = extract_random_element(v);

        T ab = a * b;
        BOOST_REQUIRE(ab / a == b);
        BOOST_REQUIRE(ab / b == a);

        v.push_back(ab);
    }

    template <typename T>
    T merge_all(std::vector<T> v)
    {
        assert(!v.empty());

        while (v.size() >= 2)
            merge_two(v);

        return v[0];
    }
}

BOOST_AUTO_TEST_CASE(mul_merge_randomized)
{
    for (unsigned itn = 0; itn != number_of_iterations; ++itn)
    {
        std::vector<big_integer> x;
        for (size_t i = 0; i != number_of_multipliers; ++i)
            x.push_back(myrand());

        big_integer a = merge_all(x);
        big_integer b = merge_all(x);

        BOOST_CHECK(a == b);
    }
}
