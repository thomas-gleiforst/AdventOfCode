import re
filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "input")

with open(filename) as f:
    lines = f.read().splitlines()

    number = 0
    for item in lines:
        numbers = re.findall(r'\d',item)
        number += int(numbers[0] + numbers[-1])
    print(number)
