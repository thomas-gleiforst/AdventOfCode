import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    interface Position {
        row: number,
        col: number,
        reached: boolean
        indicator: string,

        up?: boolean
        right?: boolean
        down?: boolean
        left?: boolean
    }
    interface Region {
        indicator: string
        perimiter: number
        positions: Position[]
        area?: number
    }

    let regions: Region[] = []

    const lineRaw = getData()
    const lines = lineRaw.map((row, rowI) => row.split("").map((col, colI) => {
        return {
            row: rowI,
            col: colI,
            reached: false,
            indicator: col,
        }
    }));

    debugLog(ErrorLevels.OUTPUT, lineRaw)

    const UNREACHABLE: Position = {row: -1, col: -1,reached: true, indicator: "*"}

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

    const traverse = (pos: Position, region?: Region) => {
        if (JSON.stringify(pos) === JSON.stringify(UNREACHABLE)) {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "uncreachable")
            return
        }
        if (pos.reached) {
            debugLog(ErrorLevels.DEBUG, JSON.stringify(pos), "already reached")
            return pos.indicator === region?.indicator
        }
        if (typeof region === "undefined") {
            region = {
                indicator: pos.indicator,
                perimiter: 0,
                positions: []
            }
            regions.push(region)
            debugLog(ErrorLevels.INFO, "Creating new region", region)
        }
        if (region?.indicator === pos.indicator) {
            region.positions.push(pos)
            pos.reached = true
            debugLog(ErrorLevels.DEBUG, "Pos fits in existing region", pos)
        } else {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "not in", region)
            return
        }
        
        debugLog(ErrorLevels.VERBOSE, "UP")
        pos.up = traverse(up(pos), region)
        
        debugLog(ErrorLevels.VERBOSE, "RIGHT")
        pos.right = traverse(right(pos), region)

        debugLog(ErrorLevels.VERBOSE, "DOWN")
        pos.down = traverse(down(pos), region)
        
        debugLog(ErrorLevels.VERBOSE, "LEFT")
        pos.left = traverse(left(pos), region)
        
        debugLog(ErrorLevels.VERBOSE, "\n")
        return true
    }

    for (const row of lines) {
        for (const pos of row) {
            traverse(pos) ?? 0
            debugLog(ErrorLevels.INFO, "\n")
        }
    }

    regions = regions.map(r => {
        return {
            ...r,
            area: r.positions.length,
            perimiter: r.positions.length*4 - r.positions.reduce(
                (total, pos) => {
                    return total += [pos.down, pos.up, pos.right, pos.left].filter(i => i).length
                }
            , 0)
        }
    })
    debugLog(ErrorLevels.INFO, regions)

    debugLog(ErrorLevels.OUTPUT, regions.reduce((total, region) => total += ((region.area ?? 1) * region.perimiter), 0))
}