package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Day9 {
    public static Boolean debug = false;

    protected static class Pos {
        Integer x;
        Integer y;

        public Pos(Integer input_x, Integer input_y) {
            x = input_x;
            y = input_y;
        }

        @Override
        public String toString() {
            return "Pos (" + x + "," + y + ")";
        }

        public Boolean isDiagonal(Pos b) {
            return (this.x-b.x) - (this.y-b.y) == 0;
        }

        public Long area(Pos b) {
            return (long) (Math.abs(this.x-b.x) + 1) * (Math.abs(this.y-b.y) + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        Path inputPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("2025/input/Day3.txt");

        if (!Files.exists(inputPath)) {
            System.out.println("No input file found at: " + inputPath.toString());
            System.out.println("Put your puzzle input in 2025/input.txt or pass a path as first arg.");
            return;
        }

        List<String> rawInputs = Files.readAllLines(inputPath);
        if (Day9.debug) System.out.println("Loaded input (" + rawInputs.size() + " lines)");

        List<Pos> allPos = new ArrayList<>();
        for (String input : rawInputs) {
            List<Integer> cords = List.of(input.split(",")).stream().map(Integer::parseInt).toList();
            allPos.add(new Pos(cords.get(0), cords.get(1)));
        }

        if (Day9.debug) System.out.println(allPos);

        List<Long> areas = new ArrayList<>();

        for (int i = 0; i < allPos.size() - 1; i++) {
            Pos a = allPos.get(i);
            for (int j = i + 1; j < allPos.size(); j++) {
                Pos b = allPos.get(j);
                // if (a.isDiagonal(b)) {
                    areas.add(a.area(b));
                    if (Day9.debug) System.out.println(a + " is diagonal " + b + " with area " + a.area(b));
                // }
            }
        }
        if (Day9.debug) System.err.println(areas);

        areas.sort((a, b) -> a < b ? 1 : a > b ? -1 : 0);

        if (Day9.debug) System.err.println(areas);

        System.out.println("Final Output: " + areas.get(0));
    }
}
