// puzzle: https://adventofcode.com/2022/day/11

import static java.lang.Math.*

// CAUTION: all numbers should be long

def ins = []

// in[0]: starting list of item worry values
// in[1]: single argument (x) formula to calculate new worry value
// in[3]: if divisible by [0] throw to [1] otherwise to [2]

new File(args[0]).getText().split('\n\n').each {
    def s = it.split('\n')
    ins << [
        (s[1] =~ /[\d]+/).findAll().collect { it as long },
        (s[2].substring(s[2].lastIndexOf('=') + 2)).replace('old', 'x'),
        (s[3] + s[4] + s[5] =~ /[\d]+/).findAll().collect { it as long }
    ]
}

// CAUTION: while it is neat to simply call groovy.util.Eval.x(x, f), it's also painfully slow

def eval(f, v) {
    def p = (f =~ /([x]) ([\+\-\*]*) (.*)/).findAll().collect()
    def x = (p[0][3] == 'x') ? v : (p[0][3] as long)
    switch (p[0][2]) {
        case '+': return v + x
        case '-': return v - x
        case '*': return v * x
    }
}

// if not reduced the worry value will grow too big for any decent numeric type. within a context
// of a single monkey worry value could be replaced with it's modulus (completed modulus "loops"
// don't contribute anything since is it divisibility that is checked). to avoid tracking modulus
// value per monkey, least common multiple (here the product of all divisors) is used instead.

def check(n, input, reduce) {
    def ms = input.collect { [ it[0].collect(), 0L ] }

    def lcm = 1
    input.each { lcm *= it[2][0] }
    
    n.times {
        ms.eachWithIndex { m, i ->
            def (_, f, d) = input.getAt(i)
            
            while (!m[0].isEmpty()) {
                def w = (eval(f, m[0].pop()) % lcm)
                if (reduce) w = floorDiv(w, 3)
                def t = (w % d[0] == 0) ? d[1] : d[2]
                ms[t][0] << w
                m[1]++
            }
        }
    }
    return ms.collect { it[1] }
}

def r1 = check(20, ins, true).sort()

println r1[-2] * r1[-1]  // 54036

def r2 = check(10000, ins, false).sort()

println r2[-2] * r2[-1]  // 13237873355
