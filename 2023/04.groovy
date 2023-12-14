// puzzle: https://adventofcode.com/2023/day/4

// matches: list of counts of matching numbers (one per card)
def ms = new File(args[0])
    .getText()
    .split('\n')
    .collect { s ->
        def t = (s.split(':')[1]).split('\\|')
        return [
            t[0].trim().split(' +').collect { Integer.parseInt(it.trim()) },
            t[1].trim().split(' +').collect { Integer.parseInt(it.trim()) },
        ]
    }
    .collect { c ->
        c[0].intersect(c[1]).size()
    }

//--- part 1 -------------------------------------------------------------

def r1 = ms.collect {it > 0 ? 2 ** (it - 1) : 0 }.sum()

println r1 // 21558

//--- part 2 -------------------------------------------------------------

def cs = ms.collect { [ it, 1 ] }

for (def i = 0; i < cs.size(); i++) {
    for (def j = 1; j <= cs[i][0]; j++) {
        if (i + j < cs.size()) {
            cs[i + j][1] += cs[i][1]
        }
    }
}

def r2 = cs.collect { it[1] }.sum()

println r2 // 10425665