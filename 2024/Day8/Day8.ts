import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const lines = getData().map(line => line.split(""));
    
    const signalMap: {[key: string]: {row: number, col: number}[]} = {}
    const allSignals: {row: number, col: number}[] = []
    lines.forEach((row, rowI) => row.forEach((spot, colI) => {
        if (spot !== ".") {
            signalMap[spot] = [
                ...(signalMap[spot] ?? []),
                {row: rowI, col: colI}
            ]
            allSignals.push({row: rowI, col: colI})
        }
    }))

    debugLog(ErrorLevels.INFO, "Signal Map\n", signalMap)

    const posSet: {row: number, col: number}[] = [];
    
    const findPos = (testPos: {row: number, col: number}, testSet: {row: number, col: number}[]) => {
        return !!testSet.find(pos => pos.row === testPos.row && pos.col === testPos.col)
    }

    Object.entries(signalMap).forEach((entry) => {
        const [key, positions] = entry;
        positions.forEach((pos) => {
            const {row, col} = pos;
            positions.forEach((otherPos) => {
                const {row: refRow, col: refCol} = otherPos;
                const rowDiff = refRow - row;
                const colDiff = refCol - col;
                
                if (!rowDiff && !colDiff) return;
                
                const pos1 = {row: row + rowDiff, col: col + colDiff}
                const pos2 = {row: refRow + rowDiff, col: refCol + colDiff}
                const newPos = findPos(pos1, allSignals) ? pos2 : pos1
                
                if (newPos.col >= 0 && newPos.col < lines[0].length && newPos.row >= 0 && newPos.row < lines.length && !findPos(newPos, posSet)) posSet.push(newPos)
                
                debugLog(ErrorLevels.VERBOSE, {row, col}, "to", {refRow, refCol}, "is", {rowDiff, colDiff}, 'giving', newPos)
            })
        })
        debugLog(ErrorLevels.INFO, `${key} finished with ${posSet.length} so far`)
    })

    debugLog(ErrorLevels.OUTPUT, posSet.length)
}