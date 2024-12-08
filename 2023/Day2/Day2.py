sample = False # 8
shelby = True
filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "sample" if sample else ("input_" + ("shelby" if shelby else "thomas")))

with open(filename) as f:
    lines = f.read().splitlines()

    # 12 red cubes, 13 green cubes, and 14 blue cubes
    possible = {
        'red': 12,
        'green': 13,
        'blue': 14
    }
    score = 0 ## sum of the game numbers which are possible

    for line in lines:
        gameNum, gameData = line.split(': ')
        gameNum = int(gameNum[5:])

        gameData = [game.split(', ') for game in gameData.split('; ')]
        for game in gameData:
            for colorData in game:
                number, color = colorData.split()
                if (possible[color] < int(number)): # quit early if impossible
                    break
            else: 
                continue
            break
        else: # if we didn't quit early ever, then add to score
            score += gameNum
    print(score)