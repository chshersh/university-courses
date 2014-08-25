#!/usr/bin/python
# coding = utf-8

import sys
import parser
from additional_classes import *

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
quick_proofs = [
    "A|!A",
    "A->A",
    "A->!!A"
]
complex_proofs = [
    "(A->B)->!B->!A"
]
a, ba = "", ""
past_string_set = set()
assumptions, axiom_node, quick_nodes, complex_nodes = [], [], [], []

for index, axiom in enumerate(axioms):
    axiom_node.append(parser.parse(axiom))
    if index != 3 and index != 4:
        axiom_node[-1].create_paths()
axiom_node[3].left.left.path = "1"
axiom_node[4].left.right.path = "1"

for quick_proof in quick_proofs:
    quick_nodes.append(parser.parse(quick_proof))
    quick_nodes[-1].create_paths()

for complex_proof in complex_proofs:
    complex_nodes.append(parser.parse(complex_proof))
    complex_nodes[-1].create_paths()


def construct_new_proof(old_proof):
    global a, ba, assumptions, past_string_set

    last = old_proof.additional_axioms.pop()  # remove and return last element in list
    a = str(parser.parse(("!", "")[last[1]] + last[0]))
    ba = "(" + a + ")"

    assumptions = [str(parser.parse(("!", "")[pair[1]] + pair[0])) for pair in old_proof.additional_axioms]
    past_string_set = set()

    return Proof(old_proof.additional_axioms, [])


def modus_ponens(statement):
    for rule_string in past_string_set:
        search_rule = str(parser.parse("(" + rule_string + ")->(" + statement + ")"))
        if search_rule in past_string_set:
            return rule_string
    return None


def rebuild_proof(old_proof):
    new_proof = construct_new_proof(old_proof)
    for line in old_proof.text:
        node = parser.parse(line)

        axiom_find = False
        for axiom in axiom_node:
            axiom_find = Tree.is_same(axiom, node, node)
            if axiom_find: break

        for assumption in assumptions:
            axiom_find |= line == assumption

        if axiom_find:
            new_proof.add_list(Deduction.AXIOM.replace(ba, "(" + line + ")"))
        elif line == a:
            new_proof.add_list(Deduction.SAME.replace(ba))
        else:
            s = modus_ponens(line)
            if s:
                new_proof.add_list(Deduction.MODUS_PONENS.replace(ba, "(" + s + ")", "(" + line + ")"))
            else:
                print("Неверное доказательство в строке \n {0}".format(line))
                print(str(old_proof))
                sys.exit()

        past_string_set.add(line)
    return new_proof


class Template:
    def __init__(self, template):
        self.template = template

    def to_template(self, *variables):
        return [str(parser.parse(line.format(*variables))) for line in self.template]

    def replace(self, *variables):
        return self.to_template(*variables)


class Deduction:
    AXIOM = Template([
        "{1}",
        "{1}->{0}->{1}",
        "{0}->{1}"
    ])
    SAME = Template([
        "{0}->({0}->{0})",
        "({0}->({0}->{0}))->({0}->(({0}->{0})->{0}))->({0}->{0})",
        "({0}->(({0}->{0})->{0}))->({0}->{0})",
        "({0}->(({0}->{0})->{0}))",
        "{0}->{0}"
    ])
    MODUS_PONENS = Template([
        "({0}->{1})->(({0}->({1}->{2}))->({0}->{2}))",
        "({0}->({1}->{2}))->({0}->{2})",
        "{0}->{2}"
    ])


class Different:
    class DoubleInversion(Template):
        def replace(self, *variables):
            return Deduction.SAME.replace("!" + variables[0]) + self.to_template(*variables)

    DOUBLE_INVERSION = DoubleInversion([
        #  a |- !!a
        ## !a -> !a
        "{0}",
        "{0}->(!{0}->{0})",
        "(!{0}->{0})->(!{0}->!{0})->!!{0}",
        "!{0}->{0}",
        "(!{0}->!{0})->!!{0}",
        "!!{0}"
    ])

    class DoubleInversionImplication(Template):
        def replace(self, *variables):
            return rebuild_proof(Proof([(variables[0], True)], Different.DOUBLE_INVERSION.replace(*variables))).text
    # a -> !!a
    DOUBLE_INVERSION_IMPLICATION = DoubleInversionImplication([])

    class ForwardImplication(Template):
        def replace(self, *variables):
            res_proof = Proof(
                [(variables[0], True), ("({0}->{1})".format(*variables), True)],
                self.to_template(*variables)
            )
            return rebuild_proof(res_proof).text

    FORWARD_IMPLICATION = ForwardImplication([
        # a, a->b |- b to (a->b)->b
        "{0}",
        "{0}->{1}",
        "{1}"
    ])

    class Contrposition(Template):
        def replace(self, *variables):
            res_proof = Proof(
                [("{0}->{1}".format(*variables), True), ("!{1}".format(*variables), True)],
                self.to_template(*variables)
            )
            return rebuild_proof(rebuild_proof(res_proof)).text

    CONTRPOSITION = Contrposition([
        "({0}->{1})->({0}->!{1})->!{0}",
        "{0}->{1}",
        "({0}->!{1})->!{0}",
        "!{1}->({0}->!{1})",
        "!{1}",
        "{0}->!{1}",
        "!{0}"
    ])

    class ThirdExcess(Template):
        def replace(self, *variables):
            a, b = variables[0], "({0}|!{0})".format(*variables)
            return Different.CONTRPOSITION.replace(a, b) + \
                   Different.CONTRPOSITION.replace("!" + a, b) + \
                   self.to_template(a)

    THIRD_EXCESS = ThirdExcess([
        "{0}->{0}|!{0}",
        "!({0}|!{0})->!{0}",  # CONTRPOSITION

        "!{0}->{0}|!{0}",
        "!({0}|!{0})->!!{0}",  # CONTRPOSITION

        "(!({0}|!{0})->!{0})->(!({0}|!{0})->!!{0})->!!({0}|!{0})",
        "(!({0}|!{0})->!!{0})->!!({0}|!{0})",
        "!!({0}|!{0})",
        "!!({0}|!{0})->({0}|!{0})",
        "{0}|!{0}"
    ])

quick_dict = {
    "A|!A": Different.THIRD_EXCESS,
    "A->A": Deduction.SAME,
    "A->!!A": Different.DOUBLE_INVERSION_IMPLICATION
}

quick_variables = {
    "A|!A": lambda r: (str(r.left)),
    "A->A": lambda r: (str(r.left)),
    "A->!!A": lambda r: (str(r.left))
}

complex_dict = {
    "(A->B)->!B->!A": Different.CONTRPOSITION
}

complex_variables = {
    "(A->B)->!B->!A": lambda r: (str(r.left.left), str(r.left.right))
}


class Merger:
    MERGE_TEMPLATE = Template([
        "({0}->{1})->(!{0}->{1})->({0}|!{0}->{1})",
        "(!{0}->{1})->({0}|!{0}->{1})",
        "{0}|!{0}->{1}",
        "{1}"
    ])

    @staticmethod
    def merge(first, second):
        last_var = first.additional_axioms[-1][0]  # get last variable from assumption list
        return Proof(first.additional_axioms[:-1], []) \
               .add_proof(rebuild_proof(first)) \
               .add_proof(rebuild_proof(second)) \
               .add_list(Different.THIRD_EXCESS.replace(last_var)) \
               .add_list(Merger.MERGE_TEMPLATE.replace(last_var, parser.statement))


class AND:
    F_F = Template([
        "({0}&{1}->{1})->({0}&{1}->!{1})->!({0}&{1})",
        "({0}&{1}->{1})",
        "({0}&{1}->!{1})->!({0}&{1})",
        "!{1}->({0}&{1}->!{1})",
        "!{1}",
        "({0}&{1}->!{1})",
        "!({0}&{1})"
    ])
    F_T = Template([
        "({0}&{1}->{0})->({0}&{1}->!{0})->!({0}&{1})",
        "({0}&{1}->{0})",
        "({0}&{1}->!{0})->!({0}&{1})",
        "!{0}->({0}&{1}->!{0})",
        "!{0}",
        "({0}&{1}->!{0})",
        "!({0}&{1})"
    ])
    T_F = Template([
        "({0}&{1}->{1})->({0}&{1}->!{1})->!({0}&{1})",
        "({0}&{1}->{1})",
        "({0}&{1}->!{1})->!({0}&{1})",
        "!{1}->({0}&{1}->!{1})",
        "!{1}",
        "({0}&{1}->!{1})",
        "!({0}&{1})"
    ])
    T_T = Template([
        "{0}",
        "{1}",
        "{0}->{1}->{0}&{1}",
        "{1}->{0}&{1}",
        "{0}&{1}"
    ])


class OR:
    class OrFF(Template):
        def replace(self, *variables):
            return Deduction.SAME.replace(variables[0]) + \
                   IMPLICATION.F_F.replace(variables[1], variables[0]) + \
                   self.to_template(variables)

    F_F = OrFF([
        # Deduction.SAME(a)
        # IMPLICATION.F_F(b, a)
        "({0}->{0})->({1}->{0})->({0}|{1}->{0})",
        "({1}->{0})->({0}|{1}->{0})",
        "({0}|{1}->{0})",
        "({0}|{1}->{0})->({0}|{1}->!{0})->!({0}|{1})",
        "({0}|{1}->!{0})->!({0}|{1})",
        "!{0}->({0}|{1}->!{0})",
        "!{0}",
        "{0}|{1}->!{0}",
        "!({0}|{1})"
    ])
    F_T = Template([
        "{1}",
        "{1}->{0}|{1}",
        "{0}|{1}"
    ])
    T_F = Template([
        "{0}",
        "{0}->{0}|{1}",
        "{0}|{1}"
    ])
    T_T = Template([
        "{0}",
        "{0}->{0}|{1}",
        "{0}|{1}"
    ])


class IMPLICATION:
    class ImplicationFF(Template):
        def replace(self, *variables):
            return Different.DOUBLE_INVERSION_IMPLICATION.replace(variables[0]) + \
                   Different.CONTRPOSITION.replace("!" + variables[1], "!" + variables[0]) + \
                   self.to_template(*variables)

    F_F = ImplicationFF([
        # a->!!a
        # (!b->!a) = !!a->!!b -- Contrposition(!b, !a)
        "({0}->!!{0})->({0}->(!!{0}->!!{1}))->({0}->!!{1})",
        "({0}->(!!{0}->!!{1}))->({0}->!!{1})",
        "(!!{0}->!!{1})->({0}->(!!{0}->!!{1}))",
        "!{0}->(!{1}->!{0})",
        "!{0}",
        "!{1}->!{0}",
        "!!{0}->!!{1}",
        "{0}->(!!{0}->!!{1})",
        "{0}->!!{1}",
        "({0}->!!{1})->({0}->(!!{1}->{1}))->({0}->{1})",
        "({0}->(!!{1}->{1}))->({0}->{1})",
        "(!!{1}->{1})->({0}->(!!{1}->{1}))",
        "!!{1}->{1}",
        "{0}->(!!{1}->{1})",
        "{0}->{1}"
    ])
    F_T = Template([
        "{1}",
        "{1}->({0}->{1})",
        "{0}->{1}"
    ])

    class ImplicationTF(Template):
        def replace(self, *variables):
            return Different.FORWARD_IMPLICATION.replace(*variables) + \
                   Different.CONTRPOSITION.replace("({0}->{1})".format(*variables), variables[1]) + \
                   self.to_template(*variables)

    T_F = ImplicationTF([
        # (a->b)->b -- ForwardImplication
        # ((a->b)->b)->(!b->!(a->b))" -- Contrposition
        "{0}",
        "!{1}->!({0}->{1})",
        "!{1}",
        "!({0}->{1})"
    ])
    T_T = Template([
        "{1}",
        "{1}->({0}->{1})",
        "{0}->{1}"
    ])


def value_switcher(operation_class, l, r, a, b):
    if l and r:
        return operation_class.T_T.replace(a, b)
    elif l and not r:
        return operation_class.T_F.replace(a, b)
    elif not l and r:
        return operation_class.F_T.replace(a, b)
    else:  # not l and not r
        return operation_class.F_F.replace(a, b)


def binary_switcher(operation, l, r, a, b, root):
    if operation == "->":
        return value_switcher(IMPLICATION, l, r, a, b)
    elif operation == "|":
        return value_switcher(OR, l, r, a, b)
    elif operation == "&":
        return value_switcher(AND, l, r, a, b)
    else:
        print("Undefined binary operation")