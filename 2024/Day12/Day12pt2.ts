import { debugLog, ErrorLevels, getData } from '../utils';

export const Part2 = () => {
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
        sides: number
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

    const traverse = (pos: Position, lastPos?: Position, region?: Region) => {
        if (JSON.stringify(pos) === JSON.stringify(UNREACHABLE)) {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "uncreachable")
            return false
        }
        if (pos.reached) {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "already reached")
            return pos.indicator === region?.indicator
        }
        if (typeof region === "undefined") {
            region = {
                indicator: pos.indicator,
                perimiter: 0,
                positions: [],
                sides: 0
            }
            regions.push(region)
            debugLog(ErrorLevels.INFO, "Creating new region", region)
        }
        if (region?.indicator === pos.indicator) {
            region.positions.push(pos)
            pos.reached = true
            debugLog(ErrorLevels.VERBOSE, "Pos fits in existing region", pos)
        } else {
            debugLog(ErrorLevels.VERBOSE, JSON.stringify(pos), "not in", region)
            return false
        }
        
        debugLog(ErrorLevels.VERBOSE, "UP")
        pos.up = traverse(up(pos), pos, region)
        
        debugLog(ErrorLevels.VERBOSE, "RIGHT")
        pos.right = traverse(right(pos), pos, region)

        debugLog(ErrorLevels.VERBOSE, "DOWN")
        pos.down = traverse(down(pos), pos, region)

        debugLog(ErrorLevels.VERBOSE, "LEFT")
        pos.left = traverse(left(pos), pos, region)
        
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

    regions.forEach(r => {
        r.sides = 4;
        r.positions.forEach((pos, i) => {
            const upPos: Position = up(pos)
            const rightPos: Position = right(pos)
            const leftPos: Position = left(pos)
            const downPos: Position = down(pos)
            if (pos.up && (rightPos?.up || leftPos?.up)) {
                debugLog(ErrorLevels.DEBUG, pos, "adding a side up")
                r.sides += 1
            }
            if (pos.right && (upPos?.right || downPos?.right)) {
                debugLog(ErrorLevels.DEBUG, pos, "adding a side right")
                r.sides += 1
            }
    
            if (pos.down && (rightPos?.down || leftPos?.down)) {
                debugLog(ErrorLevels.DEBUG, pos, "adding a side down")
                r.sides += 1
            }
    
            if (pos.left && (upPos?.left || downPos?.left)) {
                debugLog(ErrorLevels.DEBUG, pos, "adding a side left")
                r.sides += 1
            }
        })
    })

    debugLog(ErrorLevels.INFO, regions)

    debugLog(ErrorLevels.OUTPUT, regions.reduce((total, region) => total += ((region.area ?? 1) * region.sides), 0))
}