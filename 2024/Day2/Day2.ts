import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const lines = getData();
    
    let safe_count = 0;    

    lines.forEach(line => {
        const levels = line.split(" ").map(i => parseInt(i))
        
        let safe = true
        let first_positive = levels[0] > levels[1]
        for (let i = 0; i < levels.length - 1; i++) {
            const difference = levels[i] - levels[i+1]
            const curr_positive = levels[i] > levels[i+1]
            
            if (!difference || Math.abs(difference) > 3 || first_positive !== curr_positive) safe = false
        }

        if (safe) safe_count += 1
    })
    
    debugLog(ErrorLevels.OUTPUT, safe_count)
}