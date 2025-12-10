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

public class Day8 {
    public static Boolean debug = true;

    protected static Long calcDistance(Pos a, Pos b) {
        Long value = (long) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));
        return value;
    }
 
    protected static class Pos {
        Integer x;
        Integer y;
        Integer z;
        Network network;

        public Pos(Integer input_x, Integer input_y, Integer input_z) {
            x = input_x;
            y = input_y;
            z = input_z;
            network = new Network();
            network.network.add(this);
        }

        @Override
        public String toString() {
            // Return a string representing the object's state
            return "Pos (" + x + "," + y + "," + z + ") - " + network.network.size();
        }
    }

    protected static class Combo {
        Pos a;
        Pos b;
        Long distance;

        public Combo(Pos input_a, Pos input_b) {
            a = input_a;
            b = input_b;
            distance = calcDistance(a, b);
        }
        
        @Override
        public String toString() {
            // Return a string representing the object's state
            return a + " " + b + " - " + distance;
        }
    }

    protected static class Network {
        Set<Pos> network;
        public Network() {
            network = new HashSet<>();
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
        if (Day8.debug) System.out.println("Loaded input (" + rawInputs.size() + " lines)");

        List<Pos> allJunctions = new ArrayList<>();
        for (String input : rawInputs) {
            List<Integer> cords = List.of(input.split(",")).stream().map(Integer::parseInt).toList();
            allJunctions.add(new Pos(cords.get(0), cords.get(1), cords.get(2)));
        }

        if (Day8.debug) System.out.println(allJunctions);

        List<Combo> distances = new ArrayList<>();

        for (int i = 0; i < allJunctions.size() - 1; i++) {
            // if (Day8.debug) System.out.println("i" + i);
            Pos a = allJunctions.get(i);
            for (int j = i + 1; j < allJunctions.size(); j++) {
                // if (Day8.debug) System.out.println("j" + j);
                Pos b = allJunctions.get(j);
                distances.add(new Combo(a, b));
            }
        }
        distances.sort((a, b) -> a.distance < b.distance ? -1 : a.distance > b.distance ? 1 : 0);
        
        // Map<Pos, Network> circuits = new HashMap<>();
        
        for (int id = 0; id < 1000 && id < distances.size(); id++) {
            Combo combo = distances.get(id);
            if (combo.a.network != null && combo.b.network != null) {
                if (combo.a.network.network.size() > combo.b.network.network.size()) {
                    combo.a.network.network.addAll(combo.b.network.network);
                    combo.b.network.network.forEach(box -> box.network = combo.a.network);
                } else  {
                    combo.b.network.network.addAll(combo.a.network.network);
                    combo.a.network.network.forEach(box -> box.network = combo.b.network);
                }
            }
            if (Day8.debug) System.err.println(combo.a.network.network.size());
        }

        allJunctions.sort((a, b) -> a.network.network.size() < b.network.network.size() ? 1 : a.network.network.size() > b.network.network.size() ? -1 : 0);

        if (Day8.debug) System.err.println(allJunctions);


        Network network1 = allJunctions.get(0).network;
        Network network2 = allJunctions.get(network1.network.size()).network;
        Network network3 = allJunctions.get(network1.network.size() + network2.network.size()).network;

        System.out.println("Network 1: " + network1.network.size());
        System.out.println("Network 2: " + network2.network.size());
        System.out.println("Network 3: " + network3.network.size());

        Integer output = network1.network.size() * network2.network.size() * network3.network.size();

        System.out.println("Final Output: " + output);
    }
}
