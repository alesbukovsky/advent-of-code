// puzzle: https://adventofcode.com/2022/day/15

import static java.lang.Math.*

def cover(dt, y) {
    def ct = []  // temporary cover intervals
    def bs = []  // known beacons 

    dt.each {
        def (sx, sy, bx, by) = it

        def df = abs(sx - bx) + abs(sy - by)
        def dr = df - abs(sy - y)

        if (dr >= 0) {
            ct << [sx - dr, sx + dr]
            if (y == by && !bs.contains(bx)) {
                bs << bx
            }
        }
    }

    def cs = []  // final cover intervals (sorted & merged)
    
    // CAUTION: for some strange reason the implicit sort() fails to sort the 
    // intervals based on x coordinate if numbers are substantially large and/or 
    // of long type. an explicit sort with a closure is necessary here.

    if (ct.size() > 0) {
        ct = ct.sort { a, b -> a[0] <=> b[0] }
        cs << ct.pop()
        ct.each { c ->
            if (c[0] > cs[-1][1] + 1) {
                cs << c
            } else {
                cs[-1][1] = max(cs[-1][1], c[1])
            }
        }
    }

    return [cs, bs]
}

def dt = []
new File(args[0]).eachLine { l ->
    def (sx, sy, bx, by) = (l =~ /-?\d+/).findAll().collect { it as long }
    dt << [sx, sy, bx, by]
}

def (c1, b1) = cover(dt, 2000000)
def r1 = c1.collect { it[1] - it[0] + 1 }.sum() - b1.size()

println r1  // 4725496

def ar = 4000000
def r2 = []

for (def y = 0; y <= ar; y++) {
    def (c2, _) = cover(dt, y)
    def x = 0
    for (i = 0; i < c2.size(); i++) {
        def (xl, xh) = c2[i]
        if (x < xl) {
            r2 = [x, y]
            break
        }
        x = max(x, xh + 1)
        if (x > ar) break
    }
    if (!r2.isEmpty()) break
}

println ((r2[0] * 4000000) + r2[1])  // 12051287042458
