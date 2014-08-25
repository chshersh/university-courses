from tree_class import Tree
signs = ["->", "|", "&"]


def parse(state):
    for sign in signs:
        if sign[0] == '-':
            pos = 0
            while True:
                if pos == len(state) or state[pos] == sign[0]:
                    break
                if state[pos] == '(':
                    pos, balance = pos + 1, 1
                    while pos < len(state) and balance > 0:
                        balance += state[pos] == '(' and 1 or state[pos] == ')' and -1 or 0
                        pos += 1
                else:
                    pos += 1
            if pos != len(state):
                return Tree(sign, False, parse(state[:pos]), parse(state[pos + 2:]))
        else:
            pos = len(state) - 1
            while True:
                if pos == -1 or state[pos] == sign[0]:
                    break
                if state[pos] == ')':
                    pos, balance = pos - 1, -1
                    while pos >= 0 > balance:
                        balance += state[pos] == '(' and 1 or state[pos] == ')' and -1 or 0
                        pos -= 1
                else:
                    pos -= 1
            if pos != -1:
                return Tree(sign, False, parse(state[:pos]), parse(state[pos + 1:]))

    st = state[0]
    if st == '(':
        return parse(state[1:-1])
    elif st == '!':
        return Tree("!", False, None, parse(state[1:]))
    else:
        return Tree(state, True, None, None)
