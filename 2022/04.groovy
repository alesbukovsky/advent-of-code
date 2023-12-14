// puzzle: https://adventofcode.com/2022/day/4

def d = new File(args[0])
    .getText()
    .split('\n')
    .collect {
        it.tokenize(',-').collect { it as int}
    } 

def r1 = d.count { n ->
        (n[0] <= n[2] && n[3] <= n[1]) || (n[2] <= n[0] && n[1] <= n[3])
    }

println r1  // 547

def r2 = d.count { n ->
        !(n[1] < n[2]) && !(n[3] < n[0])
    }

println r2  // 843
