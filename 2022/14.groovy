// puzzle: https://adventofcode.com/2022/day/14

import static java.lang.Math.*

// CAUTION: resulting map is not a full grid. each key is "depth" (y coordinate) and value 
// is a list of x coordinates that contain a blocking element (either rock or previously 
// fallen sand). depth level with no block is not represented in the map. x coordinate
// value -1 represents infinite block on that depth level. also, in this model it is not
// possible to distinguish rock from sand.

def load(f) {
    def m = [:]
    new File(f).eachLine { l ->
        def s = l.split(' -> ').collect { it.split(',').collect { n -> n as int} }
        for (def i = 0; i < s.size() - 1; i++) {
            def a = s[i]
            def b = s[i + 1]
            for (def c = min(a[0], b[0]); c <= max(a[0], b[0]); c++) {
                for (def r = min(a[1], b[1]); r <= max(a[1], b[1]); r++) {
                    if (!m.containsKey(r)) m[r] = []
                    m[r] << c
                }
            } 
        }
    }
    return m
}

def empty(m, r, c) {
    return (m[r]?[0] != -1 && !m[r]?.contains(c))
}

def solve(p, m) {
    def (sr, sc, sn) = [0, 500, 0]
    def rx = m.collect { k, v -> k }.max()
    def go = true

    while (go) {
        if ( empty(m, sr + 1, sc) ) {
            sr++
        } else if ( empty(m, sr + 1, sc - 1) ) {
            sr++
            sc--
        } else if ( empty(m, sr + 1, sc + 1) ) {
            sr++
            sc++
        } else {
            if (!m.containsKey(sr)) m[sr] = []
            m[sr] << sc
            sn++
            if (p == 2) go = !(sr == 0 && sc == 500)
            sr = 0
            sc = 500
        }
        if (p == 1) go = (sr <= rx)
    }

    return sn    
}

def m1 = load(args[0])

println solve(1, m1)  // 795

def m2 = load(args[0])
m2[ m2.collect { k, v -> k }.max() + 2 ] = [-1]

println solve(2, m2)  // 30214
