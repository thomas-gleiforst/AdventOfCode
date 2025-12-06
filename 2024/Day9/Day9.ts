import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const digits = getData().join("").split("").map(i => parseInt(i));
    
    let storage: number[] = [];
    const freeSpace: number[] = [];
    let entries = 0
    let answer = 0

    let file = true;
    for (const digit of digits) {
        const digitArray = new Array(digit)
        let weight = 0
        if (file) {
            digitArray.fill(entries)
            weight = digitArray.reduce((total, value, index) => {
                debugLog(ErrorLevels.VERBOSE, total, value, storage.length, index)
                return total += value * (storage.length + index)
            }, 0)
            answer += weight
            debugLog(ErrorLevels.DEBUG, `File: ${digit} of id ${entries} - adding ${weight}`)
            entries += 1    
        } else {
            digitArray.fill(".")
            digitArray.forEach((_, index) => freeSpace.push(storage.length + index))
            debugLog(ErrorLevels.DEBUG, `Space: ${digit}`)
        }
        storage = storage.concat(digitArray)
        
        file = !file;
    }
    debugLog(ErrorLevels.INFO, storage.join(""))
    debugLog(ErrorLevels.OUTPUT, "Before compression:", answer)

    for (const space of freeSpace) {
        if (space > storage.length) break;
        let finalValue: number | string = "."
        while (finalValue === "." || typeof finalValue === "string") {
            finalValue = storage.pop() ?? 0
        }
        const removeSum = finalValue * (storage.length)
        storage[space] = finalValue
        const addSum = finalValue * space
        answer += addSum - removeSum
        debugLog(ErrorLevels.DEBUG, `Filling ${space} with ${finalValue} changing sum by ${addSum-removeSum}`)
    }
    
    debugLog(ErrorLevels.INFO, storage.join(""))
    debugLog(ErrorLevels.OUTPUT, answer)
}