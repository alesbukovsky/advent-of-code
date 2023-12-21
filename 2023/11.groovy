// puzzle: https://adventofcode.com/2023/day/11

import static java.lang.Math.*

dt = new File(args[0]).getText().split('\n')

// galaxies: list of two-value lists (row, column)
gs = []
dt.eachWithIndex { s, r ->
    s.eachWithIndex{ t, c ->
        if (t == '#') gs << [r, c]
    }
}

// empty spaces: list of rows and list of columns
es = [
    (0..(dt.size() - 1)) - gs.collect { it[0] }.unique(),
    (0..(dt[0].size() - 1)) - gs.collect { it[1] }.unique()
]

def dist(a, b, f) {
    // explicit types, part 2 numbers are big
    def (long ra, long ca) = a
    def (long rb, long cb) = b

    // expansion factors: based on the number of empty rows and columns within
    // the range of given two points. needs to be mix / max to cover both possible
    // directions. and it's -1 for the original empty row / column in the input.

    long rz = (f - 1) * es[0].count { it in (min(ra, rb)..max(ra, rb)) }
    long cz = (f - 1) * es[1].count { it in (min(ca, cb)..max(ca, cb)) }

    // manhattan geometry formula plus the expansion factors
    // https://en.wikipedia.org/wiki/Taxicab_geometry)
    return abs(ra - rb) + abs(ca - cb) + rz + cz
}

def sum(f) {
    long d = 0
    for (a in (0..(gs.size() - 2))) {
        for (b in ((a + 1)..(gs.size() - 1))) {
            d += dist(gs[a], gs[b], f)
        }
    }
    return d
}

//--- part 1 -------------------------------------------------------------

println sum(2)  // 9214785

//--- part 2 -------------------------------------------------------------

println sum(1000000)  // 613686987427
