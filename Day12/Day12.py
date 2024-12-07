import sys

start, end = None, None
sys.setrecursionlimit(1500)

class Point:
    def __init__(self, r, c, value):
        global start, end

        self.r = r
        self.c = c
        self.touches = []
        self.reaches = []
        self.previous = None
        self.shortestPath = None
        self.key = lambda t: t.value
        match value:
            case 'S':
                self.shortestPath = 0
                self.value = 0
                self.start = True
                start = self
            case 'E':
                self.value = 26
                self.end = True
                end = self
            case _:
                self.value = ord(value) - ord('a')

    def pathLength(self, absolute = False):
        if self.shortestPath == None or (absolute and self != start):
            return self.previous.pathLength(absolute) + 1
        return self.shortestPath

    def setPath(self, path):
        if self.previous == None or path.shortestPath < self.previous.pathLength():
            print("here")
            self.previous = path
            self.shortestPath = path.shortestPath+1

    def touching(self, grid):
        for r in range(max(self.r-1, 0), min(self.r+2, len(grid))):
            for c in range(max(self.c-1, 0), min(self.c+2, len(grid[self.r]))):
                if (r is self.r and not c is self.c) or (c is self.c and r is not self.r):
                    touch = grid[r][c]
                    self.touches.append(touch)
                    if touch.value <= self.value + 1:
                        self.reaches.append(touch)
        self.touches.sort(key=self.key)
        self.reaches.sort(key=self.key)

    def __str__(self):
        return f'row: {self.r}, column: {self.c}, value: {self.value}, touches: {[(i.r, i.c) for i in self.touches]}, reaches: {[(i.r, i.c) for i in self.reaches]}, path: {self.shortestPath}'

def main(sample):
    global start, end
    start, end = None, None

    filename = "./{0}/{1}.txt".format(__file__.split('\\')[-2], "sample" if sample else "input" )
    with open(filename) as f:
        lines = f.read().splitlines()
        grid = []
        for r in range(len(lines)):
            grid.append([])
            for c in range(len(lines[r])):
                grid[-1].append(Point(r, c, lines[r][c]))
            
        # [print(g) for r in grid for g in r]
        [g.touching(grid) for r in grid for g in r]
        # [print(g) for r in grid for g in r]
        # print()
        # print(start)
        [t.setPath(start) for t in start.reaches]

        explored = [start]
        reaching = start.reaches.copy()

        ahhh = 0
        while len(reaching):
            print()
            explore = reaching.pop()
            newTouch = explore.reaches
            
            explore.shortestPath = explore.pathLength()
            explored.append(explore)
            for t in newTouch:
                t.setPath(explore)
                if t not in reaching and t not in explored:
                    reaching.append(t)
            reaching.sort(key=start.key)
            print(explore)
            ahhh += 1

        print(end.pathLength(True))
        last = end
        while last != start:
            last = last.previous
            # print(last)



            

main(True)        
print("-"*20)
# main(False)
# 996 is too high