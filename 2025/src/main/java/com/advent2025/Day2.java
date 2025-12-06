package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {
    public static void main(String[] args) throws Exception {
        Boolean debug = false;
        Path inputPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("2025/input/Day2.txt");

        if (!Files.exists(inputPath)) {
            System.out.println("No input file found at: " + inputPath.toString());
            System.out.println("Put your puzzle input in 2025/input.txt or pass a path as first arg.");
            return;
        }

        String rawInput = Files.readString(inputPath);
        if (debug) System.out.println("Loaded input (" + rawInput.length() + " lines)");

        List<String> input = List.of(rawInput.split(","));
        Long output = 0L;

        for (String idRange : input) {
            if (debug) System.out.println("Processing Range: " + idRange);
            String[] ranges = idRange.split("-");
            Long start = Long.valueOf(ranges[0]);
            Long end = Long.valueOf(ranges[1]);

            for (Long i = start; i <= end; i++) {
                if (debug) System.out.println("Processing ID: " + i);
                String idStr = i.toString();

                String firstHalf = idStr.substring(0, idStr.length()/2);
                String secondHalf = idStr.substring(idStr.length()/2); 

                if (firstHalf.equals(secondHalf)) {
                    output += i;
                }
            }   
        }

        System.out.println("Final Output: " + output);
    }
}
