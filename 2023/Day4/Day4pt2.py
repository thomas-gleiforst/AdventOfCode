sample = False
shelby = False
filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample" if sample else ("input_" + ("shelby" if shelby else "thomas")))
## Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53

def recurse(cards, card, count=0):
  if len(cards[card]):
    score = 0
    for c in cards[card]:
      score += recurse(cards, c) + 1
    return score
  else:
     return 0
         
with open(filename) as f:
    lines = f.read().splitlines()
    cards = {}
    for line in reversed(lines):
        round = 0
        card, nums = line.split(": ")
        card = int(card[5:])
        winning, mine = [n.split() for n in nums.split(" | ")]
        for n in mine:
          if n in winning: round += 1
        cards[card] = [x for x in range(card+1, card+round+1)]
    score = len(cards.keys())
    for c in cards:
      score += recurse(cards, c)
    print(score)
