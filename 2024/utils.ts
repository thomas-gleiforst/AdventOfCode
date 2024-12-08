import * as fs from 'fs';

const args = process.argv
const [_, file_in, data, level_in, ...extras] = args

export enum ErrorLevels {
    DEBUG,
    INFO,
    ERROR,
    OUTPUT,
}

let level: ErrorLevels = ErrorLevels.DEBUG;
Object.entries(ErrorLevels).forEach(([key, val]) => {
    if (key === level_in?.toUpperCase()) level = val as ErrorLevels
}, 0)

const folder = file_in.split('\\').at(-1)

export const debugLog = (state: ErrorLevels, ...params: any[]) => {
    if (state >= level) console.log(...params)
}

export const getData = () => {
    let lines: string[] = [];

    try {
        const input = fs.readFileSync(`./${folder}/${data}.txt`, 'utf8')
        debugLog(ErrorLevels.INFO, "input\n" + input)
        lines = input.split('\r\n')
    } catch (e) {
        debugLog(ErrorLevels.ERROR, e)
    }

    return lines;
}
