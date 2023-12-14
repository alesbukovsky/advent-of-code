// puzzle: https://adventofcode.com/2023/day/6

import static java.lang.Math.*

// data: list of two-value lists, race time (0), longest distances (1)
def dt= new File(args[0])
    .getText()
    .split('\n')
    .collect {s ->
        s.substring('Distance: '.length()).trim()
            .split('\\s+')
            .collect { Integer.parseInt(it) }
    }

def dist(x, t) {
    return x * (t - x)
}

//--- part 1 -------------------------------------------------------------

def r1 = dt.transpose().collect { r ->
        (0..r[0]).findAll { x -> dist(x, r[0]) > r[1] }.size()
    }
    .inject { a, v -> a * v }

println r1  // 3316275

//--- part 2 -------------------------------------------------------------

// somewhat surprisingly part 2 could also solved in few seconds via the same approach as part 1.

def (long t, long d) = dt.collect {
        Long.parseLong( it.inject { a, v -> "$a$v" } )
    }

def r2 = (0..t).findAll { x -> dist(x, t) > d }.size()

println r2  // 27102791

//--- part 2: binary search (much faster) --------------------------------

// function curve peeks in the middle and is symmetrical
def lo = 0
def hi = floorDiv(t, 2)

while (lo + 1 < hi) {
    // check the middle of the current range
    def m = floorDiv(lo + hi, 2)
    if ( dist(m, t) > d ) {
        hi = m
    } else {
        lo = m
    }
}

// first of winning times (because hi is assigned when search condition is met)
def ft = hi

// last of winning times since curve is symmetrical (can't use floor, .5 for odd "t" needs to count)
def lt = round(t/2 + (t/2 - ft))

// number of winning times is simply the number of them between first and last
def r3 = lt - ft + 1

println r3  // 27102791