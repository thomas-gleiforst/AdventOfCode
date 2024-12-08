import { debugLog, ErrorLevels, getData } from '../utils';

const evaluate = (levels: number[]) => {
    let safe = true
    let first_positive = levels[0] > levels[1]
    for (let i = 0; i < levels.length - 1; i++) {
        const difference = levels[i] - levels[i+1]
        const curr_positive = levels[i] > levels[i+1]
        
        if (!difference || Math.abs(difference) > 3 || first_positive !== curr_positive) {
            safe = false
            break;
        }
    }
    return safe
}

export const Part2 = () => {
    const lines = getData();
    
    let safe_count = 0;    

    lines.forEach(line => {
        const levels = line.split(" ").map(i => parseInt(i))
        
        let i = -1
        let safe = false
        let trimmed_levels: number[] = []
        while (!safe && i < levels.length) {
            trimmed_levels = [...levels]
            if (i >= 0) trimmed_levels.splice(i, 1)
            safe = evaluate(trimmed_levels)
            i++
        }

        if (safe) {
            safe_count += 1
            debugLog(ErrorLevels.DEBUG, trimmed_levels)
        }
    })
    
    debugLog(ErrorLevels.OUTPUT, safe_count)
}