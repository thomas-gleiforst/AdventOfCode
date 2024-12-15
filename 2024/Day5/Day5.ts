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

    const pagesAfterOkay = (num: number, line: number[]) => {
        if (!ordering?.[num]?.length) {
            debugLog(ErrorLevels.DEBUG, `Pages after ${num} valid, as ${line} have no orderings for it`)
            return true
        }
        if (!line.length) {
            debugLog(ErrorLevels.DEBUG, `Pages after ${num} valid, as ${line} is empty`)
            return true
        }

        const issueFound = line.find(page => {
            const currPageIsEntryForNum = ordering?.[page]?.includes(num);
            currPageIsEntryForNum && debugLog(ErrorLevels.DEBUG, `Pages before ${num} invalid as ${ordering?.[page]} includes ${num}`)
            
            return currPageIsEntryForNum
        })
        issueFound && debugLog(ErrorLevels.DEBUG, `Pages after ${num} (${line}) rejected due to later entry: ${issueFound}`)

        return !issueFound
    }

    const pagesBeforeOkay = (num: number, line: number[]) => {
        if (!ordering?.[num]?.length) {
            debugLog(ErrorLevels.DEBUG, `Pages before ${num} valid, as ${line} have no orderings for it`)
            return true
        }
        if (!line.length) {
            debugLog(ErrorLevels.DEBUG, `Pages before ${num} valid, as ${line} is empty`)
            return true
        }
        const issueFound = line.find(page => {
            debugLog(ErrorLevels.VERBOSE, page, num, !ordering?.[num]?.includes(page), ordering?.[page]?.includes(num))
            
            const currNumHasEntryForPriorPage = ordering?.[num]?.includes(page)
            currNumHasEntryForPriorPage && debugLog(ErrorLevels.DEBUG, `${num} invalid as ${ordering?.[num]} includes ${page}`)
            
            return currNumHasEntryForPriorPage
        })
        issueFound && debugLog(ErrorLevels.DEBUG, `Pages before ${num} (${line}) rejected due to prior entry: ${issueFound}`)
        return !issueFound 
    }

    let answer = 0
    
    update.forEach((book, bookIndex) => {
        let right_order = true

        book.forEach((page, index) => {
            const pagesAfterFailed = !pagesAfterOkay(page, book.slice(index+1))
            const pagesBeforeFailed = !pagesBeforeOkay(page, book.slice(0, index))
            if (
                pagesAfterFailed || pagesBeforeFailed
            ) {
                debugLog(ErrorLevels.INFO, `${page} in ${book} not in correct order${pagesAfterFailed ? " - pages after failed" : ""}${pagesBeforeFailed ? " - pages before failed" : ""}`)
                right_order = false
            }

            debugLog(ErrorLevels.VERBOSE, page, pagesAfterFailed, pagesBeforeFailed)
        })

        const bookScore = book[Math.floor(book.length/2)]
        if (right_order) answer += bookScore

        debugLog(ErrorLevels.DEBUG, `Book #${bookIndex+1}: ${book} is in the ${right_order ? "right order" : "wrong order"}, with a new score ${answer} \n`)
    })
    
    debugLog(ErrorLevels.OUTPUT, answer)
}