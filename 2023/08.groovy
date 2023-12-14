// puzzle: https://adventofcode.com/2023/day/8

def dt= new File(args[0])
    .getText()
    .split('\n|\n\n')
    .findAll { !it.isEmpty() }

// directions: cyclical list of indexes into the mapping list of next steps (L = 0, R = 1)
def ds = dt[0].collect {'LR'.indexOf(it) }

// routes: maps of current step and its next available steps
def rs = dt.tail().collectEntries {
    def t = it.split(' = ')
    return [ (t[0]) : t[1][1..-2].split(', ') ]
}

def count(ds, rs, f, c) {
    def s = 0
    def i = 0
    def n = f

    while ( !c(n) ) {
        s++
        n = rs[n][ ds[i] ]
        if (++i >= ds.size()) i = 0  // index needs to cycle
    }
    return s
}

//--- part 1 -------------------------------------------------------------

def r1 = count(ds, rs, 'AAA', { it == 'ZZZ' })

println r1 // 19951

//--- part 2 -------------------------------------------------------------

// CAUTION: the following only works because the data input has some specific characteristics.
// the paths are cyclical - when XXZ is reached it continues again with XXA following the same
// steps as before. therefore the solution could be determined as the least common multiple
// (LCM) of number of steps for each individual path.

def gcd(a, b) {
    return (b != 0) ? gcd(b, a % b) : a
}

def lcm(l) {
    // number will eventually grow large, explicit typing is needed to avoid groovy inference problems
    return l.inject(1 as long) { a, v -> (v * a) / gcd(v as long, a as long) }
}

def r2 = lcm( rs.findAll {it.key.endsWith('A') }
        .collect { k, v -> count(ds, rs, k, { it.endsWith('Z') }) }
    )

println r2 // 16342438708751