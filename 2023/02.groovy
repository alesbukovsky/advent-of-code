// puzzle: https://adventofcode.com/2023/day/2

def dt = new File(args[0])
    .getText()
    .split('\n')
    .collectEntries {
        def i = it.indexOf(':')
        def k = Integer.parseInt( it.substring('Game: '.length() - 1, i) )
        def v = []
        it.substring(i + 1).split(';').each { s ->
            def m = [blue:0, green:0, red:0]
            s.trim().split(',').each { c ->
                c = c.trim()
                def j = c.indexOf(' ')
                m.put(c.substring(j + 1), Integer.parseInt(c.substring(0, j)))
            }
            v << m
        }
        return [k, v]
    }

//--- part 1 -------------------------------------------------------------

def r1 = dt.findAll { k, v ->
        v.find { m -> m.blue > 14 || m.green > 13 || m.red > 12 } == null
    }
    .collect { k, v -> k }
    .sum()

println r1 // 2683

//--- part 2 -------------------------------------------------------------

def r2 = dt.collect { k, v ->
        v.collect { m -> m.blue }.max() * v.collect { m -> m.green }.max() * v.collect { m -> m.red }.max()
    }
    .sum()

println r2 // 49710
