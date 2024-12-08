import re
sample = False
shelby = False
filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample" if sample else ("input_" + ("shelby" if shelby else "thomas")))
# filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample_test")
# Answer: 520875 too low

def dictSearch_oneTimeUse(v, d: dict):
    for num, keys in d.items():
        if v in keys:
            d.pop(num)
            print(num, keys)
            return num
    return 0


def adjacentNums(row: int, col: int, cells: list[list[str]], parts: dict):
    sum = 0
    for i in [row-1, row, row+1]:
        for j in [col-1, col, col+1]:
            if not ((i == row and j == col) or (0 > i > len(cells)) or (0 > j > len(cells[i])) and cells[i][j].isdigit()):
                answer = dictSearch_oneTimeUse((i, j), parts)
                answer = answer.split("-") if answer else answer
                answer = answer[0] if answer else answer
                sum += int(answer)
    return sum

def findParts(cells: list[list[str]], lines: [list[str]], parts: dict):
    for i in range(len(cells)):
        nums = re.findall(r'\d+', lines[i])
        first = None
        for j in range(len(cells[i])):
            if (cells[i][j].isdigit()):
                if not first and nums:
                    first = f"{str(int(nums.pop(0)))}-{i}_{j}"
                    parts[first] = []
                parts[first] += [(i, j)]
            else:
                first = None

with open(filename) as f:
    lines = f.read().splitlines()
    cells = [list(r) for r in lines]
    parts = {}
    findParts(cells, lines, parts)

    summed = 0
    for i in range(len(cells)):
        for j in range(len(cells[i])):
            if not cells[i][j].isdigit() and not cells[i][j] == ".":
                summed += adjacentNums(i, j, cells, parts)

    print(summed)

    