// puzzle: https://adventofcode.com/2023/day/7

def dt= new File(args[0])
    .getText()
    .split('\n')
    .collect {s ->
        def h = s.split(' ')
        return [ h[0], Integer.parseInt(h[1]) ]
    }

def type(h, joker) {
    def cs = h.collect()
        .groupBy { (char) it }
        .collectEntries { k, v -> [k, v.size()] }

    // joker logic only apples if there is a joker and at least one other card
    if (joker && cs.containsKey('J' as char) && cs.size() > 1) {

        // type is better with more of the same cards present. shift joker count to most
        // frequent card count (any if there is more options, card value doesn't matter)

        int j = cs.remove('J' as char)
        cs.max { it.value }.value += j
    }

    // CAUTION: order matters for groovy list equality
    cs = cs.collect { it.value }.sort()

    return [
            [1, 1, 1, 1, 1],  // high card: all different cards (23456)
            [1, 1, 1, 2],     // one pair: two same cards, three other different cards (A23A4)
            [1, 2, 2],        // two pair: two sets of two same cards (23432)
            [1, 1, 3],        // three of a kind: three same cards, two other different cards (TTT98)
            [2, 3],           // full house: three same cards, two other same cards (23332)
            [1, 4],           // four of a kind: four same cards (AA8AA)
            [5]               // five of a kind: five same cards (AAAAA)
        ]
        .findIndexOf { it == cs } + 1
}

def ord(c, joker) {
    return (
            joker
            ? ['J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A']
            : ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A']
        )
        .findIndexOf { it == c }
}

def run(hs, joker) {
    return hs.collect { h -> [ h[0], h[1], type(h[0], joker) ] }
        .sort { a, b ->
            // different ranks: just compare
            if (a[2] != b[2]) {
                return a[2] <=> b[2]

            // same ranks: compare card strengths
            } else {
                // find first index where cards are different (assumptions are made, e.g. equal length)
                def i = (0..a[0].length()).find { a[0][it] != b[0][it] }
                return ord(a[0][i], joker) <=> ord(b[0][i], joker)
            }
        }
        .withIndex().sum { h, i -> h[1] * (i + 1) }
}

//--- part 1 -------------------------------------------------------------

def r1 = run(dt, false)

println r1 // 250898830

//--- part 2 -------------------------------------------------------------

def r2 = run(dt, true)

println r2 // 252127335
