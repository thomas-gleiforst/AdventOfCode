package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class BeamField2 {

    protected class Pos {

        Integer x;
        Integer y;
        Long weight;
        String id;

        public Pos(Integer input_x, Integer input_y, Long input_weight) {
            x = input_x;
            y = input_y;
            id = x + "_" + y;
            weight = input_weight;
        }

        public void move_down() {
            this.x++;
            id = x + "_" + y;
        }
    }

    List<List<String>> field;
    List<Pos> activeBeams;
    List<Pos> divergers;

    public BeamField2(List<String> input) {
        this.activeBeams = new ArrayList<>();
        this.divergers = new ArrayList<>();

        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            String row = input.get(i);
            List<String> charRow = Arrays.asList(row.split(""));

            for (int j = 0; j < charRow.size(); j++) {
                if (charRow.get(j).equals("S")) {
                    Pos starting = new Pos(i, j, 1L);
                    this.activeBeams.add(starting);
                } else if (charRow.get(j).equals("^")) {
                    Pos diverger = new Pos(i, j, 0L);
                    this.divergers.add(diverger);
                }
            }

            rows.add(charRow);
        }
        this.field = rows;
    }

    public Optional<Pos> beam_exists(Pos beam) {
        return this.activeBeams.stream().parallel().filter(existing -> existing.id.equals(beam.id)).findAny();
    }

    public Optional<Pos> diverger_exists(Pos beam) {
        return this.divergers.stream().parallel().filter(existing -> existing.id.equals(beam.id)).findAny();
    }

    public Long simulate() {
        if (Day7pt2.debug) {
            System.out.println("Simulating");
        }
        if (Day7pt2.debug) {
            System.out.println("All beams " + this.activeBeams.stream().map(beam -> beam.id).toList());
        }

        Long reachedEnd = 0L;
        while (!this.activeBeams.isEmpty()) {
            Pos beam = this.activeBeams.removeFirst();

            if (Day7pt2.debug) {
                System.out.println("Simulating beam " + beam.id + " with weight " + beam.weight);
            }
            this.activeBeams.remove(beam);
            beam.move_down();
            Optional<Pos> beamBelow = beam_exists(beam);
            Optional<Pos> diverger = diverger_exists(beam);
            if (beamBelow.isPresent()) {
                beamBelow.get().weight += beam.weight;
            } else if (diverger.isPresent()) {
                diverger.get().weight = beam.weight;
                if (Day7pt2.debug) {
                    System.out.println("  Diverger at " + beam.id);
                }

                Pos left = new Pos(beam.x, beam.y - 1, beam.weight);
                Pos right = new Pos(beam.x, beam.y + 1, beam.weight);

                Optional<Pos> beamLeft = beam_exists(left);
                if (beamLeft.isPresent()) {
                    beamLeft.get().weight += beam.weight;
                } else {
                    this.activeBeams.add(left);
                }

                Optional<Pos> beamRight = beam_exists(right);
                if (beamRight.isPresent()) {
                    beamRight.get().weight += beam.weight;
                } else {
                    this.activeBeams.add(right);
                }
            } else if (beam.x < this.field.size()) {
                this.activeBeams.add(beam);
            }

            if (beam.x >= this.field.size()) {
                if (true) {
                    System.out.println("    Exiting beam " + beam.id + " - " + beam.weight);
                }
                reachedEnd += beam.weight;
            }
        }
        return reachedEnd;
    }
}

public class Day7pt2 {

    public static Boolean debug = false;

    public static void main(String[] args) throws Exception {
        Path inputPath = args.length > 0 ? Paths.get(args[0]) : Paths.get("2025/input/Day3.txt");

        if (!Files.exists(inputPath)) {
            if (Day7pt2.debug) {
                System.out.println("No input file found at: " + inputPath.toString());
            }
            if (Day7pt2.debug) {
                System.out.println("Put your puzzle input in 2025/input.txt or pass a path as first arg.");
            }
            return;
        }

        List<String> rawInputs = Files.readAllLines(inputPath);
        if (Day7pt2.debug) {
            System.out.println("Loaded input (" + rawInputs.size() + " lines)");
        }

        BeamField2 field = new BeamField2(rawInputs);
        Long output = field.simulate();

        Integer diverger = 0;
        for (List<String> row : field.field) {
            for (String c : row) {
                if (!c.equals("^")) {
                    System.out.print(c+"\t");
                } else {
                    System.out.print(field.divergers.get(diverger).weight+"\t");
                    diverger++;
                }
            }
            System.out.println();
        }

        System.out.println("Final Output: " + output);
    }
}

// 2139895809 too low

// real answer, 3223365367809 ??