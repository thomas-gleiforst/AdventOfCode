from collections import namedtuple

def main(sample):
    filename = "./{0}/{1}.txt".format(__file__.split('\\')[-2], "sample" if sample else "input" )
    with open(filename) as f:
        lines = f.read().splitlines()

        Point = namedtuple('Point', 'x y')
        tail = head = Point(0, 0)
        visited = set([tail])
        for line in lines:
            direction, count = line.split()
            count = int(count)
            match direction:
                case 'R':
                    head = Point(head.x + count, head.y)
                    prev = Point(head.x - 1, head.y)
                    if prev.x != tail.x and head.x != tail.x and head != tail:
                        [visited.add(Point(x, head.y)) for x in range(tail.x + (0 if tail.y == head.y else 1), head.x)]
                        tail = Point(prev.x, head.y)
                case 'L':
                    head = Point(head.x - count, head.y)
                    prev = Point(head.x + 1, head.y)
                    if prev.x != tail.x and head.x != tail.x and head != tail:
                        [visited.add(Point(x, head.y)) for x in range(tail.x - (0 if tail.y == head.y else 1), head.x, -1)]
                        tail = Point(prev.x, head.y)
                case 'U':
                    head = Point(head.x, head.y + count)
                    prev = Point(head.x, head.y - 1)
                    if prev.y != tail.y and head.y != tail.y and head != tail:
                        [visited.add(Point(head.x, y)) for y in range(tail.y + (0 if tail.x == head.x else 1), head.y)]
                        tail = Point(head.x, prev.y)
                case 'D':
                    head = Point(head.x, head.y - count)
                    prev = Point(head.x, head.y + 1)
                    if prev.y != tail.y and head.y != tail.y and head != tail:
                        [visited.add(Point(head.x, y)) for y in range(tail.y - (0 if tail.x == head.x else 1), head.y, -1)]
                        tail = Point(head.x, prev.y)
        print(len(visited))

main(True)
print("-"*20)
main(False)

