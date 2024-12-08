import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const lines = getData();
    const line = lines.join("")
    const matches = line.matchAll(/mul\((\d{1,3}),(\d{1,3})\)/g)
    
    const matchArray = matches.toArray()
    debugLog(ErrorLevels.INFO, matchArray)

    let answer = 0

    matchArray.forEach(([func, x, y]) => {
        debugLog(ErrorLevels.INFO, 'match', answer, parseInt(x), parseInt(y))
        answer += parseInt(x) * parseInt(y);
    })
    
    debugLog(ErrorLevels.OUTPUT, answer)
}