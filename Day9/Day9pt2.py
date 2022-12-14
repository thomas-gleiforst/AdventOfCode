from collections import namedtuple
import numpy as np
Point = namedtuple('Point', 'x y')

def printList(size, list):
    with np.printoptions(threshold=np.inf, linewidth=np.inf):
        output = np.chararray((size, size),unicode=True)
        output[:] = '.'
        center = Point(size/2, size/2)
        for point in list:
            x, y, char = point.x, point.y, "#"
            output.put((size*(center.y-y-1)) + center.x + x, char)
        print(output)

def touching(point):
    touches = []
    for i in range(point.x - 1, point.x + 2):
        for j in range(point.y - 1, point.y + 2):
            touches.append(Point(i, j))
    return touches

def generate(lines):
    head = Point(0, 0)
    returnList = [head]
    for line in lines:
        direction, count = line.split()
        count = int(count)
        match direction:
            case 'R':
                nHead = Point(head.x + count, head.y)
                [returnList.append(Point(x, head.y)) for x in range(head.x + (0 if head.y == nHead.y else 1), nHead.x)]
                head = nHead
            case 'L':
                nHead = Point(head.x - count, head.y)
                [returnList.append(Point(x, head.y)) for x in range(head.x - (0 if head.y == nHead.y else 1), nHead.x, -1)]
                head = nHead
            case 'U':
                nHead = Point(head.x, head.y + count)
                [returnList.append(Point(head.x, y)) for y in range(head.y + (0 if head.x == nHead.x else 1), nHead.y)]
                head = nHead
            case 'D':
                nHead = Point(head.x, head.y - count)
                [returnList.append(Point(head.x, y)) for y in range(head.y - (0 if head.x == nHead.x else 1), nHead.y, -1)]
                head = nHead
    return returnList + [head]

def main(sample):
    filename = "./{0}/{1}.txt".format(__file__.split('\\')[-2], "sample" if sample else "input" )
    with open(filename) as f:
        lines = f.read().splitlines()
        allList = [generate(lines)]
        
        for i in range(9):
            vList = allList[i]
            tail = Point(0, 0)
            nList = [tail]
            for h in vList:
                if h not in touching(tail):
                    xDif = max(-1, min(1, h.x - tail.x))
                    yDif = max(-1, min(1, h.y - tail.y))
                    tail = Point(tail.x + xDif, tail.y + yDif)
                    nList.append(tail)
            allList.append(nList)
            # printList(50, allList[-1], [])
            if i == 0: print('Part 1', len(set(nList)))
        print('Part 2', len(set(nList)))
main(True)
print("-"*20)
main(False)
