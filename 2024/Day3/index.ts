import { Part1 } from "./Day3"
import { Part2 } from "./Day3pt2"

const args = process.argv
const [_1, _2, _3, _4, part] = args

if (part === "pt2") {
    Part2()
} else {
    Part1()
}