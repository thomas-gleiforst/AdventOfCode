import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
    interface Position {
        height: number,
        row: number,
        col: number,
        score: number,
    }

    const zeroes: Position[] = []
    let reached: Position[] = []
    const lines = getData().map((l, row) => l.split("").map((i, col) => {
        const pos = {
            height: parseInt(i),
            row,
            col,
            score: 0,
        }
        if (!pos.height) zeroes.push(pos)
        return pos
    }));

    let answer = 0
    const UNREACHABLE: Position = {row: -1, col: -1, height: -1, score: 0}

    const up = (pos: Position) => {
        const {row, col} = pos
        if (row-1 < 0) return UNREACHABLE
        const newPos = lines[row-1][col];
        return newPos
    }

    const down = (pos: Position) => {
        const {row, col} = pos
        if (row+1 >= lines.length) return UNREACHABLE
        const newPos = lines[row+1][col]
        return newPos
    }

    const left = (pos: Position) => {
        const {row, col} = pos
        if (col-1 < 0) return UNREACHABLE
        const newPos = lines[row][col-1]
        return newPos
    }

    const right = (pos: Position) => {
        const {row, col} = pos
        if (col+1 >= lines[row].length) return UNREACHABLE
        const newPos = lines[row][col+1]
        return newPos
    }

    const traverse = (pos: Position, lastPos?: Position): number => {
        if (JSON.stringify(pos) === JSON.stringify(UNREACHABLE)) {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "uncreachable")
            return 0
        }
        if (pos.height - 1 !== lastPos?.height) {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "too high from", JSON.stringify(lastPos))
            return 0
        }
        if (reached.find(r => r.row === pos.row && r.col === pos.col)) {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "already reached")
            return pos.score > 0 ? pos.score : 0
        }
        if (pos.height === 9) {
            debugLog(ErrorLevels.DEBUG, JSON.stringify(pos), "PEAKED!!")
            reached.push(pos)
            pos.score += 1
            return pos.score
        }
        
        reached.push(pos)
        debugLog(ErrorLevels.DEBUG, "Visiting", JSON.stringify(pos), "from", JSON.stringify(lastPos))
        
        debugLog(ErrorLevels.VERBOSE, "UP")
        
        let newScore = 0
        newScore += traverse(up(pos), pos)
        
        debugLog(ErrorLevels.VERBOSE, "RIGHT")
        newScore += traverse(right(pos), pos)

        debugLog(ErrorLevels.VERBOSE, "DOWN")
        newScore += traverse(down(pos), pos)
        
        debugLog(ErrorLevels.VERBOSE, "LEFT")
        newScore += traverse(left(pos), pos)
        pos.score = newScore
        
        debugLog(ErrorLevels.DEBUG, "leaving", JSON.stringify(pos))
        debugLog(ErrorLevels.DEBUG, "\n")
        return pos.score
    }

    let lastAnswer = 0
    for (const i in zeroes) {
        const zero = zeroes[i]
        debugLog(ErrorLevels.OUTPUT, `Expoloring ${parseInt(i)+1}/${zeroes.length}`)
        reached.forEach(pos => pos.score = 0)
        reached = []
        answer += traverse(zero, UNREACHABLE) ?? 0
        debugLog(ErrorLevels.INFO, answer-lastAnswer)
        debugLog(ErrorLevels.DEBUG, "\n")
        lastAnswer=answer
    }

    debugLog(ErrorLevels.OUTPUT, answer)
}