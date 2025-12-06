#!/usr/bin/env bash
# Compile and run a specified Day class using javac/java. Usage:
#   ./run.sh <day> [input|sample]
# Examples:
#   ./run.sh 1 input    -> runs com.advent2025.Day1 with input/Day1.txt
#   ./run.sh 3 sample   -> runs com.advent2025.Day3 with input_sample/Day3.txt

set -e
ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC="$ROOT_DIR/src/main/java"
OUT="$ROOT_DIR/out"

DAY="${1:-1}"
MODE="${2:-input}"

if [ "$MODE" = "sample" ]; then
	INPUT="$ROOT_DIR/input_sample/Day${DAY}.txt"
else
	INPUT="$ROOT_DIR/input/Day${DAY}.txt"
fi

# fallback to single input.txt at project root
if [ ! -f "$INPUT" ] && [ -f "$ROOT_DIR/input.txt" ]; then
	INPUT="$ROOT_DIR/input.txt"
fi

mkdir -p "$OUT"

echo "Compiling sources..."
javac -d "$OUT" $(find "$SRC" -name "*.java")

PART="${3:-1}"
if [ "$PART" = "2" ] || [ "$PART" = "pt2" ] || [ "$PART" = "part2" ]; then
	CLASS="com.advent2025.Day${DAY}pt2"
else
	CLASS="com.advent2025.Day${DAY}"
fi
echo "Running $CLASS (mode=$MODE, part=$PART) -- input=$INPUT"
if [ -f "$INPUT" ]; then
	java -cp "$OUT" "$CLASS" "$INPUT"
else
	java -cp "$OUT" "$CLASS"
fi
