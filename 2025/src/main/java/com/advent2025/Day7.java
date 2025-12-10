package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


class BeamField {
    protected class Pos {
        Integer x;
        Integer y;
        String id;

        public Pos(Integer input_x, Integer input_y) {
            x = input_x;
            y = input_y;
            id = x + "_" + y;
        }

        public void move_down() {
            this.x++;
            id = x + "_" + y;
        }
    }
    
    List<List<String>> field;
    List<Pos> activeBeams;
    List<Pos> divergers;
    Integer output;
    Integer splits;

    public BeamField(List<String> input) {
        this.output = 0;
        this.splits = 0;
        this.activeBeams = new ArrayList<>();
        this.divergers = new ArrayList<>();

        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            String row = input.get(i);
            List<String> charRow = Arrays.asList(row.split(""));
            
            for (int j = 0; j < charRow.size(); j++) {
                if (charRow.get(j).equals("S")) {
                    Pos starting = new Pos(i, j);
                    this.activeBeams.add(starting);
                }  else if (charRow.get(j).equals("^")) {
                    Pos diverger = new Pos(i, j);
                    this.divergers.add(diverger);
                }
            }

            rows.add(charRow);
        }
        this.field = rows;
    }

    public Boolean beam_exists(Pos beam) {
        return this.activeBeams.stream().parallel().filter(existing -> existing.id.equals(beam.id)).findAny().isPresent();
    }
    public Boolean diverger_exists(Pos beam) {
        return this.divergers.stream().parallel().filter(existing -> existing.id.equals(beam.id)).findAny().isPresent();
    }

    public Integer simulate() {
        System.out.println("Simulating");
        System.out.println("All beams " + this.activeBeams.stream().map(beam -> beam.id).toList());

        while (!this.activeBeams.isEmpty()) {
            Pos beam = this.activeBeams.removeFirst();

            System.out.println("Simulating beam " + beam.id);
            this.activeBeams.remove(beam);
            beam.move_down();

            if (diverger_exists(beam)) {
                splits++;
                System.out.println("  Diverger at " + beam.id);
                Pos left = new Pos(beam.x, beam.y-1);
                Pos left_up = new Pos(beam.x-1, beam.y-1);
                Pos right = new Pos(beam.x, beam.y+1);
                Pos right_up = new Pos(beam.x-1, beam.y+1);

                if (!beam_exists(left) && !beam_exists(left_up)) {
                    this.output++;
                    this.activeBeams.add(left);
                    System.out.println("    Formed beam " + output + " at " + left.id);
                }
                if (!beam_exists(right) && !beam_exists(right_up)) {
                    this.output++;
                    this.activeBeams.add(right);
                    System.out.println("    Formed beam " + output + " at " + right.id);
                }
            } else if (beam.x < this.field.size()) {
                this.activeBeams.add(beam);
            }

        }
        return this.splits;
    }
}

public class Day7 {

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

        BeamField field = new BeamField(rawInputs);
        Integer output = field.simulate();

        System.out.println("Final Output: " + output);
    }
}
