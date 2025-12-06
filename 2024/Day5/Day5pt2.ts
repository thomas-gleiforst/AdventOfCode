import { debugLog, ErrorLevels, getData } from '../utils';



export const Part2 = () => {
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

    const pagesAfterOkay = (num: number, line: number[], baseIndex: number) => {
        if (!ordering?.[num]?.length) {
            debugLog(ErrorLevels.DEBUG, `Pages after ${num} valid, as ${line} have no orderings for it`)
            return {passed: true}
        }
        if (!line.length) {
            debugLog(ErrorLevels.DEBUG, `Pages after ${num} valid, as ${line} is empty`)
            return {passed: true}
        }

        const issueFound = line.findIndex(page => {
            const currPageIsEntryForNum = ordering?.[page]?.includes(num);
            currPageIsEntryForNum && debugLog(ErrorLevels.DEBUG, `Pages after ${num} invalid as ${page} has ${ordering?.[page]} including ${num}`)
            
            return currPageIsEntryForNum
        })
        issueFound > -1 && debugLog(ErrorLevels.DEBUG, `Pages after ${num} (${line}) rejected due to later entry: ${issueFound}`)

        return {passed: issueFound === -1, index: issueFound + baseIndex + 1}
    }

    const pagesBeforeOkay = (num: number, line: number[]) => {
        if (!ordering?.[num]?.length) {
            debugLog(ErrorLevels.DEBUG, `Pages before ${num} valid, as ${line} have no orderings for it`)
            return {passed: true}
        }
        if (!line.length) {
            debugLog(ErrorLevels.DEBUG, `Pages before ${num} valid, as ${line} is empty`)
            return {passed: true}
        }
        const issueFound = line.findIndex(page => {
            const currNumHasEntryForPriorPage = ordering?.[num]?.includes(page)
            currNumHasEntryForPriorPage && debugLog(ErrorLevels.DEBUG, `${num} invalid as ${ordering?.[num]} includes ${page}`)
            
            return currNumHasEntryForPriorPage
        })
        issueFound > -1 && debugLog(ErrorLevels.DEBUG, `Pages before ${num} (${line}) rejected due to prior entry: ${issueFound}`)
        
        return {passed: issueFound === -1, index: issueFound}
    }

    const verifyBook = (bookObj: {book: number[], passed: boolean}) => {
        const {book} = bookObj;
        for (const [indexStr, page] of Object.entries(book)) {
            const index = parseInt(indexStr)
            
            const pagesAfter = pagesAfterOkay(page, book.slice(index+1), index)
            const pagesBefore = pagesBeforeOkay(page, book.slice(0, index))

            if (!pagesAfter.passed && typeof pagesAfter.index !== "undefined") {
                debugLog(ErrorLevels.DEBUG, `${page} in ${book} not in correct order - pages after failed`)
                const newBook = [...book]
                const failedNum = newBook[pagesAfter.index] 
                newBook[pagesAfter.index] = page
                newBook[index] = failedNum
                debugLog(ErrorLevels.INFO, `Swapped ${book} to ${newBook} (at ${pagesAfter.index} = ${book[pagesAfter.index]})`)
                return {book: newBook, passed: false}
            } else if (!pagesBefore.passed && typeof pagesBefore.index !== "undefined") {
                debugLog(ErrorLevels.DEBUG, `${page} in ${book} not in correct order - pages before failed`)
                const newBook = [...book]
                const failedNum = newBook[pagesBefore.index] 
                newBook[pagesBefore.index] = page
                newBook[index] = failedNum
                debugLog(ErrorLevels.INFO, `Swapped ${book} to ${newBook} (at ${pagesBefore.index} = ${book[pagesBefore.index]})`)
                return {book: newBook, passed: false}
            }

            debugLog(ErrorLevels.VERBOSE, "End of Verify Book for", page, pagesAfter, pagesBefore)
        }
        return {book, passed: true}
    }

    let answer = 0
    
    update.forEach((book, bookIndex) => {
        let attempts = 0
        let bookObj = {book, passed: false}
        while (!bookObj.passed) {
            bookObj = verifyBook(bookObj)
            attempts += 1
        }
        debugLog(ErrorLevels.INFO, "Sorted book:", bookObj.book, `after ${attempts}`)

        const bookScore = bookObj.book[Math.floor(bookObj.book.length/2)]
        if (attempts > 1) {
            debugLog(ErrorLevels.VERBOSE, `Adding ${bookScore} for ${bookObj.book} w/ ${attempts} attempts`)
            answer += bookScore
        }
    })
    
    debugLog(ErrorLevels.OUTPUT, answer)
}