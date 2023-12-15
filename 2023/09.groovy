// puzzle: https://adventofcode.com/2023/day/9

def dt= new File(args[0])
    .getText()
    .split('\n')
    .collect { s ->
        s.split(' ').collect{ Integer.parseInt(it) }
    }

// executes recursively, extrapolation of the current line depends on the result of
// the next one. the only difference between parts is how the results are combined.

def ext(ns, c) {
    if (ns.every { it == 0 }) {
        return 0
    }
    hs = []
    1.upto (ns.size() - 1) { i ->
        hs << ns[i] - ns[i - 1]
    }
    return c(ns, ext(hs, c))
}

//--- part 1 -------------------------------------------------------------

def r1 = dt.sum { ext(it, { ns, e -> ns.last() + e }) }

println r1  // 1934898178

//--- part 2 -------------------------------------------------------------

def r2 = dt.sum { ext(it, { ns, e -> ns[0] - e }) }

println r2  // 1129
