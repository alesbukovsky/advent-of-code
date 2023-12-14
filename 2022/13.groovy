// puzzle: https://adventofcode.com/2022/day/13

import static java.lang.Math.*

def ps1 = []

new File(args[0]).getText().split('\n\n').each {
    def s = it.split('\n')
    ps1 << [ Eval.me(s[0]), Eval.me(s[1]) ]
}

// returns standard compareTo() result: -1 (a < b), 0 (a = b) and 1 (a > b) 

def cmp(a, b) {
    def la = (a instanceof List)
    def lb = (b instanceof List)

    if (!la && !lb) {
        return a <=> b

    } else if (!la && lb) {
        return cmp([a], b)

    } else if (la && !lb) {
        return cmp(a, [b])

    } else {
        def len = min(a.size(), b.size())
        def res = 0
        for (def i = 0; (i < len && res == 0); i++) {
            res = cmp(a[i], b[i])
        }
        return (res == 0) ? a.size() <=> b.size() : res
    }
}

def r1 = ps1.withIndex().collect { p, i -> (cmp(p[0], p[1]) <= -1) ? i + 1 : 0 }.sum()

println r1  // 6369

ps2 = ps1.collectMany{ it }
ps2 << [[2]]
ps2 << [[6]]

def r2 = ps2.sort { a, b -> cmp(a, b) }.findIndexValues { it == [[2]] || it == [[6]] }.collect { it + 1 }

println r2[0] * r2[1]  // 25800
