// puzzle: https://adventofcode.com/2022/day/10

import static java.lang.Math.*

def cs = [1]

new File(args[0]).eachLine { l-> 
    def i = l.split()
    if (i[0] == 'noop' || i[0] == 'addx') {
        cs << cs.last()
        if (i[0] == 'addx') {
            cs << cs.last() + (i[1] as int)
        }
    }
}

// CAUTION: cs[N] is register's value after the instruction completes, the value 
// during the instruction execution is the previous one, i.e. cs[N-1]

def ps = [20, 60, 100, 140, 180, 220]

println ps.collect { it * cs[it - 1] }.sum()  // 13220

def w = 40

cs.withIndex().each { v, i ->
    def x = i - (floorDiv(i, w) * w)
    print ((cs[i] - 1 <= x && x <= cs[i] + 1) ? '#' : ' ')
    if ((i + 1) % w == 0) print '\n'
}

// RUAKHBEK
