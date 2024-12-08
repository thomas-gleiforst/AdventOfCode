def main(sample):
    filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample" if sample else "input")
    with open(filename) as f:
        lines = f.read().splitlines()

        part1, part2 = 0, 0
        for line in lines:
            aStart, aEnd, bStart, bEnd = list(map(int, "-".join(line.split(",")).split("-")))
            
            if (bStart >= aStart and bEnd <= aEnd) or (bStart <= aStart and bEnd >= aEnd): 
                part1 += 1


            a, b = set(range(aStart, aEnd+1)), set(range(bStart, bEnd+1))
            part2 += 1 if len(a.intersection(b)) else 0

        print("Part 1", part1)
        print("Part 2", part2)

main(True)
print("-"*20)
main(False)