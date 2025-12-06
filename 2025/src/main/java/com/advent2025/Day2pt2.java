package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day2pt2 {
    public static void main(String[] args) throws Exception {
        Boolean debug = true;
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
            // if (debug) System.out.println("Processing Range: " + idRange);
            String[] ranges = idRange.split("-");
            Long start = Long.valueOf(ranges[0]);
            Long end = Long.valueOf(ranges[1]);

            for (Long i = start; i <= end; i++) {
                // if (debug) System.out.println("Processing ID: " + i);
                String idStr = i.toString();

                for (Integer j = 1; j <= idStr.length() / 2; j++) {
                    if (idStr.length() % j == 0) {
                        String firstPart = idStr.substring(0, j);
                        String full = firstPart.repeat(idStr.length() / j);

                        if (full.equals(idStr)) {
                            output += i;
                            if (debug) System.out.println("Found Matching ID: " + i + " " + firstPart);
                            break;
                        } else {
                            // if (debug) System.out.println("No match ID: " + i + " " + firstPart + " " + idStr.length() / j);
                        }
                    }
                }
            }   
        }

        System.out.println("Final Output: " + output);
    }
}
