package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6pt2 {

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

        List<String> rows = rawInputs.subList(0, rawInputs.size()-1);

        List<String> operators = Arrays.asList(rawInputs.get(rawInputs.size() - 1).split("\s+"));
        Long output = 0L;
        Integer index = 0;

        List<Integer> columnValues = new ArrayList<>();
        String latest = "";

        for (int col = 0; col < rows.get(0).length(); col++) {
            Boolean doOperation = false;

            for (int row = 0; row < rows.size(); row++) {
                char c = rows.get(row).charAt(col);
                latest += c;
                if (row == rows.size() - 1) {
                    latest = latest.trim();
                    if (latest.length() > 0) {
                        columnValues.add(Integer.parseInt(latest));
                    }
                    if (latest.length() == 0 || col == rows.get(0).length() - 1) {
                        doOperation = true;
                    }
                    latest = "";
                    break;
                }
            }

            if (doOperation) {
                if (debug) System.out.println("Doing Operation " + index + " on Column Values: " + columnValues);
                String operator = operators.get(index);
                if (debug) System.out.println("  Operation " + operator);
                index++;
                Long runningTotal = operator.equals("*") ? 1L : 0L;
                
                for (Integer value : columnValues) {
                    if (operator.equals("+")) {
                        runningTotal += value;
                    } else if (operator.equals("*")) {
                        runningTotal *= value;
                    }
                }
                columnValues.clear();
                output += runningTotal;
                if (debug) System.out.println("  Result: " + runningTotal);
                if (debug) System.out.println("  New total: " + output);
            }
        }

        System.out.println("Final Output: " + output);
    }
}
