#!/usr/bin/python
# coding = utf-8

import sys
import parser
from tree_class import Tree

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

past_string_set = set()
axiom_node = []
for index, axiom in enumerate(axioms):
    axiom_node.append(parser.parse(axiom))
    if index != 3 and index != 4:
        axiom_node[-1].create_paths()
axiom_node[3].left.left.path = "1"
axiom_node[4].left.right.path = "1"


def modus_ponens(statement):
    for rule_string in past_string_set:
        search_rule = str(parser.parse("(" + rule_string + ")->(" + statement + ")"))
        if search_rule in past_string_set:
            return rule_string
    return None


def check_proof(input_file_name, output_file_name):
    inp = open(input_file_name,  "r", encoding="utf-8")
    out = open(output_file_name, "w", encoding="utf-8")
    glist = inp.readlines()

    all_ok = True
    j = 0
    for line in glist:
        j += 1
        if line == "":
            continue
        node = parser.parse(line.rstrip())

        cur_ok = False
        for axiom in axiom_node:
            cur_ok = Tree.is_same(axiom, node, node)
            if cur_ok:
                break

        if not cur_ok:
            prev_node = modus_ponens(str(node))
            cur_ok = prev_node is not None

        if not cur_ok:
            all_ok = False
            break

        past_string_set.add(str(node))

    if all_ok:
        out.write("Доказательство корректно.")
    else:
        out.write("Доказательство некорректно со строки {0}.".format(j))

    inp.close()
    out.close()

if __name__ == "__main__":
    input_file_name, output_file_name = {
        1: ("a.in", "a.out"),
        2: (sys.argv[1] if len(sys.argv) > 1 else None, "a.out"),
        3: (sys.argv[1] if len(sys.argv) > 1 else None, sys.argv[2] if len(sys.argv) > 2 else None)
    }[len(sys.argv)]
    check_proof(input_file_name, output_file_name)