package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws Exception {
        Boolean debug = true;
        Path inputPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("2025/input/Day1.txt");

        if (!Files.exists(inputPath)) {
            System.out.println("No input file found at: " + inputPath.toString());
            System.out.println("Put your puzzle input in 2025/input.txt or pass a path as first arg.");
            return;
        }

        List<String> rawInputs = Files.readAllLines(inputPath);
        if (debug) System.out.println("Loaded input (" + rawInputs.size() + " lines)");

        Integer safe = 50;
        Integer isMult100 = 0;

        for (String line : rawInputs) {
            String direction = line.substring(0, 1);
            Integer value = Integer.parseInt(line.substring(1));
            if (debug) System.out.println("Direction: " + direction + " Value: " + value);

            if (direction.equals("L")) {
                safe -= value;
            } else if (direction.equals("R")) {
                safe += value;
            }
            
            if (safe % 100 == 0) {
                isMult100 += 1;
            }

            if (debug) System.out.println("Current Safe Value: " + safe + " isMult100: " + isMult100);
        }

        if (debug) System.out.println("Raw Safe Value: " + safe);
        System.out.println("Final Output: " + isMult100);
    }
}
