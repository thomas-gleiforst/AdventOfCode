import { debugLog, ErrorLevels, getData } from '../utils';


export const Part1 = () => {
    const lines = getData().map(line => line.split(''));
    debugLog(ErrorLevels.DEBUG, lines)
    
    const up = (row:number,col:number) => {
        if (row-3 < 0) return false
        const word = lines[row][col] + lines[row-1][col] + lines[row-2][col] + lines[row-3][col]
        return word === 'XMAS'
    }

    const down = (row:number,col:number) => {
        if (row+3 >= lines.length) return false
        const word = lines[row][col] + lines[row+1][col] + lines[row+2][col] + lines[row+3][col]
        return word === 'XMAS'
    }

    const left = (row:number,col:number) => {
        if (col-3 < 0) return false
        const word = lines[row][col] + lines[row][col-1] + lines[row][col-2] + lines[row][col-3]
        return word === 'XMAS'
    }

    const right = (row:number,col:number) => {
        if (col+3 >= lines[row].length) return false
        const word = lines[row][col] + lines[row][col+1] + lines[row][col+2] + lines[row][col+3]
        return word === 'XMAS'
    }

    const up_left = (row:number,col:number) => {
        if (row-3 < 0 || col < 0) return false
        const word = lines[row][col] + lines[row-1][col-1] + lines[row-2][col-2] + lines[row-3][col-3]
        return word === 'XMAS'
    }

    const up_right = (row:number,col:number) => {
        if (row-3 < 0 || col+3 >= lines[row].length) return false
        const word = lines[row][col] + lines[row-1][col+1] + lines[row-2][col+2] + lines[row-3][col+3]
        return word === 'XMAS'
    }

    const down_left = (row:number,col:number) => {
        if (row+3 >= lines.length || col < 0) return false
        const word = lines[row][col] + lines[row+1][col-1] + lines[row+2][col-2] + lines[row+3][col-3]
        return word === 'XMAS'
    }

    const down_right = (row:number,col:number) => {
        if (row+3 >= lines.length || col+3 >= lines[row].length) return false
        const word = lines[row][col] + lines[row+1][col+1] + lines[row+2][col+2] + lines[row+3][col+3]
        return word === 'XMAS'
    }




    let answer = 0

    for (let i = 0; i < lines.length; i++) {
        for (let j = 0; j < lines[i].length; j++) {
            if(up(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'up', i, j) }
            if(down(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'down', i, j) }
            if(left(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'left', i, j) }
            if(right(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'right', i, j) }
            if(up_left(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'up_left', i, j) }
            if(up_right(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'up_right', i, j) }
            if(down_left(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'down_left', i, j) }
            if(down_right(i,j)) { answer++; debugLog(ErrorLevels.INFO, 'down_right', i, j) }
        }
    }
    
    debugLog(ErrorLevels.OUTPUT, answer)
}