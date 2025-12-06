package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day3 {
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

        Integer output = 0;

        for (String input : rawInputs) {
            if (debug) System.out.println("Processing Input: " + input);
            List<Integer> batteries = List.of(input.split("")).stream().map(Integer::parseInt).toList();

            Integer largest = batteries.subList(0, batteries.size()-1).stream().max(Integer::compareTo).orElse(0);

            List<Integer> after = batteries.subList(batteries.indexOf(largest)+1, batteries.size());
            Integer secondLargest = after.stream().max(Integer::compareTo).orElse(0);

            Integer bank = Integer.valueOf(largest.toString() + secondLargest.toString());
            if (debug) System.out.println("Bank size: " + bank);
            output += bank;
        }

        System.out.println("Final Output: " + output);
    }
}
