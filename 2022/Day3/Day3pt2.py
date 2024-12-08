filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "input")
with open(filename) as f:
    lines = f.read().splitlines()
    value = 0
    for i in range(0, len(lines), 3):
        line = lines[i:i+3]
        sack1, sack2, sack3 = set(line[0]), set(line[1]), set(line[2])
        all = list(sack1.intersection(sack2).intersection(sack3))[0]
        value += ord(all) - (96 if all.islower() else (64-26))

    print(value)
