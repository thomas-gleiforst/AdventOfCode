import { debugLog, ErrorLevels, getData } from '../utils';



export const Part1 = () => {
    const lines = getData();
    const sepearator = lines.findIndex(i => i === "")
    const page_ordering = lines.slice(0, sepearator).map(entry => entry.split('|'))
    const update = lines.slice(sepearator+1).map(entry => entry.split(',').map(i => parseInt(i)))

    debugLog(ErrorLevels.DEBUG, page_ordering, update)

    const ordering: {[key: string]: number[]} = {}
    page_ordering.forEach(([key, val]) => {
        const current = ordering[key] ?? []
        ordering[key] = current.concat(parseInt(val)).sort()
    })

    debugLog(ErrorLevels.DEBUG, ordering)

    const checkPagesAfter = (num: number, line: number[]) => {
        if (!ordering?.[num]?.length || !line.length) return true
        return !!line.find(page => ordering?.[num]?.includes(page))
    }

    const checkPagesBefore = (num: number, line: number[]) => {
        if (!ordering?.[num]?.length || !line.length) return true
        return !!line.find(page => {
            debugLog(ErrorLevels.VERBOSE, page, num, !ordering?.[num]?.includes(page), ordering?.[page]?.includes(num))
            return ordering?.[num]?.includes(page) && !ordering?.[page]?.includes(num)
        })
    }

    let answer = 0
    
    update.forEach(book => {
        let right_order = true

        book.forEach((page, index) => {
            if (
                !checkPagesAfter(page, book.slice(index+1)) ||
                !checkPagesBefore(page, book.slice(0, index))
            ) right_order = false
            debugLog(ErrorLevels.VERBOSE, page, checkPagesAfter(page, book.slice(index+1)), checkPagesBefore(page, book.slice(0, index)))
        })

        if (right_order) answer += book[Math.floor(book.length/2)]

        debugLog(ErrorLevels.DEBUG, right_order, book[Math.floor(book.length/2)])
    })
    
    debugLog(ErrorLevels.OUTPUT, answer)
}