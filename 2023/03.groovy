// puzzle: https://adventofcode.com/2023/day/3

// numbers: list of two-value lists, number (0) and list (1) of line (0) and first digit column (1)
def num = []

// symbols: list of two-value lists, symbol (0) and list (1) of line (0) and column (1)
def sym = []

new File(args[0])
    .getText()
    .split('\n')
    .eachWithIndex { s, l ->
        def c = 0
        while (c < s.length()) {
            if (s[c].isNumber()) {
                num << [ s[c], [l, c] ]
                c++
                while (c < s.length() && s[c].isNumber()) {
                    num.last()[0] = num.last()[0] + s[c]
                    c++
                }
            }
            if (c < s.length() && s[c] != '.') {
                sym << [ s[c], [l, c] ]
            }
            c++
        }
    }

//--- part 1 -------------------------------------------------------------

def r1 = num.findAll { n ->
        def hit = false

        def l = n[1][0] -1
        while (!hit && l <= n[1][0] + 1) {

            def c = n[1][1] - 1
            while (!hit && c <= n[1][1] + n[0].length()) {
                hit = (sym.find { s -> s[1][0] == l && s[1][1] == c } != null)
                c++
            }
            l++
        }
        return hit
    }
    .collect { Integer.parseInt(it[0]) }
    .sum()

println r1 // 550064

//--- part 2 -------------------------------------------------------------

r2 = sym.findAll { s ->
        s[0] == '*'
    }
    .collect { g ->
        def gl = [ g[1][0] - 1, g[1][0] + 1 ]
        def gc = [ g[1][1] - 1, g[1][1] + 1 ]

        def ns = num.findAll { n ->
            def nl = n[1][0]
            def nc = [ n[1][1], n[1][1] + n[0].length() - 1 ]
            return ( (gl[0] <= nl && nl <= gl[1]) && (gc[0] <= nc[1] && gc[1] >= nc[0]) )
        }
        return ns.size() == 2 ? Integer.parseInt(ns[0][0]) * Integer.parseInt(ns[1][0]) : 0
    }
    .sum()

println r2 // 85010461