// puzzle: https://adventofcode.com/2023/day/5

import static java.lang.Math.*

def dt= new File(args[0])
    .getText()
    .split('\n|\n\n')
    .findAll { it.length() > 0 }

def seeds = dt.head().substring('seeds: '.length()).split(' ').collect { Long.parseLong(it) }
def maps = []

def x = dt.tail()
def i = 0
while (i < x.size()) {
    if (x[i].endsWith(' map:')) {
        maps << []
    } else {
        maps.last() << x[i].split(' ').collect { Long.parseLong(it) }
    }
    i++
}

//--- part 1 -------------------------------------------------------------

def loc1(s, ms) {
    ms.each { m ->
        for (def j = 0; j < m.size(); j++) {
            if (m[j][1] <= s && s < (m[j][1] + m[j][2])) {
                s = m[j][0] + s - m[j][1]
                break;
            }
        }
    }
    return s
}

def r1 = seeds.collect { s -> loc1(s, maps) }.min()

println r1 // 340994526

//--- part 2 -------------------------------------------------------------

// numbers are too big for piecemeal processing as above, using whole intervals
// instead. intervals are left-inclusive and right exclusive, [1,2,3] => (1..4).

def loc2(rs, ms) {
    def ot = []
    ms.each { m ->
        long ml = m[1]         // map source left index
        long mr = m[1] + m[2]  // map source right index
        long md = m[0]         // map destination (effectively left index)

        def rn = []
        while (rs.size() > 0) {
            def r = rs.pop()
            long rl = r[0]  // seed range left limit
            long rr = r[1]  // seed range right limit

            // overlap between ranges: if legit, translate to destination indexes
            def ov = [ max(rl, ml), min(rr, mr) ]
            if (ov[0] < ov[1]) {
                ot << [ov[0] - ml + md, ov[1] - ml + md]
            }

            // reminder on the left: if legit, needs to be matched further
            def lt = [ rl, min(rr, ml) ]
            if (lt[0] < lt[1]) rn << lt

            // reminder on the right: if legit, needs to be matched further
            def rt = [ max(rl, mr), rr ]
            if (rt[0] < rt[1]) rn << rt
        }
        rs = rn  // keep matching any reminders found
    }
    return ot + rs  // found matches + unmatched
}

def r2 = seeds.collate(2)
    .collect {s ->
        def rs = [ [s[0], s[0] + s[1]] ]
        maps.each { m ->
            rs = loc2(rs, m)
        }
        return rs.collect { it[0] }
    }
    .flatten()
    .min()

println r2 // 52210644
