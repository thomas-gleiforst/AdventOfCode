package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6 {

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

        List<List<Long>> rows = rawInputs.subList(0, rawInputs.size()-1).stream()
            .map(line -> List.of(line.trim().split("\s+")).stream().map(Long::parseLong).toList())
            .toList();

        List<String> operators = List.of(rawInputs.get(rawInputs.size() - 1).split("\s+"));
        Long output = 0L;

        for (int col = 0; col < rows.get(0).size(); col++) {
            if (debug) System.out.println("Processing Column: " + col);
            String operator = operators.get(col);
            Long runningTotal = operator.equals("*") ? 1L : 0L;
            
            for (List<Long> row : rows) {
                if (operator.equals("+")) {
                    runningTotal += row.get(col);
                } else if (operator.equals("*")) {
                    runningTotal *= row.get(col);
                }
            }
            output += runningTotal;
            if (debug) System.out.println("  Result: " + runningTotal);
            if (debug) System.out.println("  New total: " + output);
        }

        System.out.println("Final Output: " + output);
    }
}
