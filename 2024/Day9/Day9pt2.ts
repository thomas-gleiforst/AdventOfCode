import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
    interface Digit {
        length: number,
        file: boolean,
        id: number
    }
    const digits = getData().join("").split("").map((entry, index) => {
        return {
            length: parseInt(entry),
            file: index % 2 === 0,
            wasFile: index % 2 === 0,
            id: Math.floor(index / 2)
        }
    });
    
    let storage: number[] = [];
    let entries = 0
    let answer = 0

    const addDigit = (digit: Digit) => {
        const digitArray = new Array(digit.length)
        let weight = 0
        const fillValue = digit.id
        digitArray.fill(fillValue)
        weight = digitArray.reduce((total, value, index) => {
            debugLog(ErrorLevels.VERBOSE, total, value, storage.length, index)
            return total += value * (storage.length + index)
        }, 0)
        answer += weight
        debugLog(ErrorLevels.DEBUG, `File: ${JSON.stringify(digit)} of id ${fillValue} - adding ${weight}`)
        storage = storage.concat(digitArray)
    }

    let digit = digits.shift()

    while (typeof digit !== "undefined") {
        debugLog(ErrorLevels.VERBOSE, "Checking", JSON.stringify(digit))
        if (digit.file) {
            addDigit(digit)
            entries += 1
        } else {
            let space = digit.length

            if (!digit.wasFile) {
                let movable;
                do {
                    movable = digits.findLastIndex((d) => d.file && d.length <= space)
                    movable > -1 && debugLog(ErrorLevels.VERBOSE, `Found index ${movable}, ${JSON.stringify(digits[movable])}`)
                    
                    if (movable > -1) {
                        const movableDigit = digits[movable]
                        addDigit(movableDigit)
                        movableDigit.file = false
                        space -= movableDigit.length;
                    }
                } while (space && movable > -1)
    
                debugLog(ErrorLevels.DEBUG, `Leftover space: ${space}`)
            }

            const digitArray = new Array(space)
            digitArray.fill(".")
            storage = storage.concat(digitArray)
        }
        
        digit = digits.shift()
        debugLog(ErrorLevels.VERBOSE, storage.join(""))
    }
    debugLog(ErrorLevels.INFO, storage.join(""))
    debugLog(ErrorLevels.OUTPUT, answer)
}