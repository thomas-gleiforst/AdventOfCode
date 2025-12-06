package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Range2 {
    Long start;
    Long end;
    Long practicalStart;
    Long practicalEnd;

    public Range2(Long start, Long end) {
        this.start = start;
        this.end = end;
        this.practicalStart = start;
        this.practicalEnd = end;
    }

    public Boolean inRange(Long value) {
        return value >= this.start && value <= this.end;
    }

    public Boolean isGreaterThan(Range2 other) {
        return this.start > other.start;
    }

    public Boolean isLessThan(Range2 other) {
        return this.start < other.start;
    }

    public Void trimOverlap(Range2 other) {
        if (this.end <= other.practicalEnd) {
            this.practicalEnd = null;
            this.practicalStart = null;
        } else if (this.start <= other.practicalEnd) {
            this.practicalStart = other.practicalEnd + 1;
        }
        return null;
    }

    public Long size() {
        if (this.practicalStart == null || this.practicalEnd == null) {
            return 0L;
        }
        return this.practicalEnd - this.practicalStart + 1;
    }

    public String functionalRange() {
        return "Range after trim: " + this.practicalStart + "-" + this.practicalEnd;
    }

    public String originalRange() {
        return "Range: " + this.start + "-" + this.end;
    }
}

public class Day5pt2 {

    public static void main(String[] args) throws Exception {
        Boolean debug = true;
        Path inputPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("2025/input/Day3.txt");

        if (!Files.exists(inputPath)) {
            System.out.println("No input file found at: " + inputPath.toString());
            System.out.println("Put your puzzle input in 2025/input.txt or pass a path as first arg.");
            return;
        }

        List<String> rawInputs = Files.readAllLines(inputPath);
        if (debug) System.out.println("Loaded input (" + rawInputs.size() + " lines)");

        List<Range2> ranges = new ArrayList<>();
        Long output = 0L;
        Boolean oneWayFlag = false;
        for (String input : rawInputs) {
            if (input.isBlank()) {
                oneWayFlag = true;
                System.out.println("Sorting List");
                ranges.sort((a, b) -> a.isLessThan(b) ? -1 : a.isGreaterThan(b) ? 1 : 0);                
                System.out.println("Skipping Value Processing");
                Range2 previous = null;
                for (Range2 range : ranges) {
                    if (debug) System.out.println(range.originalRange());
                    if (previous != null) {
                        range.trimOverlap(previous);
                    }
                    if (range.practicalEnd != null) {
                        previous = range;
                    }
                    Long rangeSize = range.size();
                    if (debug) System.out.println("    " + range.functionalRange());
                    if (debug) System.out.println("    Size after trim: " + rangeSize);
                    output += rangeSize;
                    if (debug) System.out.println("    Running Total: " + output);
                }
                break;
            } else if (!oneWayFlag) {
                if (debug) System.out.println("Processing Range2: " + input);
                String[] parts = input.split("-");
                Long start = Long.parseLong(parts[0]);
                Long end = Long.parseLong(parts[1]);
                Range2 range = new Range2(start, end);
                ranges.add(range);
            }
        }

        System.out.println("Final Output: " + output);
    }
}
