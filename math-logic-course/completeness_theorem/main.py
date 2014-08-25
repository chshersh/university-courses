#!/usr/bin/python
# coding = utf-8
__author__ = 'Dmitriy Kovanikov'

from template_proofs import *


def generate_subsets(container, k, current):
    if k == len(container):
        return [current]
    return generate_subsets(container, k + 1, current + [(container[k], False)]) + \
        generate_subsets(container, k + 1, current + [(container[k], True)])


def quick_proof_checker(root, variables):
    for axiom in axiom_node:
        if Tree.is_same(axiom, root, root):
            return True, Proof(variables, [str(root)])
    for quick_node in quick_nodes:
        if Tree.is_same(quick_node, root, root):
            templ = str(quick_node)
            return True, Proof(variables, quick_dict[templ].replace(quick_variables[templ](root)))
    for complex_node in complex_nodes:
        if Tree.is_same(complex_node, root, root):
            templ = str(complex_node)
            return True, Proof(variables, complex_dict[templ].replace(*complex_variables[templ](root)))
    return False, None


def generate_proof(root, variables):
    quick = quick_proof_checker(root, variables)
    if quick[0]:
        return quick[1]
    if root.is_variable:
        return Proof(variables, [root.statement])
    elif root.statement == "!":
        if root.right.is_variable:
            return Proof(variables, ["!" + root.right.statement])
        elif root.right.statement == "!":
            root_proof = generate_proof(root.right.right, variables)
            return root_proof.add_list(Different.DOUBLE_INVERSION.replace(str(root.right.right)))
        else:
            return generate_proof(root.right, variables)

    left_val, right_val = root.left.calculate(dict(variables)), root.right.calculate(dict(variables))
    root_proof = Proof(variables, [])

    if left_val:
        root_proof.add_proof(generate_proof(root.left, variables))
    else:
        root_proof.add_proof(generate_proof(parser.parse("!(" + str(root.left) + ")"), variables))

    if right_val:
        root_proof.add_proof(generate_proof(root.right, variables))
    else:
        root_proof.add_proof(generate_proof(parser.parse("!(" + str(root.right) + ")"), variables))

    return root_proof.add_list(binary_switcher(root.statement, left_val, right_val,
                               "(" + str(root.left) + ")", "(" + str(root.right) + ")", root))


def compose_proof(input_file, output_file):
    inp = open(input_file,  "r", encoding="utf-8")
    out = open(output_file, "w", encoding="utf-8")

    parser.statement = "(" + inp.readline().rstrip() + ")"
    state_node = parser.parse(parser.statement)
    first = str(state_node)
    variable_sets = generate_subsets(state_node.variables(), 0, [])

    always_true, false_set = True, None
    for subset in variable_sets:
        always_true = state_node.calculate(dict(subset))
        if not always_true:
            false_set = subset
            break

    if not always_true:
        out.write("Высказываие ложно при " + ",".join([t[0] + ("=Л", "=И")[t[1]] for t in false_set]))
    else:
        quick = quick_proof_checker(state_node, [])
        if quick[0]:
            proof_sets = [quick[1]]
        else:
            proof_sets = [generate_proof(state_node, subset) for subset in variable_sets]
            while len(proof_sets) > 1:
                proof_sets = [Merger.merge(proof_sets[2 * i], proof_sets[2 * i + 1]) for i in range(len(proof_sets) // 2)]

        for line in proof_sets[0].text:
            out.write(line + "\n")
            if first == line:
                break

    inp.close()
    out.close()

if __name__ == "__main__":
    input_file_name, output_file_name = {
        1: ("a.in", "a.out"),
        2: (sys.argv[1] if len(sys.argv) > 1 else None, "a.out"),
        3: (sys.argv[1] if len(sys.argv) > 1 else None, sys.argv[2] if len(sys.argv) > 2 else None)
    }[len(sys.argv)]

    compose_proof(input_file_name, output_file_name)