import { debugLog, ErrorLevels, getData } from '../utils';

const lines = getData();

const list1: {[key: number]: number} = {}
const list2: {[key: number]: number} = {}

lines.forEach(line => {
    const [left, right] = line.split("   ").map(i => parseInt(i))
    list1[left] = (list1[left] ?? 0) + 1
    list2[right] = (list2[right] ?? 0) + 1
})

debugLog(ErrorLevels.DEBUG, list1, list2)


let difference = 0
Object.entries(list1).forEach(([key, val1]) => {
    const val2 = list2[key as any] ?? 0
    const score = parseInt(key) * val1 * val2
    difference += score
    debugLog(ErrorLevels.DEBUG, key, val1, val2, score)
})

debugLog(ErrorLevels.OUTPUT, difference)
