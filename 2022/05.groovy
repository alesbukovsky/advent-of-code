// puzzle: https://adventofcode.com/2022/day/5

def d = new File(args[0])
    .getText()
    .split('\n\n')

def w1 = [].withDefault{ [] }
def w2 = [].withDefault{ [] }

// CAUTION: stack of crates is represented "horizontally" as a list, with top at its end

d[0].split('\n').dropRight(1).each { l ->
    for (i in 0..8) {
        def c = l.getAt((i * 4) + 1)
        if (!c.isBlank()) {
            w1[i].push(c)
            w2[i].push(c)
        }
    }
}

// CAUTION: stack indexes are adjusted to 0 base for more readable use later

def p = d[1].split('\n').collect { 
        it.tokenize() 
    }
    .collect {
        [ it[1] as int, (it[3] as int) - 1, (it[5] as int) - 1 ]
    } 

p.each { s ->
    for (i in 1..s[0]) {
        w1[s[2]] << w1[s[1]].removeLast()
    }
}

def r1 = w1.collect { it.last() }.join()

println r1  // SBPQRSCDF

p.each { s ->
    w2[s[2]].addAll(w2[s[1]].takeRight(s[0]))
    w2[s[1]] = w2[s[1]].dropRight(s[0])
}

def r2 = w2.collect { it.last() }.join()

println r2  // RGLVRCQSB
