#!/usr/bin/python
# coding = utf-8


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