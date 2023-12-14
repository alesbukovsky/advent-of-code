// puzzle: https://adventofcode.com/2022/day/6

def sniff(f, ps) {
    def buf = []
    def pas = 0

    def r = new File(f).newInputStream()
    def b = -1
    def x = false

    while ( !x && (b = r.read()) != -1 ) {
        buf << b
        if (buf.size() == ps + 1) {
            pas++
            buf = buf.drop(1)
            x = buf.toUnique().size() == ps
        }
    }

    return (x) ? pas + ps : -1
}
    
println sniff(args[0],  4)  // 1987
println sniff(args[0], 14)  // 3059