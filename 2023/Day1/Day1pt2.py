import regex as re

def numberify(num):
    match num:
        case 'one': return '1'
        case 'two': return '2'
        case 'three': return '3'
        case 'four': return '4'
        case 'five': return '5'
        case 'six': return '6'
        case 'seven': return '7'
        case 'eight': return '8'
        case 'nine': return '9'
        case 'zero': return '0'
    return num

filename = "{0}/{1}.txt".format("/".join(__file__.split("\\")[:-1]), "input")

with open(filename) as f:
    lines = f.read().splitlines()

    number = 0
    for item in lines:
        numbers = re.findall(r'(\d|one|two|three|four|five|six|seven|eight|nine)',item, overlapped=True)
        numbers = [numberify(num) for num in numbers]
        number += int(numbers[0] + numbers[-1])
    print(number)