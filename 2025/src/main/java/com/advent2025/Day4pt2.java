package com.advent2025;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Grid2 {
    String HIT_MARKER = "@";
    String MISS_MARKER = ".";
    List<List<String>> grid;

    public Grid2(List<String> rawInputs) {
        List<List<String>> grid = new ArrayList<>();
        for (String line : rawInputs) {
            List<String> row = new ArrayList<>(Arrays.asList(line.split("")));
            grid.add(row);
        }

        this.grid = grid;
    }

    public void printGrid() {
        for (List<String> row : this.grid) {
            String rowStr = String.join("", row);
            System.out.println(rowStr);
        }
    }


    public String at(Integer row, Integer col) {
        return this.grid.get(row).get(col);
    }

    public String set(Integer row, Integer col) {
        List<String> rowData = this.grid.get(row);
        rowData.set(col, MISS_MARKER);
        this.grid.set(row, rowData);
        return this.at(row, col);
    }

    public Integer surronding(Integer row, Integer col) {
        Integer total = 0;
        if (up(row, col)) total++;
        if (down(row, col)) total++;
        if (left(row, col)) total++;
        if (right(row, col)) total++;
        if (up_left(row, col)) total++;
        if (up_right(row, col)) total++;
        if (down_left(row, col)) total++;
        if (down_right(row, col)) total++;
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


public class Day4pt2 {

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

        Grid2 grid = new Grid2(rawInputs);

        Integer output = 0;
        Integer lastOutput = -1;
        while (!lastOutput.equals(output)) {
            if (debug) System.out.println("New Iteration:");
            if (debug) grid.printGrid();
            lastOutput = output;
            for (Integer row = 0; row < grid.grid.size(); row++) {
                for (Integer col = 0; col < grid.grid.get(row).size(); col++) {
                    if (grid.at(row,  col).equals(grid.HIT_MARKER)) {
                        Integer surrondingHits = grid.surronding(row, col);
                        if (debug) System.out.println("  Surronding hits: " + surrondingHits);
                        if (surrondingHits < 4) {
                            String result = grid.set(row, col);
                            if (debug) System.out.println("  Removing " + row + " " + col + " " + result);
                            output++;
                        }
                    }
                }
            }
        }
        System.out.println("Final Output: " + output);
    }
}
