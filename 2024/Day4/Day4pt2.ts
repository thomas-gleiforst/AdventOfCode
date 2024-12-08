import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
    const lines = getData().map(line => line.split(''));
    debugLog(ErrorLevels.VERBOSE, lines)
    
    const up_left = (row:number,col:number) => {
        if (row-2 < 0 || col-2 < 0 || row >= lines.length || col >= lines[row].length) return false
        const word = lines[row][col] + lines[row-1][col-1] + lines[row-2][col-2]
        debugLog(ErrorLevels.VERBOSE, 'up_left', word)
        return word === 'MAS'
    }

    const up_right = (row:number,col:number) => {
        if (row-2 < 0 || col < 0 || row >= lines.length || col+2 >= lines[row].length) return false
        const word = lines[row][col] + lines[row-1][col+1] + lines[row-2][col+2]
        debugLog(ErrorLevels.VERBOSE, 'up_right', word)
        return word === 'MAS'
    }

    const down_left = (row:number,col:number) => {
        if (row < 0 || row+2 >= lines.length || col-2 < 0 || col >= lines[row].length) return false
        const word = lines[row][col] + lines[row+1][col-1] + lines[row+2][col-2]
        debugLog(ErrorLevels.VERBOSE, 'down_left', word)
        return word === 'MAS'
    }

    const down_right = (row:number,col:number) => {
        if (row < 0 || col < 0 || row+2 >= lines.length || col+2 >= lines[row].length) return false
        const word = lines[row][col] + lines[row+1][col+1] + lines[row+2][col+2]
        debugLog(ErrorLevels.VERBOSE, 'down_right', word)
        return word === 'MAS'
    }


    let answer = 0

    for (let i = 0; i < lines.length; i++) {
        for (let j = 0; j < lines[i].length; j++) {
            debugLog(ErrorLevels.VERBOSE, i, j, lines[i][j])

            let mas_count = 0
            if (up_left(i+1,j+1)) mas_count++
            if (down_left(i-1,j+1)) mas_count++
            if (up_right(i+1,j-1)) mas_count++
            if (down_right(i-1,j-1)) mas_count++
            if(mas_count === 2) { answer++; debugLog(ErrorLevels.INFO, 'X-MAS', i, j) }
        }
    }
    
    debugLog(ErrorLevels.OUTPUT, answer)
}