import math

sample = False # 2286
shelby = False
filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample" if sample else ("input_" + ("shelby" if shelby else "thomas")))

with open(filename) as f:
    lines = f.read().splitlines()

    score = 0 ## sum of the game numbers which are possible

    for line in lines:
        possible = {
            'red': 0,
            'green': 0,
            'blue': 0
        }
        gameNum, gameData = line.split(': ')
        gameNum = int(gameNum[5:])

        gameData = [game.split(', ') for game in gameData.split('; ')]
        for game in gameData:
            for colorData in game:
                number, color = colorData.split()

                # if impossible update  
                if (possible[color] < int(number)): ## impossible
                    possible[color] = int(number)

        score += math.prod(list(possible.values()))
    print(score)