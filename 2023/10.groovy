// puzzle: https://adventofcode.com/2023/day/10

dt = new File(args[0]).getText().split('\n').collect { it.collect() }

// directional indexes
RIGHT = 0; DOWN = 1; LEFT = 2; UP = 3

// directional index offsets (row, column)
ofs = [ [0, 1], [1, 0], [0, -1], [-1, 0] ]

// next direction based on current direction and encountered tile
drs = [
    [ '-': RIGHT, '7': DOWN , 'J': UP    ],
    [ '|': DOWN,  'L': RIGHT, 'J': LEFT  ],
    [ '-': LEFT,  'L': UP,    'F': DOWN  ],
    [ '|': UP,    '7': LEFT,  'F': RIGHT ]
]

// starting position
st = []
st[0] = dt.findIndexOf { it.contains('S') }
st[1] = dt[ st[0] ].indexOf('S')

def off(c, d) {
    return [ c[0] + ofs[d][0], c[1] + ofs[d][1] ]
}

def ok(c, d) {
    def x = off(c, d)
    if (x[0] < 0 || x[0] > dt.size() - 1 || x[1] < 0 || x[1] > dt[0].size() - 1) {
        return false  // offset position is out of bounds
    }
    return drs[d].find { it.key == dt[ x[0] ][ x[1] ] } != null
}

// determine initial direction: below code is an overkill, it would be sufficient
// to check in three individual directions, e.g RIGHT, LEFT and DOWN. but it allows
// to determine the tile hidden under starting S. note that the direction taken
// could be any of the matching two.

dr = -1
if ( ok(st, RIGHT) && ok(st, LEFT) ) dr = RIGHT  // S = -
if ( ok(st, RIGHT) && ok(st, DOWN) ) dr = RIGHT  // S = F
if ( ok(st, RIGHT) && ok(st, UP  ) ) dr = RIGHT  // S = L
if ( ok(st, LEFT ) && ok(st, UP  ) ) dr = LEFT   // S = J
if ( ok(st, DOWN ) && ok(st, UP  ) ) dr = DOWN   // S = |

// data input is invalid if two valid directions from the start could not be found
assert dr > -1

//--- part 1 -------------------------------------------------------------

cp = st
i = 0

// keep crawling thru the maze until the start is reached again
do {
    i++
    cp = off(cp, dr)
    dr = drs[dr][ dt[ cp[0] ][ cp[1] ] ]
} while ( cp[0] != st[0] || cp[1] != st[1] )

// farthest tile is halved loop size rounded up
def r1 = ((i as int) + 1).intdiv(2)

println r1  // 6820
