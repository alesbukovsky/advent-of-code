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

// starting position (row, column)
sr = dt.findIndexOf { it.contains('S') }
sc = dt[sr].indexOf('S')

def off(r, c, d) {
    return [ r + ofs[d][0], c + ofs[d][1] ]
}

def ok(r, c, d) {
    (r, c) = off(r, c, d)
    if (r < 0 || r > dt.size() - 1 || c < 0 || c > dt[0].size() - 1) {
        return false  // offset position is out of bounds
    }
    return drs[d].find { it.key == dt[r][c] } != null
}

// check validity of next tile in all four directions from start
oks = []
for (d in (0..3)) { oks << ok(sr, sc, d) }

// determine initial direction: any of the two valid ones is fine to use.
// also, replace the starting S tile with corresponding type. this is needed
// for part 2 to correctly detect a corner or boundary.

dr = -1
if ( oks[RIGHT] && oks[LEFT] ) { dr = RIGHT; dt[sr][sc] = '-' }
if ( oks[RIGHT] && oks[DOWN] ) { dr = RIGHT; dt[sr][sc] = 'F' }
if ( oks[RIGHT] && oks[UP  ] ) { dr = RIGHT; dt[sr][sc] = 'L' }
if ( oks[LEFT ] && oks[UP  ] ) { dr = LEFT;  dt[sr][sc] = 'J' }
if ( oks[LEFT ] && oks[DOWN] ) { dr = LEFT;  dt[sr][sc] = '7' }
if ( oks[DOWN ] && oks[UP  ] ) { dr = DOWN ; dt[sr][sc] = '|' }
assert dr > -1

// history of visited positions, map of rows (key) with list of columns (value)
hst = [:]

//--- part 1 -------------------------------------------------------------

cr = sr
cc = sc

// keep crawling thru the maze until the start position is reached again
do {
    // add current position into history list
    if (!hst.containsKey(cr)) hst << [ (cr): [] ]
    hst[cr] << cc

    // calculate next position and direction
    (cr, cc) = off(cr, cc, dr)
    dr = drs[dr][ dt[cr][cc] ]
} while ( cr != sr || cc != sc )

// farthest tile is the loop length halved and rounded up
def r1 = hst.inject(0) { a, k, v -> a + v.size() }.intdiv(2)

println r1  // 6820

//--- part 2 -------------------------------------------------------------

// replace all unvisited tiles with "ground". this is not strictly necessary as it could be
// checked later on. but it makes the visualization and the processing code easier to read.

for (r in (0..dt.size() - 1)) {
    for (c in (0..dt[r].size() - 1)) {
        if ( !hst.containsKey(r) || !hst[r].contains(c) ) dt[r][c] = '.'
    }
}

// scan each line from left to right. odd vertical path tile marks a beginning of the inside
// region, even tile means an end of it. there is a special case of an s-curve: F-J or L-7
// (with any number of horizontal path tiles in between the corners). the whole sequence needs
// to be treated as a vertical path tile.

def r2 = 0

for (r in (0..dt.size() - 1)) {

    def ins = false  // inside vs. outside toggle
    def crn = null   // detected possible s-curve corner

    for (c in (0..dt[r].size() - 1)) {
        def t = dt[r][c]

        // possible s-curve starting corner detected
        if (!crn && t in ['F', 'L']) { crn = t; continue }

        // if possible s-curve starting corner is known, assess the sequence
        if (crn) {
            // horizontal path tile is fine
            if (t == '-') {
                continue
            }
            // corresponding ending corner completes the s-curve and toggles region
            if ((t == 'J' && crn == 'F') || (t == '7' && crn == 'L')) {
                ins = !ins
                crn = null
                continue
            }
            // any other tile means it is not a proper s-curve
            crn = null
        }

        // vertical path tile just toggles region
        if (t == '|') { ins = !ins; continue }

        // any ground tile within inside region counts toward the result
        if (t == '.' && ins) r2++
    }
}

println r2  // 337
