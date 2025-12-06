import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const lines = getData().join(" ");
    const stones: number[] = lines.split(" ").map(i => parseInt(i));
    let duppedStones: {[key: number]: number} = {};
    let newDuppedStones: {[key: number]: number} = {};

    stones.forEach(stone => {
        duppedStones[stone] = (duppedStones[stone] ?? 0) + 1
    })
    
    // const instaLookup = {}

    const doRules = (stone: number) => {
        const digitString = stone.toString()
        const digitLength = digitString.length
        if (stone === 0) {
            newDuppedStones[1] = (newDuppedStones[1] ?? 0) + duppedStones[0]

        } else if (digitLength % 2 === 0) {
            const newStones = [digitString.slice(0, digitLength / 2), digitString.slice(digitLength / 2)].map(i => parseInt(i))
            debugLog(ErrorLevels.VERBOSE, stone, newStones)

            newDuppedStones[newStones[0]] = (newDuppedStones[newStones[0]] ?? 0) + duppedStones[stone]
            newDuppedStones[newStones[1]] = (newDuppedStones[newStones[1]] ?? 0) + duppedStones[stone]
        } else {
            const bigStone = stone*2024
            debugLog(ErrorLevels.VERBOSE, bigStone)

            newDuppedStones[bigStone] = (newDuppedStones[bigStone] ?? 0) + duppedStones[stone]
        }
    }

    for (const i of [...Array(25).keys()]) {
        Object.keys(duppedStones).forEach(stone => doRules(parseInt(stone)))
        duppedStones = newDuppedStones
        newDuppedStones = {}
    }

    debugLog(ErrorLevels.INFO, duppedStones)
    const answer = Object.values(duppedStones).reduce((total, stone) => total+=stone, 0)
    
    debugLog(ErrorLevels.OUTPUT, answer)
}