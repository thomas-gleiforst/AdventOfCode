import { debugLog, ErrorLevels } from "../utils"
import { Part1 } from "./DayX"
import { Part2 } from "./DayXpt2"

const args = process.argv
const [_1, day, _3, _4, part] = args

try {
    if (part === "pt2") {
        Part2()
    } else {
        Part1()
    }
} catch (e) {
    debugLog(ErrorLevels.ERROR, 'uncaught error in', day, part, '\n', e)
}