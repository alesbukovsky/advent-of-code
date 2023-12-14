// puzzle: https://adventofcode.com/2022/day/1

def d = new File(args[0])
    .getText()
    .split('\n\n')
    .collect { 
        it.split('\n')
        .collect { it as int }
        .sum() 
    }

def r1 = d.max()
println r1  // 67658

def r2 = d.sort().takeRight(3).sum()
println r2  // 200158