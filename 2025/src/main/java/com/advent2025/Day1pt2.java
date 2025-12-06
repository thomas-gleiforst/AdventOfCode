package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day1pt2 {
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
        Integer toggles = 0;

        for (String line : rawInputs) {
            String direction = line.substring(0, 1);
            Integer rawvalue = Integer.parseInt(line.substring(1));
            Integer value = rawvalue;
            if (debug) System.out.println();
            
            if (value / 100 > 0) {
                toggles += value / 100;
                value = value % 100;
            }

            if (direction.equals("L")) {
                if (safe > 0 && safe - value <= 0) {
                    safe += 100;
                    toggles += 1;
                }
                safe -= value;
            } else if (direction.equals("R")) {
                if (safe < 100 && safe + value >= 100) {
                    safe -= 100;
                    toggles += 1;
                }
                safe += value;
            }
            if (safe < 0) {
                safe += 100;
            } else if (safe > 100) {
                safe -= 100;
            }

            if (debug) System.out.println("Direction: " + direction + " Value: " + rawvalue + "  |  Current Safe Value: " + safe + " Toggles: " + toggles);
        }

        if (debug) System.out.println("Raw Safe Value: " + safe);
        System.out.println("Final Output: " + toggles);
    }
}
