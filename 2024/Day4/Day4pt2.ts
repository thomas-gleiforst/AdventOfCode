import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
    const lines = getData().map(line => line.split(''));
    debugLog(ErrorLevels.DEBUG, lines)
    
    const up_left = (row:number,col:number) => {
        if (row-3 < 0 || col < 0) return false
        const word = lines[row][col] + lines[row-1][col-1] + lines[row-2][col-2] + lines[row-3][col-3]
        return word === 'MAS'
    }

    const up_right = (row:number,col:number) => {
        if (row-3 < 0 || col+3 >= lines[row].length) return false
        const word = lines[row][col] + lines[row-1][col+1] + lines[row-2][col+2] + lines[row-3][col+3]
        return word === 'MAS'
    }

    const down_left = (row:number,col:number) => {
        if (row+3 >= lines.length || col < 0) return false
        const word = lines[row][col] + lines[row+1][col-1] + lines[row+2][col-2] + lines[row+3][col-3]
        return word === 'MAS'
    }

    const down_right = (row:number,col:number) => {
        if (row+3 >= lines.length || col+3 >= lines[row].length) return false
        const word = lines[row][col] + lines[row+1][col+1] + lines[row+2][col+2] + lines[row+3][col+3]
        return word === 'MAS'
    }


    let answer = 0

    for (let i = 0; i < lines.length; i++) {
        for (let j = 0; j < lines[i].length; j++) {
            if((up_left(1-i,1-j) || down_right(1+i,1+j)) && (up_right(1-i,1+j) || down_left(1+i,1-j))) { answer++; debugLog(ErrorLevels.INFO, 'X-MAS', i, j) }
        }
    }
    
    debugLog(ErrorLevels.OUTPUT, answer)
}