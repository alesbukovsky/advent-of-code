// puzzle: https://adventofcode.com/2023/day/1

def dt= new File(args[0])
    .getText()
    .split('\n')

//--- part 1 -------------------------------------------------------------

def r1 = dt.collect {
        def m = it =~ /(\d)/
        return Integer.parseInt(m[0][0] + m[m.getCount() - 1][0])
    }
    .sum()

println r1  // 54968

//--- part 2 -------------------------------------------------------------

def r2 = dt.collect {
        def d = []
        it.eachWithIndex { c, i ->
            if (c.isNumber()) {
                d << Integer.parseInt(c)
            }
            ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'].eachWithIndex { w, j ->
                if (it.substring(i).startsWith(w)) d << j + 1
            }
        }
        return d.first() * 10 + d.last()
    }
    .sum()

println r2  // 54094
