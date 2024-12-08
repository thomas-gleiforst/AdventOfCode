import { debugLog, ErrorLevels, getData } from '../utils';

const lines = getData();

const list1: number[] = []
const list2: number[] = []

lines.forEach(line => {
    const [left, right] = line.split("   ").map(i => parseInt(i))
    list1.push(left)
    list2.push(right)
})

debugLog(ErrorLevels.DEBUG, list1, list2)

list1.sort()
list2.sort()

debugLog(ErrorLevels.DEBUG, list1, list2)

let difference = 0
list1.forEach((val1, index) => {
    const val2 = list2[index]
    difference += Math.abs(val1-val2)
})

debugLog(ErrorLevels.OUTPUT, difference)