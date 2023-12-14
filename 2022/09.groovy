// puzzle: https://adventofcode.com/2022/day/9

import static java.lang.Math.*

def add(a, b) {
    return [ a[0] + b[0], a[1] + b[1] ]
}

def move(d) {
    return [
        (d == 'R') ? 1 : ((d == 'L') ? -1 : 0),
        (d == 'U') ? 1 : ((d == 'D') ? -1 : 0)
    ]   
}

def plot(a, b) {
    def z = [ a[0] - b[0], a[1] - b[1] ]
    if (abs(z[0]) > 1 || abs(z[1]) > 1) {
        if (z[0] == 0) {
            return [0, floorDiv(z[1], 2)]
        } else if (z[1] == 0) {
            return [floorDiv(z[0], 2), 0]
        } else {
            return [(z[0] > 0) ? 1 : -1, (z[1] > 0) ? 1 : -1]
        }
    } 
    return [0, 0]
}

def h = [0, 0]
def t = [0, 0]
def v = []

new File(args[0]).eachLine { l ->
    def (d, s) = l.split()
    for (def i in 1..(s as int)) {
        h = add(h, move(d))
        t = add(t, plot(h, t))
        if (!v.contains(t)) v << List.copyOf(t)
    }
}

println v.size()  // 6391

h = [0, 0]
t = []
9.times { t << [0, 0] }
v = []

new File(args[0]).eachLine { l ->
    def (d, s) = l.split()
    for (def i in 1..(s as int)) {
        h = add(h, move(d))
        def p = h
        for (def j in 0..8) {
            t[j] = add(t[j], plot(p, t[j]))
            p = t[j]
        }        
        if (!v.contains(t[8])) v << List.copyOf(t[8])
    }
}

println v.size()  // 2593