// puzzle: https://adventofcode.com/2022/day/8

import static java.lang.Math.*

def ts = []

new File(args[0]).eachLine { l ->
    ts << l.collect { it as int }
}

// CAUTION: line of sight is meant from the tree, i.e. east and north sequences 
// are to be reversed for so the tree is always on its left / start.

def los(d, g, x, y) {
    switch (d) {
        case 'e':
            return g[y].subList(0, x).reverse()
        case 'w':
            return g[y].subList(x + 1, g[y].size())
        case 'n':
            return g.collect { it[x] }.subList(0, y).reverse()
        case 's':
            return g.collect { it[x] }.subList(y + 1, g.size())
    }
}

def r1 = 0

for (def y = 0; y < ts.size(); y++) {
    for (def x = 0; x < ts[0].size(); x++) {
        def t = ts[y][x]
        
        def e = los('e', ts, x, y).every { it < t }
        def w = los('w', ts, x, y).every { it < t }
        def n = los('n', ts, x, y).every { it < t }
        def s = los('s', ts, x, y).every { it < t }

        if (e || w || n || s) {
            r1++
        }
    }
}

println r1  // 1695

def vis(l, h) {
    def c = 0
    for (i = 0; i < l.size(); i++) {
        c++
        if (l[i] >= h) break
    }
    return c
} 

def r2 = 0

for (def y = 0; y < ts.size(); y++) {
    for (def x = 0; x < ts[0].size(); x++) {
        def t = ts[y][x]
        
        def e = vis(los('e', ts, x, y), t)
        def w = vis(los('w', ts, x, y), t)
        def n = vis(los('n', ts, x, y), t)
        def s = vis(los('s', ts, x, y), t)

        r2 = max(r2, e * w * n * s)
    }
}

println r2  // 287040
