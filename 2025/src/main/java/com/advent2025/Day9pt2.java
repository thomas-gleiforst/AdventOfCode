package com.advent2025;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9pt2 {
    protected static enum Mode {
        OUTPUT,
        ERROR,
        DEBUG,
        INFO,
        VERBOSE,
    }

    public static Integer debug = Mode.OUTPUT.ordinal();
    public static Boolean enableCache = true;
    public static Boolean enableRandomization = true;

    protected static class Line {
        Integer start_x;
        Integer end_x;
        Integer start_y;
        Integer end_y;
        String direction;

        public Line(Integer x_1, Integer y_1, Integer x_2, Integer y_2, String input_direction) {
            start_x = Math.min(x_1, x_2);
            end_x = Math.max(x_1, x_2);
            start_y = Math.min(y_1, y_2);
            end_y = Math.max(y_1, y_2);
            direction = input_direction;
        }

        @Override
        public String toString() {
            if (Objects.equals(start_x, end_x)) {
                return "Line x=" + start_x + " y " + start_y + "-" + end_y + " - " + direction;
            } else {
                return "Line y=" + start_y + " x " + start_x + "-" + end_x + " - " + direction;
            }
        }
    }

    protected static class Area {
        Pos A;
        Pos B;
        Long area;

        public Area (Pos a, Pos b, Long size) {
            A = a;
            B = b;
            area = size;
        }

        @Override
        public String toString() {
            return "Area (" + A + " - " + B + " | " + area + ")";
        }
    }

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

        public Area area(Pos b) {
            return new Area(this, b, (long) (Math.abs(this.x-b.x) + 1) * (Math.abs(this.y-b.y) + 1));
        }

        public Boolean isInside(List<Line> hor, List<Line> vert, List<Line> all) {
            if (Day9pt2.debug >= Mode.VERBOSE.ordinal()) {
                for (Line each : all) {
                    System.out.println(this + " " + each);
                    System.out.println((Objects.equals(each.start_x, this.x) && Objects.equals(each.end_x, this.x)) && each.start_y <= this.y && this.y <= each.end_y);
                    System.out.println((Objects.equals(each.start_y, this.y) && Objects.equals(each.end_y, this.y)) && each.start_x <= this.x && this.x <= each.end_x);
                    System.out.println(each.start_y < this.y && each.start_x <= this.x && this.x < each.end_x);
                    System.out.println(each.start_x < this.x  && each.start_y <= this.y && this.y < each.end_y);
                    System.out.println();
                }
            }
            Optional<Line> touching = all.stream().filter(perim -> (
                ((Objects.equals(perim.start_x, this.x) && Objects.equals(perim.end_x, this.x)) && perim.start_y <= this.y && this.y <= perim.end_y)) ||
                ((Objects.equals(perim.start_y, this.y) && Objects.equals(perim.end_y, this.y)) && perim.start_x <= this.x && this.x <= perim.end_x)
            ).findAny();

            if (touching.isPresent()) return true;

            Integer leftCount = vert.stream().filter(perim -> perim.start_y < this.y && perim.start_x <= this.x && this.x < perim.end_x).toList().size();
            Boolean outHorizontal = leftCount % 2 == 0;
            if (!outHorizontal) return true;

            Integer aboveCount = hor.stream().filter(perim -> perim.start_x < this.x  && perim.start_y <= this.y && this.y < perim.end_y).toList().size();
            Boolean outVertical = aboveCount % 2 == 0;
            if (!outVertical) return true;
            
            if (Day9pt2.debug >= Mode.VERBOSE.ordinal()) {
                System.out.println(this + " is on the line");
                System.out.println(vert);
                System.out.println(this + " is " + (!outHorizontal ? "in" : "out") + " horizontally (" + leftCount + ")");
                System.out.println();
                System.out.println(hor);
                System.out.println(this + " is " + (!outVertical ? "in" : "out") + " vertically (" + aboveCount + ")");
                System.out.println();
            }

            return !outHorizontal && !outVertical;
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
        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("Loaded input (" + rawInputs.size() + " lines)");

        List<Pos> allPos = new ArrayList<>();
        for (String input : rawInputs) {
            List<Integer> cords = List.of(input.split(",")).stream().map(Integer::parseInt).toList();
            allPos.add(new Pos(cords.get(0), cords.get(1)));
        }

        if (Day9pt2.debug >= Mode.VERBOSE.ordinal()) System.out.println(allPos);

        List<Line> perimiter = new ArrayList<>();
        List<Line> perimiter_vertical = new ArrayList<>();
        List<Line> perimiter_horizontal = new ArrayList<>();

        Integer maxLen = allPos.size()-1;
        for (int i = 0; i < allPos.size(); i++) {
            Line newLine;
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println();
            Pos a = allPos.get(i % (maxLen+1));
            Pos b = allPos.get((i+1) % (maxLen+1));
            Pos c = allPos.get((i+2) % (maxLen+1));
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println(a + " " + b + " " + c);
            String direction;
            if (Objects.equals(a.x, b.x)) {
                if (c.x > b.x) {
                    direction = "R";
                } else  {
                    direction = "L";
                } 
                newLine = new Line(a.x, a.y, b.x, b.y, direction);
                perimiter_vertical.add(newLine);
                if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Adding vertical line" + newLine + " from " + a + " to " + b);
            } else {
                 if (c.y > b.y) {
                    direction = "D";
                } else {
                    direction = "U";
                }
                newLine = new Line(a.x, a.y, b.x, b.y, direction);
                perimiter_horizontal.add(newLine);
                if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Adding horizontal line" + newLine + " from " + a + " to " + b);
            }

            perimiter.add(newLine);
        }

        perimiter_vertical.sort((a, b) -> a.start_y < b.start_y ? 1 : a.start_y > b.start_y ? -1 : 0);
        perimiter_horizontal.sort((a, b) -> a.end_x < b.end_x ? 1 : a.end_x > b.end_x ? -1 : 0);

        if (Day9pt2.debug >= Mode.INFO.ordinal()) {
            System.out.println("all perimiters");
            System.out.println(perimiter);
        }
        if (Day9pt2.debug >= Mode.VERBOSE.ordinal()) {
            System.out.println("vertical perimiters");
            System.out.println(perimiter_vertical);
            System.out.println("horizontal perimiters");
            System.out.println(perimiter_horizontal);
        }

        List<Area> areas = new ArrayList<>();

        for (int i = 0; i < allPos.size() - 1; i++) {
            Pos a = allPos.get(i);
            for (int j = i + 1; j < allPos.size(); j++) {
                Pos b = allPos.get(j);
                areas.add(a.area(b));
            }
        }

        areas.sort((a, b) -> a.area < b.area ? 1 : a.area > b.area ? -1 : 0);


        HashMap<String, Boolean> isIn = new HashMap<>();
        for (int i = 0; i < areas.size(); i++) {
            System.err.print("\r" + (i+1) + "/" + (areas.size()+1) + " (" + ((i+1)/(areas.size()+1)*100) + "%)");
            Area id = areas.get(i);
            Line top = new Line(id.A.x, id.A.y, id.B.x, id.A.y, "");
            Line right = new Line(id.B.x, id.A.y, id.B.x, id.B.y, "");
            Line bottom = new Line(id.B.x, id.B.y, id.A.x, id.B.y, "");
            Line left = new Line(id.A.x, id.B.y, id.A.x, id.A.y, "");

            Boolean earlyBreak = false;
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 1 of " + top + " " + top.start_x + "-" + top.end_x);
            
            List<Integer> topRange = IntStream.range(top.start_x, top.end_x).boxed().collect(Collectors.toList());
            List<Integer> rightRange = IntStream.range(right.start_y, right.end_y).boxed().collect(Collectors.toList());
            List<Integer> bottomRange = IntStream.range(bottom.start_x, bottom.end_x).boxed().collect(Collectors.toList());
            List<Integer> leftRange = IntStream.range(left.start_y, left.end_y).boxed().collect(Collectors.toList());

            if (enableRandomization) Collections.shuffle(topRange);
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 1 is " + topRange);
            
            for (int x : topRange) {
                Pos curr = new Pos(x, top.end_y);
                Boolean currIn = isIn.get(curr.toString());
                if (currIn != null && enableCache) {
                    if (!currIn) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 1, Checking: " + top + "Checked before: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;    
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 1, Checking: " + top + "Checked before: " + curr + ", Passing: " + id);
                    }
                } else {
                    Boolean isInside = curr.isInside(perimiter_vertical, perimiter_horizontal, perimiter);
                    isIn.put(curr.toString(), isInside);
                    if (!isInside) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 1, Checking: " + top + "Not in: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 1, Checking: " + top + "In: " + curr + ", Passing: " + id);
                    }
                }
            }
            if (earlyBreak) continue;
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 2 of " + right + " " + right.start_y + "-" + right.end_y);

            if (enableRandomization) Collections.shuffle(rightRange);
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 2 is " + rightRange);

            for (int y : rightRange) {
                Pos curr = new Pos(right.start_x, y);
                Boolean currIn = isIn.get(curr.toString());
                if (currIn != null && enableCache) {
                    if (!currIn) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 2, Checking: " + right + "Checked before: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;    
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 2, Checking: " + right + "Checked before: " + curr + ", Passing: " + id);
                    }
                } else {
                    Boolean isInside = curr.isInside(perimiter_vertical, perimiter_horizontal, perimiter);
                    isIn.put(curr.toString(), isInside);
                    if (!isInside) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 2, Checking: " + right + "Not in: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 2, Checking: " + right + "In: " + curr + ", Passing: " + id);
                    }
                }
            }
            if (earlyBreak) continue;
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 3 of " + bottom + " " + bottom.start_x + "-" + bottom.end_x);

            if (enableRandomization) Collections.shuffle(bottomRange);
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 3 is " + bottomRange);


            for (int x : bottomRange) {
                Pos curr = new Pos(x, bottom.end_y);
                Boolean currIn = isIn.get(curr.toString());
                if (currIn != null && enableCache) {
                    if (!currIn) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 3, Checking: " + bottom + "Checked before: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;    
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 3, Checking: " + bottom + "Checked before: " + curr + ", Passing: " + id);
                    }
                } else {
                    Boolean isInside = curr.isInside(perimiter_vertical, perimiter_horizontal, perimiter);
                    isIn.put(curr.toString(), isInside);
                    if (!isInside) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 3, Checking: " + bottom + "Not in: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 3, Checking: " + bottom + "In: " + curr + ", Passing: " + id);
                    }
                }
            }
            if (earlyBreak) continue;
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 4 of " + left + " " + left.start_y + "-" + left.end_y);

            if (enableRandomization) Collections.shuffle(leftRange);
            if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.err.println("Loop 4 is " + leftRange);

            for (int y : leftRange) {
                Pos curr = new Pos(left.start_x, y);
                Boolean currIn = isIn.get(curr.toString());
                if (currIn != null && enableCache) {
                    if (!currIn) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 4, Checking: " + left + "Checked before: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;    
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 4, Checking: " + left + "Checked before: " + curr + ", Passing: " + id);
                    }
                } else {
                    Boolean isInside = curr.isInside(perimiter_vertical, perimiter_horizontal, perimiter);
                    isIn.put(curr.toString(), isInside);
                    if (!isInside) {
                        if (Day9pt2.debug >= Mode.DEBUG.ordinal()) System.out.println("Loop 4, Checking: " + left + "Not in: " + curr + ", Rejecting: " + id);
                        earlyBreak = true;
                        break;
                    } else {
                        if (Day9pt2.debug >= Mode.INFO.ordinal()) System.out.println("    Loop 4, Checking: " + left + "In: " + curr + ", Passing: " + id);
                    }
                }
            }
            if (earlyBreak) continue;
            System.out.println();
            System.out.println(id);
            break;
        }
    }
}

//takes 4.7 hrs
// 270/122761 (0%)Area (Pos (15658,84073) - Pos (83987,16276) | 4632637340) - too high
// 21843/122761 (0%) Area (Pos (97839,50187) - Pos (23155,90387) | 3002411685) - too high
// Area (Pos (6271,68563) - Pos (94582,50174) | 1624057680) - correct