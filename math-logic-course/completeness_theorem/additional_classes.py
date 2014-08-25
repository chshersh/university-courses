#!/usr/bin/python
# coding = utf-8


class Proof:
    def __init__(self, additional_axioms, text):
        self.additional_axioms = additional_axioms  # is a list of tuples [(A, True), (B, False)] for example
        self.text = text
        self.unique = set()

    def add_line(self, line):
        if line not in self.unique:
            self.text.append(line)
            self.unique.add(line)
        return self

    def add_list(self, lines):
        if not lines or lines[-1] in self.unique:
            return self
        for line in lines:
            self.add_line(line)
        return self

    def add_proof(self, other_proof):
        self.add_list(other_proof.text)
        return self

    def __contains__(self, item):
        return item in self.unique

    def __str__(self):
        return "\n".join(self.text)


class Tree:
    def __init__(self, statement, is_variable, left, right):
        self.statement = statement
        self.is_variable = is_variable
        self.left = left
        self.right = right
        self.path = ""

    priorities = {"!": 3, "&": 2, "|": 1, "->": 0}

    def __str__(self):
        if self.is_variable:
            return self.statement

        r = str(self.right)
        if self.statement == "!":
            if self.right.is_variable or self.right.statement == "!":
                return "!" + r
            else:
                return "!(" + r + ")"

        l = str(self.left)
        if not self.left.is_variable and (
            Tree.priorities[self.left.statement] < Tree.priorities[self.statement] or
            self.left.statement == "->"
        ): l = "(" + l + ")"

        if not self.right.is_variable and (
            Tree.priorities[self.right.statement] < Tree.priorities[self.statement] or
            (
                (self.right.statement == '&' or self.right.statement == '|') and
                self.statement == self.right.statement
            )
        ): r = "(" + r + ")"

        return l + self.statement + r

    def __eq__(self, other):
        return str(self) == str(other)

    def create_paths(self):
        links = ["", "", ""]

        def thread_tree(node, path):
            if not node:
                return
            elif not node.is_variable:
                thread_tree(node.left, path + '0')
                thread_tree(node.right, path + '1')
            else:
                pos = ord(node.statement[0]) - ord('A')
                if links[pos]:
                    node.path = links[pos]
                else:
                    links[pos] = path
        thread_tree(self, "")

    def walk_path(self, path):
        node = self
        for direction in path:
            if not node:
                return None
            node = (node.left, node.right)[direction == '1']
        return node

    @staticmethod
    def is_same(template, cur, root):
        if not template:
            return True
        elif not cur:
            return False
        elif template.is_variable:
            return True if not template.path else root.walk_path(template.path) == cur
        else:
            return template.statement == cur.statement and \
                   Tree.is_same(template.left, cur.left, root) and \
                   Tree.is_same(template.right, cur.right, root)

    def variables(self):  # returns list of variables in tree
        def get_vars(node):
            if node is None:
                return set()
            elif node.is_variable:
                return set(node.statement)
            else:
                return get_vars(node.left) | get_vars(node.right)
        return list(get_vars(self))

    def calculate(self, vars):
        if self.is_variable:
            return vars[self.statement]
        elif self.statement == "!":
            return not self.right.calculate(vars)
        l, r = self.left.calculate(vars), self.right.calculate(vars)
        return {"->": not l or r, "|": l or r, "&": l and r}[self.statement]