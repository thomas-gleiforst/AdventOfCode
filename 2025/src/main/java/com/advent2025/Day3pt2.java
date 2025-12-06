package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day3pt2 {
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

        Long output = 0L;

        for (String input : rawInputs) {
            if (debug) System.out.println("Processing Input: " + input);
            List<Integer> batteries = List.of(input.split("")).stream().map(Integer::parseInt).toList();
            List<Integer> enabled = new ArrayList<Integer>();
            while (enabled.size() < 12) {
                Integer largest = batteries.subList(0, batteries.size()-(11-enabled.size())).stream().max(Integer::compareTo).orElse(batteries.get(0));
                batteries = batteries.subList(batteries.indexOf(largest)+1, batteries.size());
                enabled.add(largest);
            }
            if (debug) System.out.println("Enabled Batteries: " + enabled);

            List<String> enabledStr = enabled.stream().map(String::valueOf).toList();
            String bankStr = enabledStr.stream().reduce("", String::concat);
            Long bank = Long.valueOf(bankStr);

            if (debug) System.out.println("Bank size: " + bank);
            output += bank;
        }

        System.out.println("Final Output: " + output);
    }
}
