sample = False
shelby = False
filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample" if sample else ("input_" + ("shelby" if shelby else "thomas")))
## Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53

with open(filename) as f:
    lines = f.read().splitlines()
    score = 0
    for line in lines:
        round = 0
        card, nums = line.split(": ")
        card = int(card[5:])
        winning, mine = [n.split() for n in nums.split(" | ")]
        for n in mine:
          if n in winning: round = round*2 if round else 1
        score += round
    print(score)
