package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Range {
    Long start;
    Long end;

    public Range(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public Boolean inRange(Long value) {
        return value >= this.start && value <= this.end;
    }

    public Boolean isGreaterThan(Range other) {
        return this.start > other.start;
    }

    public Boolean isLessThan(Range other) {
        return this.start < other.start;
    }
}

public class Day5 {

    public static void main(String[] args) throws Exception {
        Boolean debug = false;
        Path inputPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("2025/input/Day3.txt");

        if (!Files.exists(inputPath)) {
            System.out.println("No input file found at: " + inputPath.toString());
            System.out.println("Put your puzzle input in 2025/input.txt or pass a path as first arg.");
            return;
        }

        List<String> rawInputs = Files.readAllLines(inputPath);
        if (debug) System.out.println("Loaded input (" + rawInputs.size() + " lines)");

        List<Range> ranges = new ArrayList<>();
        Integer output = 0;
        Boolean oneWayFlag = false;
        for (String input : rawInputs) {
            if (input.isBlank()) {
                oneWayFlag = true;
                System.out.println("Sorting List");
                ranges.sort((a, b) -> a.isLessThan(b) ? -1 : a.isGreaterThan(b) ? 1 : 0);                
                System.out.println("Switching to Values Processing");
            } else if (!oneWayFlag) {
                if (debug) System.out.println("Processing Range: " + input);
                String[] parts = input.split("-");
                Long start = Long.parseLong(parts[0]);
                Long end = Long.parseLong(parts[1]);
                Range range = new Range(start, end);
                ranges.add(range);
            } else {
                Long value = Long.parseLong(input);
                if (debug) System.out.println("Processing Value: " + value);

                for (Range range : ranges) {
                    if (range.inRange(value)) {
                        output++;
                        break;
                    }
                }
            }
        }

        System.out.println("Final Output: " + output);
    }
}
