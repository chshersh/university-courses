#include <crtdbg.h>
#include <iostream>
#include <ctime>
#include <set>
#include <memory>
#include <vector>

using namespace std;
//using namespace std::tr1;

#define sptr(T) shared_ptr<T>


struct TreeNode {
	int val;

	TreeNode()
		:left()
		,right()
		,val()
	{}

	TreeNode(sptr(TreeNode) other)
		:left(other->left)
		,right(other->right)
		,val(other->val)
	{}

	TreeNode(int v)
		:val(v)
	{}

	TreeNode(sptr(TreeNode) l, sptr(TreeNode) r, int v)
		:left(l)
		,right(r)
		,val(v)
	{}

	void print(sptr(TreeNode) node) {
		if (node) {
			return;
		}
		node->print(node->left);
		cout << node->val << " ";
		node->print(node->right);
	}

	friend sptr(TreeNode) binaryTreeInsert(sptr(TreeNode) node, sptr(TreeNode) what);
	friend sptr(TreeNode) binaryTreeFind(sptr(TreeNode) node, int val);
	friend sptr(TreeNode) binaryTreeRemove(sptr(TreeNode) node, int val);

private:
	sptr(TreeNode) left, right;
};

struct pBST {
	vector<sptr(TreeNode)> roots;

	pBST() {
		roots.push_back(NULL);
	}

	void insert(int val) {
		sptr(TreeNode) what = make_shared<TreeNode>(val);
		roots.push_back(binaryTreeInsert(roots.back(), what));
	}

	void remove(int val) {
		roots.push_back(binaryTreeRemove(roots.back(), val));
	}

	bool lookup(int val) {
		return (roots.empty() || (binaryTreeFind(roots.back(), val) == NULL) ? 0 : 1);
	} 

	void print() {
		roots.back()->print(roots.back());
		cout << endl;
	}
};

sptr(TreeNode) binaryTreeInsert(sptr(TreeNode) node, sptr(TreeNode) what) { //куда вставляем и что
	if (node == NULL) {
		return make_shared<TreeNode>(what);
	} else if (what->val == node->val) {
		//sptr(TreeNode) obj = make_shared<TreeNode>(node);
		return make_shared<TreeNode>(node);
	} else if (what->val < node->val) {
		return make_shared<TreeNode>(binaryTreeInsert(node->left, what), node->right, node->val);
	} else {
		return make_shared<TreeNode>(node->left, binaryTreeInsert(node->right, what), node->val);
	}
}

sptr(TreeNode) binaryTreeFind(sptr(TreeNode) node, int val) {
	if (node == NULL) {
		return NULL;
	} else if (val == node->val) {
		return node;
	} else if (val < node->val) {
		return binaryTreeFind(node->left, val);
	} else {
		return binaryTreeFind(node->right, val);
	}
}

sptr(TreeNode) binaryTreeRemove(sptr(TreeNode) node, int val) {
	if (node == NULL) {
		return NULL;
	} else if (val < node->val) {
		return make_shared<TreeNode>(binaryTreeRemove(node->left, val), node->right, node->val);
	} else if (val > node->val) {
		return make_shared<TreeNode>(node->left, binaryTreeRemove(node->right, val), node->val);
	} else {
		if (node->left != NULL && node->right != NULL) {
			return make_shared<TreeNode>(binaryTreeInsert(node->left, node->right));
		} else if (node->left != NULL) {
			return make_shared<TreeNode>(node->left->left, node->left->right, node->left->val);
		} else if (node->right != NULL) {
			return make_shared<TreeNode>(node->right->left, node->right->right, node->right->val);
		} else {
			return NULL;
		}
	}
}

int main() {
	_CrtSetDbgFlag(_CrtSetDbgFlag(_CRTDBG_REPORT_FLAG) | _CRTDBG_LEAK_CHECK_DF);

	pBST tree;
	srand(time(0));
	const int n = 10;
	int a[100010];
	set<int> unique1, unique2;

	for (int i = 0; i < n; ++i) {
		a[i] = rand() % 100;
		tree.insert(a[i]);
		unique1.insert(a[i]);
		cout << a[i] << " ";
	}
	cout << endl;
	cout << unique1.size() << endl;
	tree.print();

	for (int i = 0; i < n; i += 2) {
		tree.remove(a[i]);
		unique1.erase(a[i]);
	}
	cout << unique1.size() << endl;

	for (int i = 0; i < n; ++i) {
		if (tree.lookup(a[i])) {
			unique2.insert(a[i]);
		}
	}
	cout << unique2.size() << endl;
	tree.print();

	system("pause");
	return 0;
}