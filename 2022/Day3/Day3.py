filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "input")
with open(filename) as f:
    lines = f.read().splitlines()
    value = 0
    for line in lines:
        sack1, sack2 = set(line[:len(line)//2]), set(line[len(line)//2:])
        both = list(sack1.intersection(sack2))[0]
        value += ord(both) - (96 if both.islower() else (64-26))

    print(value)
