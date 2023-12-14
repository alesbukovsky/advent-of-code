// puzzle: https://adventofcode.com/2022/day/3

def d = new File(args[0])
    .getText()
    .split('\n') 

def r1 = d.collect {[ 
        it.substring(0, (it.size() / 2) as int) as Set, 
        it.substring((it.size() / 2) as int) as Set
    ]}
    .collect {
        it[0].intersect(it[1])
    }
    .collect {
        def c = it[0].charAt(0) as int
        (c > 96) ? c - 96 : c - 38
    }
    .sum()

println r1  // 7863

def r2 = d.collate(3)
    .collect { 
        (it[0] as Set).intersect( (it[1] as Set) ).intersect( (it[2] as Set) ) 
    }
    .collect {
        def c = it[0].charAt(0) as int
        (c > 96) ? c - 96 : c - 38
    }
    .sum()

println r2  // 2488