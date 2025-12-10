Advent of Code 2025 - Java templates

Bare-minimum setup to solve AoC 2025 in Java.

Quick start

1) Run a specific day (no Maven required):
   - From repository root (bash):
     ./2025/run.sh <day> [input|sample]
   - Examples:
     ./2025/run.sh 1 input      # run Day1 with `input/Day1.txt`
     ./2025/run.sh 3 sample     # run Day3 with `input_sample/Day3.txt`

2) If you have Maven installed you can build and run via:
   mvn -f 2025/ package
   java -cp 2025/target/advent-of-code-2025-1.0-SNAPSHOT.jar com.advent2025.Day1

Files created
- `pom.xml` - minimal Maven config with exec plugin
- `src/main/java/com/advent2025/Day1.java` - template main
- `2025/input/Day1.txt` - place per-day puzzle inputs here
- `2025/input_sample/Day1.txt` - place per-day sample inputs here
- `run.sh` / `build.sh` - helper scripts (bash)

Notes
- The template reads `2025/input.txt` by default (or accepts a file path as first arg).
- Feel free to create new classes under `com.advent2025` for each day (Day2, Day3...).

export JAVA_HOME="/c/oracleJdk-25"
export PATH="$JAVA_HOME/bin:$PATH"