package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


class Grid {
    String HIT_MARKER = "@";
    List<List<String>> grid;

    public Grid(List<String> rawInputs) {
        this.grid = rawInputs.stream()
                .map(line -> List.of(line.split("")))
                .toList();
    }


    public String at(Integer row, Integer col) {
        return this.grid.get(row).get(col);
    }

    public Integer surronding(Integer row, Integer col) {
        Integer total = 0;
        if (up(row, col)) {
            System.out.print("up ");
            total++;
        }
        if (down(row, col)) {
            System.out.print("down ");
            total++;
        }
        if (left(row, col)) {
            System.out.print("left ");
            total++;
        }
        if (right(row, col)) {
            System.out.print("right ");
            total++;
        }
        if (up_left(row, col)) {
            System.out.print("up_left ");
            total++;
        }
        if (up_right(row, col)) {
            System.out.print("up_right ");
            total++;
        }
        if (down_left(row, col)) {
            System.out.print("down_left ");
            total++;
        }
        if (down_right(row, col)) {
            System.out.print("down_right ");
            total++;
        }
        return total;
    }
    
    public Boolean up(Integer row, Integer col) {
        if (row == 0) return false;
        return this.at(row-1, col).equals(HIT_MARKER);
    }

    public Boolean down(Integer row, Integer col) {
        if (row == this.grid.size() - 1) return false;
        return this.at(row+1, col).equals(HIT_MARKER);
    }
    
    public Boolean left(Integer row, Integer col) {
        if (col == 0) return false;
        return this.at(row, col-1).equals(HIT_MARKER);
    }

    public Boolean right(Integer row, Integer col) {
        if (col == this.grid.get(row).size() - 1) return false;
        return this.at(row, col+1).equals(HIT_MARKER);
    }

    public Boolean up_left(Integer row, Integer col) {
        if (row == 0 || col == 0) return false;
        return this.at(row-1, col-1).equals(HIT_MARKER);
    }
    public Boolean up_right(Integer row, Integer col) {
        if (row == 0 || col == this.grid.get(row).size() - 1) return false;
        return this.at(row-1, col+1).equals(HIT_MARKER);
    }
    public Boolean down_left(Integer row, Integer col) {
        if (row == this.grid.size() - 1 || col == 0) return false;
        return this.at(row+1, col-1).equals(HIT_MARKER);
    }
    public Boolean down_right(Integer row, Integer col) {
        if (row == this.grid.size() - 1 || col == this.grid.get(row).size() - 1) return false;
        return this.at(row+1, col+1).equals(HIT_MARKER);
    }
}


public class Day4 {

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

        Grid grid = new Grid(rawInputs);

        Integer output = 0;
        for (Integer row = 0; row < grid.grid.size(); row++) {
            for (Integer col = 0; col < grid.grid.get(row).size(); col++) {
                if (grid.at(row,  col).equals(grid.HIT_MARKER)) {
                    Integer surrondingHits = grid.surronding(row, col);
                    if (debug) System.out.println("  Surronding hits: " + surrondingHits);
                    if (surrondingHits < 4) {
                        output++;
                    }
                }
            }
        }

        System.out.println("Final Output: " + output);
    }
}
