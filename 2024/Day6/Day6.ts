import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const lines = getData().map(line => line.split('')) as ("." | "#" | "^" | "X")[][];

    const up = (row:number,col:number) => {
        if (row-1 < 0) return false
        const space = lines[row-1][col];
        return {space: space, row: row-1, col: col}
    }

    const down = (row:number,col:number) => {
        if (row+1 >= lines.length) return false
        const space = lines[row+1][col]
        return {space: space, row: row+1, col: col}
    }

    const left = (row:number,col:number) => {
        if (col-1 < 0) return false
        const space = lines[row][col-1]
        return {space: space, row: row, col: col-1}
    }

    const right = (row:number,col:number) => {
        if (col+1 >= lines[row].length) return false
        const space = lines[row][col+1]
        return {space: space, row: row, col: col+1}
    }
    
    let startRow = 0, startCol = 0;
    for (let row in lines) {
        for (let col in lines[row]) {
            if (lines[row][col] === "^") {
                startRow = parseInt(row);
                startCol = parseInt(col);
                lines[row][col] = "X";
            }
        }
    }

    enum Direction { UP, RIGHT, DOWN, LEFT}
    const nextDirection = (curr: Direction) => {
        switch (curr) {
            case Direction.UP: return Direction.RIGHT 
            case Direction.RIGHT: return Direction.DOWN 
            case Direction.DOWN: return Direction.LEFT 
            case Direction.LEFT: return Direction.UP
        }
    }

    interface Position {
        space: "." | "#" | "^" | "X",
        row: number,
        col: number,
        direction?: Direction
    }

    const move = ({row, col, direction}: Position) => {
        switch (direction) {
            case Direction.UP: return up(row, col)
            case Direction.RIGHT: return right(row, col)
            case Direction.DOWN: return down(row, col)
            case Direction.LEFT: return left(row, col)
        }
    }

    let answer = 1;

    let currentPosition: Position | false | undefined = {space: "^", row: startRow, col: startCol, direction: Direction.UP}
    debugLog(ErrorLevels.INFO, "starting position", currentPosition)
    while (currentPosition) {
        debugLog(ErrorLevels.VERBOSE, "curr position", currentPosition)
        const newPosition = move(currentPosition)

        if (!newPosition) {
            debugLog(ErrorLevels.INFO, `Exiting from ${currentPosition.direction}`)
            currentPosition = newPosition
            break;
        }

        debugLog(ErrorLevels.VERBOSE, "next position", newPosition)
        switch (newPosition.space) {
            case ".":
                lines[newPosition.row][newPosition.col] = "X";
                answer += 1
                currentPosition = {...newPosition, direction: currentPosition.direction}
                break;
            case "X":
                currentPosition = {...newPosition, direction: currentPosition.direction}
                break;
            case "#":
                if (typeof currentPosition.direction === "undefined") {
                    debugLog(ErrorLevels.ERROR, "CRITICAL ERROR: turning with no direction found", currentPosition)
                    return
                }
                const newDirection = nextDirection(currentPosition.direction)
                debugLog(ErrorLevels.INFO, `Turning from ${currentPosition.direction} to ${newDirection} at ${currentPosition.row},${currentPosition.col}`)
                currentPosition = {...currentPosition, direction: newDirection}
        }
    }
    
    debugLog(ErrorLevels.OUTPUT, answer)
}