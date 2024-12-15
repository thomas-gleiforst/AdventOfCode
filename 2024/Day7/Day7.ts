import { availableMemory } from 'process';
import { debugLog, ErrorLevels, getData } from '../utils';

export const Part1 = () => {
    const lines = getData();
    
    let answer = 0

    type Operator = ("*" | "+")
    const operators: Operator[]  = ["*", "+"]

    const doOp = (op: Operator, left: number, right: number) => {
        switch (op) {
            case "*": return left * right;
            case "+": return left + right;
        }
    }


    for (let line of lines) {
        const [targetString, eqString] = line.split(": ")
        const target = parseInt(targetString)
        const sequence = eqString.split(" ").map(i => parseInt(i))

        
        const tryOps = (sequence: number[]): number | false => {
            if (sequence.length === 1) return sequence[0]

            const [first, second, ...rest] = sequence

            for (const op of operators) {
                let result = doOp(op, first, second)
                if (target === tryOps([result, ...rest])) {
                    return target
                }
            }
            return false
        }

        const attempt = tryOps(sequence)
        if (attempt) {
            answer += attempt
        }
    }
    
    debugLog(ErrorLevels.OUTPUT, answer)
}