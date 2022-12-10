from collections import namedtuple

def main(sample):
    filename = "./{0}/{1}.txt".format(__file__.split('\\')[-2], "sample" if sample else "input" )
    with open(filename) as f:
        lines = f.read().splitlines()

        Point = namedtuple('Point', 'x y')
        head = Point(0, 0)
        tail = Point(0, 0)
        vList = [tail]
        visited = set([tail])
        for line in lines:
            direction, count = line.split()
            count = int(count)
            if sample: print(direction, count, head, tail)
            match direction:
                case 'R':
                    head = Point(head.x + count, head.y)
                    prev = head.x - 1
                    if prev != tail.x and head != tail:
                        if sample: print(direction, range(tail.x + (0 if tail.y == head.y else 1), head.x))
                        [visited.add(Point(x, head.y)) for x in range(tail.x + (0 if tail.y == head.y else 1), head.x)]
                        [vList.append(Point(x, head.y)) for x in range(tail.x + (0 if tail.y == head.y else 1), head.x)]
                        tail = Point(prev, head.y)
                        # visited.add(tail)
                        if sample: print(head, tail, vList)
                case 'L':
                    head = Point(head.x - count, head.y)
                    prev = head.x + 1
                    if prev != tail.x and head != tail:
                        if sample: print(direction, range(tail.x - (0 if tail.y == head.y else 1), head.x, -1))
                        [visited.add(Point(x, head.y)) for x in range(tail.x - (0 if tail.y == head.y else 1), head.x, -1)]
                        [vList.append(Point(x, head.y)) for x in range(tail.x - (0 if tail.y == head.y else 1), head.x, -1)]
                        tail = Point(prev, head.y)
                        # visited.add(tail)
                        if sample: print(direction, count, head, tail, vList)
                case 'U':
                    head = Point(head.x, head.y + count)
                    prev = head.y - 1
                    if prev != tail.y and head != tail:
                        if sample: print(direction, range(tail.y + (0 if tail.x == head.x else 1), head.y))
                        [visited.add(Point(head.x, y)) for y in range(tail.y + (0 if tail.x == head.x else 1), head.y)]
                        [vList.append(Point(head.x, y)) for y in range(tail.y + (0 if tail.x == head.x else 1), head.y)]
                        tail = Point(head.x, prev)
                        # visited.add(tail)
                        if sample: print(head, tail, vList)
                case 'D':
                    head = Point(head.x, head.y - count)
                    prev = head.y + 1
                    if prev != tail.y and head != tail:
                        if sample: print(direction, range(tail.y - (0 if tail.x == head.x else 1), head.y, -1))
                        [visited.add(Point(head.x, y)) for y in range(tail.y - (0 if tail.x == head.x else 1), head.y, -1)]
                        [vList.append(Point(head.x, y)) for y in range(tail.y - (0 if tail.x == head.x else 1), head.y, -1)]
                        tail = Point(head.x, prev)
                        # visited.add(tail)
                        if sample: print(head, tail, vList)
            if sample: 
                print(head, tail)
                print()
        if sample: print(len(visited), visited, '\n', vList)
        print(len(visited))

main(True)
print("-"*20)
main(False)

