#!/usr/bin/python
# coding = utf-8

import sys
import re


class Tree:
    def __init__(self, statement, is_variable, left, right):
        self.statement = statement
        self.is_variable = is_variable
        self.left = left
        self.right = right
        self.path = ""

    def __str__(self):
        if self.is_variable:
            return self.statement

        l = ""
        if self.left is not None:
            l = str(self.left)
        r = str(self.right)

        prog = re.compile("->")
        if l and prog.search(l): l = "(" + l + ")"
        if prog.search(r): r = "(" + r + ")"

        return l + self.statement + r

    def __eq__(self, other):
        return str(self) == str(other)

    def __hash__(self):
        return str(self).__hash__()

    def create_paths(self):
        links = ["", "", ""]
        def thread_tree(node, path):
            if not node: return
            elif not node.is_variable:
                thread_tree(node.left, path + '0')
                thread_tree(node.right, path + '1')
            else:
                pos = ord(node.statement[0]) - ord('A')
                if links[pos]: node.path = links[pos]
                else: links[pos] = path
        thread_tree(self, "")

    def walk_path(self, path):
        node = self
        for direction in path:
            if not node: return None
            node = (node.left, node.right)[direction == '1']
        return node

    @staticmethod
    def is_same(template, cur, root):
        if not template: return True
        elif not cur: return False
        elif template.is_variable: return True if not template.path else root.walk_path(template.path) == cur
        else:
            return template.statement == cur.statement and \
                   Tree.is_same(template.left, cur.left, root) and \
                   Tree.is_same(template.right, cur.right, root)

is_variable = True
signs = ["->", "|", "&"]


def term_skip(state, stop_character):
    pos = 0
    while pos < len(state):
        if state[pos] == stop_character: return pos
        if state[pos] == '(':
            pos, balance = pos + 1, 1
            while pos < len(state) and balance > 0:
                balance += state[pos] == '(' and 1 or state[pos] == ')' and -1 or 0
                pos += 1
        else: pos += 1
    return 0  # can't stop at zero position


def backterm_skip(state, stop_character):
    pos = len(state) - 1
    while pos >= 0:
        if state[pos] == stop_character: return pos
        if state[pos] == ')':
            pos, balance = pos - 1, -1
            while pos >= 0 and balance < 0:
                balance += state[pos] == '(' and 1 or state[pos] == ')' and -1 or 0
                pos -= 1
        else: pos -= 1
    return 0  # can't stop at zero position


def parse(state):
    for sign in signs:
        if sign != "->":
            pos = backterm_skip(state, sign[0])
        else:
            pos = term_skip(state, sign[0])
        if pos: return Tree(sign, not is_variable, parse(state[:pos]), parse(state[pos + len(sign):]))
    # bracket or var here
    st = state[0]
    if st == '(': return parse(state[1:-1])
    elif st == '!': return Tree("!", not is_variable, None, parse(state[1:]))
    else: return Tree(state, is_variable, None, None)

axioms = [
    "A->(B->A)",
    "(A->B)->(A->B->C)->(A->C)",
    "A->B->A&B",
    "A&B->A",
    "A&B->B",
    "A->A|B",
    "B->A|B",
    "(A->C)->(B->C)->(A|B->C)",
    "(A->B)->(A->!B)->!A",
    "!!A->A"
]
alpha, beta, alpha_node = "", "", None
past_set, used_set = set(), set()
axiom_node, hypothesis_node = [], []


def prebuild(line):
    for axiom in axioms:
        axiom_node.append(parse(axiom))
        axiom_node[-1].create_paths()

    global alpha, beta, alpha_node, hypothesis_node
    beta = '(' + line[line.index("|-") + 2:].rstrip() + ')'
    rules = line[:line.index("|-")].split(",")
    alpha = '(' + str(parse(rules[-1])) + ')'
    alpha_node = parse(alpha)
    hypothesis_node = [parse(rules[i]) for i in range(len(rules) - 1)]


def modus_ponens(statement):
    for rule_node in past_set:
        search_rule = parse("(" + str(rule_node) + ")->(" + statement + ")")
        if search_rule in past_set: return rule_node
    return None


def rebuild_proof(input_file_name, output_file_name):
    inp = open(input_file_name,  "r", encoding="utf-8")
    out = open(output_file_name, "w", encoding="utf-8")

    res = ""
    glist = inp.readlines()
    prebuild(glist.pop(0))

    for line in glist:
        node = parse(line.rstrip())
        im_prove = "(" + str(node) + ")"

        axiom_used, assume_used, hypothesis_used = False, False, ""
        for axiom in axiom_node:
            axiom_used |= Tree.is_same(axiom, node, node)
            assume_used |= axiom_used
            if assume_used:
                break

        if not axiom_used:
            for hypothesis in hypothesis_node:
                assume_used = node == hypothesis
                if assume_used:
                    hypothesis_used = hypothesis
                    break

        if assume_used:
            res += "{1}\n" \
                   "{1}->({0}->{1})\n" \
                   "{0}->{1}\n".format(alpha, im_prove)
            if not axiom_used: used_set.add(hypothesis_used)
        elif node == alpha_node:
            res += "{0}->({0}->{0})\n" \
                   "({0}->({0}->{0}))->({0}->(({0}->{0})->{0}))->({0}->{0})\n" \
                   "({0}->(({0}->{0})->{0}))->({0}->{0})\n" \
                   "{0}->(({0}->{0})->{0})\n" \
                   "{0}->{0}\n".format(alpha)
        else:
            prev_node = modus_ponens(im_prove)
            if prev_node:
                prev = "(" + str(prev_node) + ")"
                res += "({0}->{1})->(({0}->({1}->{2}))->({0}->{2}))\n" \
                       "({0}->({1}->{2}))->({0}->{2})\n" \
                       "{0}->{2}\n".format(alpha, prev, im_prove)
            else:
                out.write("Вывод некорректен со строки {0}.".format(glist.index(line) + 1))
                print(res)
                inp.close()
                out.close()
                sys.exit()
                break
        past_set.add(node)

    used_list = list(hypothesis_used)
    for i in range(len(used_list) - 1):
        out.write(used_list[i] + ", ")

    if used_list:
        out.write(used_list[-1])
    out.write("|-" + alpha + "->" + beta + "\n")
    out.write(res)

    inp.close()
    out.close()

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("run: \"main.py inputFileName outputFileName\"")
        sys.exit(0)
    rebuild_proof(sys.argv[1], sys.argv[2])