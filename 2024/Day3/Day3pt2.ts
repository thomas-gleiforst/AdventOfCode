import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
    const lines = getData();
    const line = lines.join("")
    const mul_matches = line.matchAll(/mul\((\d{1,3}),(\d{1,3})\)/g)
    
    const do_matches = line.matchAll(/do\(\)/g).toArray().map(({index}) => index)
    const do_not_matches = line.matchAll(/don't\(\)/g).toArray().map(({index}) => index)

    do_matches.unshift(0)

    const mul_match_array = mul_matches.toArray()
    debugLog(ErrorLevels.INFO, mul_match_array.map(({index}) => index))
    debugLog(ErrorLevels.INFO, do_matches)
    debugLog(ErrorLevels.INFO, do_not_matches)

    let answer = 0

    const find_closest = (i: number): 'do' | 'dont' => {
        if (i > do_matches?.[0] && i > do_not_matches?.[0]) {
            if (do_matches?.[0] > do_not_matches?.[0]) {
                const final_index = do_not_matches.findIndex(threshold => threshold > do_matches?.[0])
                do_not_matches.splice(0, final_index)
                return 'do'
            } else {
                const final_index = do_matches.findIndex(threshold => threshold > do_not_matches?.[0])
                do_matches.splice(0, final_index)
                return 'dont'
            }
        } else if (i > do_matches?.[0]) {
            return 'do'
        } else if (i > do_not_matches?.[0]) {
            return 'dont'
        } else return 'do'
    }


    mul_match_array.forEach((entry) => {
        const [func, x, y] = entry
        
        if (find_closest(entry.index) === 'dont') return
        debugLog(ErrorLevels.DEBUG, 'doing', entry.index, parseInt(x), parseInt(y))
        answer += parseInt(x) * parseInt(y);
    })
    
    debugLog(ErrorLevels.OUTPUT, answer)
}