monkeys = []
tests = []
items = []
part = ""

class Item:
    global items
    def __init__(self, value):
        self.value = value
        self.values = []
        items.append(self)
    
    def addTests(self):
        [self.values.append(self.value % i) for i in tests]

    def addValue(self, quantity):
        for i in range(len(self.values)):
            self.values[i] = (self.values[i] + quantity) % tests[i]

    def multiplyValue(self, quantity):
        for i in range(len(self.values)):
            self.values[i] = (self.values[i] * quantity) % tests[i]

    def powerValue(self, quantity):
        for i in range(len(self.values)):
            self.values[i] = (self.values[i] ** quantity) % tests[i]

    def __str__(self):
        return f'value: {self.value}, values: {self.values}'


class Monkey:
    global part
    def __init__(self, id, items, operation, quantity, test, true, false):
        self.id = id
        self.items = [Item(i) for i in items]
        self.test = test
        self.true = true
        self.false = false
        self.inspected = 0
        if quantity == "old":
            if operation == "+":
                self.operation = "*"
                self.quantity = 2
            else:
                self.operation = "^"
                self.quantity = 2
        else:
            self.operation = operation
            self.quantity = int(quantity) if quantity != "old" else quantity
        
    def inspect(self, item):
        self.inspected += 1
        match self.operation:
            case '+':
                if part == "Part1": item.value += self.quantity
                else: item.addValue(self.quantity)
            case '*':
                if part == "Part1": item.value *= self.quantity
                else: item.multiplyValue(self.quantity)
            case '^':
                if part == "Part1": item.value *= item.value
                else: item.powerValue(self.quantity)
        return item

    def check(self, item):
        if part == "Part1": self.throw(item, self.true if item.value % self.test == 0 else self.false)
        else: self.throw(item, self.true if item.values[self.id] == 0 else self.false)

    def throw(self, item, target):
        monkeys[target].items.append(item)

    def turn(self):
        for item in self.items:
            item = self.inspect(item)
            if part == "Part1": item.value //= 3
            self.check(item)
        self.items = []

    def __str__(self):
        return f'id: {self.id}, items: {len(self.items)}, operation: {self.operation}, quantity: {self.quantity}, test: {self.test}, true: {self.true}, false: {self.false}, inspected: {self.inspected} \n' + " --- ".join(str(i) for i in self.items)

def main(sample):
    global monkeys, tests, items, part
    filename = "./{0}/{1}.txt".format(__file__.split('\\')[-2], "sample" if sample else "input" )
    with open(filename) as f:
        lines = f.read().splitlines()
        for p in ["Part1", "Part2"]:
            monkeys, tests, items, part = [], [], [], p

            for i in range(0, len(lines), 7):
                id = int(lines[i].split()[1][:-1])
                initialItems = list(map(int, "".join(lines[i+1].split()[2:]).split(",")))
                operation, quantity = lines[i+2].split()[4:]
                test = int(lines[i+3].split()[-1])
                true = int(lines[i+4].split()[-1])
                false = int(lines[i+5].split()[-1])
                
                tests.append(test)
                monkeys.append(Monkey(id, initialItems, operation, quantity, test, true, false))

            [i.addTests() for i in items]
            
            for i in range(20 if part == "Part1" else 10000):
                [m.turn() for m in monkeys]

            best = sorted(monkeys, key=lambda x: x.inspected, reverse=True)[:2]
            print(part, best[0].inspected * best[1].inspected)
main(True)
print("-"*20)
main(False)
