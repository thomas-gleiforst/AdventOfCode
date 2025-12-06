import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    interface Position {
        height: number,
        row: number,
        col: number,
        score?: number,
    }

    const zeroes: Position[] = []
    let reached: Position[] = []
    const lineRaw = getData()
    const lines = lineRaw.map((l, row) => l.split("").map((i, col) => {
        const pos = {
            height: parseInt(i),
            row,
            col,
        }
        if (!pos.height) zeroes.push(pos)
        return pos
    }));

    debugLog(ErrorLevels.OUTPUT, lineRaw)


    let answer = 0

    const up = (pos: Position) => {
        const {row, col} = pos
        if (row-1 < 0) return pos
        const newPos = lines[row-1][col];
        return newPos
    }

    const down = (pos: Position) => {
        const {row, col} = pos
        if (row+1 >= lines.length) return pos
        const newPos = lines[row+1][col]
        return newPos
    }

    const left = (pos: Position) => {
        const {row, col} = pos
        if (col-1 < 0) return pos
        const newPos = lines[row][col-1]
        return newPos
    }

    const right = (pos: Position) => {
        const {row, col} = pos
        if (col+1 >= lines[row].length) return pos
        const newPos = lines[row][col+1]
        return newPos
    }

    const traverse = (pos: Position, lastPos?: Position, score = 0) => {
        if (reached.find(r => r.row === pos.row && r.col === pos.col)) {
            debugLog(ErrorLevels.DEBUG, JSON.stringify(pos), "already reached")
            return 
        }
        if (pos.height - 1 !== lastPos?.height) {
            debugLog(ErrorLevels.DEBUG, JSON.stringify(pos), "too high from", JSON.stringify(lastPos))
            return 
        }
        if (pos.height === 9) {
            debugLog(ErrorLevels.DEBUG, JSON.stringify(pos), "PEAKED!!")
            answer += 1
            reached.push(pos)
            return 
        }
        
        reached.push(pos)
        debugLog(ErrorLevels.DEBUG, "Visiting", JSON.stringify(pos), "from", JSON.stringify(lastPos))
        
        debugLog(ErrorLevels.VERBOSE, "UP")
        traverse(up(pos), pos) ?? 0
        
        debugLog(ErrorLevels.VERBOSE, "RIGHT")
        traverse(right(pos), pos) ?? 0

        debugLog(ErrorLevels.VERBOSE, "DOWN")
        traverse(down(pos), pos) ?? 0
        
        debugLog(ErrorLevels.VERBOSE, "LEFT")
        traverse(left(pos), pos) ?? 0
        
        debugLog(ErrorLevels.VERBOSE, "\n")
        return score
    }

    let lastAnswer = 0
    for (const zero of zeroes) {
        reached = []
        traverse(zero, {row: -1, col: -1, height: -1}) ?? 0
        debugLog(ErrorLevels.INFO, answer-lastAnswer)
        debugLog(ErrorLevels.DEBUG, "\n")
        lastAnswer=answer
    }

    debugLog(ErrorLevels.OUTPUT, answer)
}