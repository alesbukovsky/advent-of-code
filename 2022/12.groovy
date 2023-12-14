// puzzle: https://adventofcode.com/2022/day/12

def hm = []
def ps = []
def pe = []

new File(args[0]).getText().split('\n').eachWithIndex { s, r ->
    def cs = s.indexOf('S')
    if (cs >= 0) ps = [r, cs]

    def ce = s.indexOf('E')
    if (ce >= 0) pe = [r, ce]    

    s = s.replace(S:'a', E:'z')
    hm << s.getChars().collect { it }
}

// simplified A* search. the actual path is not needed, only the distance traveled. 
// to accommodate both parts of the puzzle, some condition checks (end reached, 
// passable terrain) are exposed as closures.

def scan(map, start, isEnd, tooSteep) {
    def ol = [[start[0], start[1], 0]]
    def cl = [[start[0], start[1]]]
    
    def dst = -1

    while (!ol.isEmpty() && (dst == -1)) {
        def (r, c, d) = ol.pop()

        for (def p in [[r, c - 1], [r, c + 1], [r - 1, c], [r + 1, c]]) {
            def (rn, cn) = p
            
            // outside of the map
            if (rn < 0 || rn >= map.size() || cn < 0 || cn >= map[rn].size() ) {
                continue
            }
            
            // already visited
            if (cl.contains([rn, cn])) {
                continue
            }
        
            // impassable terrain
            if (tooSteep.call(map[rn][cn], map[r][c])) {
                continue
            }

            // end reached
            if (isEnd.call(rn, cn, map[rn][cn])) {
                dst = d + 1
            }

            cl << [rn, cn]
            ol << [rn, cn, d + 1]
        }    
    }
    return dst
}

def r1 = scan(hm, ps, 
    { r, c, h -> r == pe[0] && c == pe[1] }, 
    { hn, h -> (hn as byte) - (h as byte) > 1 }
)  

println r1  // 534

// there is over 2000 'a' on the map. scanning for path to the end for all of them 
// takes forever. it is much simpler to look from the end for a closest 'a' instead.

def r2 = scan(hm, pe, 
    { r, c, h -> h == ('a' as char) }, 
    { hn, h -> (hn as byte) - (h as byte) < -1 }
) 

println r2  // 525
