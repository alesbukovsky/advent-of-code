// puzzle: https://adventofcode.com/2022/day/7

def root = [:]

// CAUTION: current directory is on top of the position stack

def pos = new Stack()
pos.push(root)

new File(args[0]).eachLine { l ->
    def (x, y, z) = l.tokenize()
    if (x == '$') {
        if (y == 'cd') {
            if (z == '/') {
                pos.clear()        
                pos.push(root)
            } else if (z == '..') {
                pos.pop()
            } else {
                pos.push(pos.peek()[z])
            }
        }
    } else {
        if (x == 'dir') {
            pos.peek()[y] = [:]
        } else {
            pos.peek()[y] = x as int
        }
    }
}

def size(m) {
    return m.collect { k, v -> (v instanceof Map) ? size(v) : v }.sum()
}

def seek1(m, l) {
    def s = size(m)
    def n = m.collect { k, v -> (v instanceof Map) ? seek1(v, l) : 0 }.sum()
    return (s <= l ? s : 0) + n
}

println seek1(root, 100000)  // 1723892

def seek2(m, ms) {
    return [ size(m) ]
        .plus(m.findAll { k, v -> v instanceof Map }.collect { k, v -> seek2(v, ms) }.flatten())
        .findAll{ it > ms }
        .min()
}

def fs = 70000000
def ns = 30000000
def ms = ns - (fs - size(root))

println seek2(root, ms)  // 8474158