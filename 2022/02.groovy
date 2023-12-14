// puzzle: https://adventofcode.com/2022/day/2

import static java.lang.Math.*

// CAUTION: this code uses a modulus operation to essentially obtain a cyclic index value. 
// Java / Groovy % operator is not suitable for the purpose because of the way it performs 
// the calculation for arguments with different signs.
//
// See: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Math.html#floorMod(int,int)

def d = new File(args[0])
    .getText()
    .split('\n')
    .collect { it.split(' ') }
    .collect {[ 
        'ABC'.indexOf(it[0]),
        'XYZ'.indexOf(it[1])
    ]}

def r1 = d.collect {
        def s = it[1] + 1
        if (it[1] == it[0]) {
            s += 3
        } else if (floorMod(it[1] - it[0], 3) == 1) {
            s += 6
        }
        return s
    }
    .sum()

println r1  // 14069

def r2 = d.collect { 
        def s = it[1] * 3
        s += floorMod(it[1] + it[0] + 2, 3) + 1
        return s
    }
    .sum()

println r2  // 12411