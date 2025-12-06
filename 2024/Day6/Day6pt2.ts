import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
    const lines = getData().map(line => line.split('')) as ("." | "#" | "^" | "X")[][];

    
    
    let startRow = 0, startCol = 0, loopsToRun = 0;
    for (let row in lines) {
        for (let col in lines[row]) {
            if (lines[row][col] === "^") {
                startRow = parseInt(row);
                startCol = parseInt(col);
                lines[row][col] = "X";
            }
            if (lines[row][col] !== "#") {
                loopsToRun += 1
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
        direction?: Direction,
        looping?: boolean,
        lastNew?: number[],
        done: boolean
    }



    const traverse = (lines: ("." | "#" | "^" | "X")[][]) => {
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

        const move = ({row, col, direction}: Position) => {
            switch (direction) {
                case Direction.UP: return up(row, col)
                case Direction.RIGHT: return right(row, col)
                case Direction.DOWN: return down(row, col)
                case Direction.LEFT: return left(row, col)
            }
        }

        let currentPosition: Position = {space: "^", row: startRow, col: startCol, direction: Direction.UP, looping: false, lastNew: [startRow, startCol], done: false}
        debugLog(ErrorLevels.INFO, "starting position", currentPosition)
        while (!currentPosition.done && !currentPosition.looping) {
            debugLog(ErrorLevels.VERBOSE, "curr position", currentPosition)
            const newPosition = move(currentPosition)
    
            if (!newPosition) {
                debugLog(ErrorLevels.INFO, `Exiting from ${currentPosition.direction}`)
                currentPosition.done = true
                break;
            }
    
            debugLog(ErrorLevels.VERBOSE, "next position", newPosition)
            switch (newPosition.space) {
                case ".":
                    lines[newPosition.row][newPosition.col] = "X";
                    currentPosition = {...newPosition, direction: currentPosition.direction, lastNew: [newPosition.row, newPosition.col], done: false}
                    break;
                case "X":
                    let looping = false
                    if (newPosition.row === currentPosition.lastNew?.[0] && newPosition.col === currentPosition.lastNew?.[1]) looping = true
                    currentPosition = {...newPosition, direction: currentPosition.direction, looping, done: false, lastNew: currentPosition.lastNew}
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
        debugLog(ErrorLevels.INFO, `Final board\n${lines.join("\n")}`)
        return currentPosition
    }

    let answer = 0, loopsRan = 0;

    for (let row in lines) {
        for (let col in lines[row]) {
            if (lines[row][col] === ".") {
                loopsRan += 1
                const newLines = JSON.parse(JSON.stringify(lines))
                newLines[row][col] = "#";
                const finalPosition = traverse(newLines)
                if (finalPosition?.looping) answer += 1
                debugLog(ErrorLevels.OUTPUT, `Running ${loopsRan}/${loopsToRun} - found: ${answer}`)
            }
        }
    }

    
    debugLog(ErrorLevels.OUTPUT, answer)
}