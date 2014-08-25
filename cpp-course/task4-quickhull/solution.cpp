#include <iostream>
#include <vector>
#include <set>
#include <cmath>
using namespace std;

struct Point {
	double x, y;

	Point()
		:x(0)
		,y(0)
	{};

	Point(double x, double y)
		:x(x)
		,y(y)
	{};
	
	void read() {
		cin >> x >> y;
	}

	void print() {
		cout << x << " " << y << endl;
	}

	bool operator==(Point other) const {
		return (x == other.x && y == other.y);
	}

	bool operator!=(Point other) const {
		return !(*this == other);
	}

	bool operator<(Point other) const {
		return (x < other.x || (x == other.x && y < other.y));

	}
};

double dist(Point a, Point b, Point c) {	//distance from vector AB to Point C
	return ((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y));
}

bool isAbove(Point a, Point b, Point c) {
	return (dist(a, b, c) > 0);
}

bool isBelow(Point a, Point b, Point c) {
	return (dist(a, b, c) < 0);
}

set<Point> up, down;


void quickHull(vector<Point> p, Point left, Point right, int direction) {
	if (p.empty() || left == right) {
		return;
	}

	vector<Point> a, b;
	Point d = p[0];

	for (size_t i = 1; i < p.size(); ++i) {
		if (fabs(dist(left, right, d)) < fabs(dist(left, right, p[i]))) {
			d = p[i];
		}
	}

	for (size_t i = 0; i < p.size(); ++i) {
		if (direction ? isBelow(left, d, p[i]) : isAbove(left, d, p[i])) {
			a.push_back(p[i]);
		} else if (direction ? isBelow(d, right, p[i]) : isAbove(d, right, p[i])) {
			b.push_back(p[i]);
		}
	}

	if (direction) {
		down.insert(d);
	} else {
		up.insert(d);
	}
	quickHull(a, left, d, direction);
	quickHull(b, d, right, direction);
}

int main() {
	freopen("40.in", "r", stdin);
	freopen("output.out", "w", stdout);

	int n;
	Point left;
	vector<Point> a, b;
	set<Point> t;
	
	cin >> n;
	if (n == 0) return 0;

	left.read();
	Point right(left);
	t.insert(left);

	for (int i = 1; i < n; ++i) {
		Point temp;
		temp.read();
		if (temp < left) left = temp;
		if (right < temp) right = temp;
		t.insert(temp);
	}

	for (set<Point>::iterator it = t.begin(); it != t.end(); ++it) {
		if (isAbove(left, right, *it)) {
			a.push_back(*it);
		} else if (isAbove(right, left, *it)) {
			b.push_back(*it);
		}
	}
	
	quickHull(a, left, right, 0);
	quickHull(b, left, right, 1);
	up.insert(left);
	up.insert(right);

	cout << up.size() + down.size() << endl;
	for (set<Point>::iterator it = up.begin(); it != up.end(); ++it) {
		Point temp = (*it);
		temp.print();
	}
	for (set<Point>::reverse_iterator rit = down.rbegin(); rit != down.rend(); ++rit) {
		Point temp = (*rit);
		temp.print();
	}

	system("pause");
	return 0;
}