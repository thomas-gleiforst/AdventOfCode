#!/usr/bin/env bash
# Build helper: prefer Maven if available, otherwise compile with javac
set -e
ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
if command -v mvn >/dev/null 2>&1; then
  echo "Maven found, running mvn package"
  mvn -f "$ROOT_DIR" package
else
  echo "Maven not found, compiling with javac into $ROOT_DIR/out"
  SRC="$ROOT_DIR/src/main/java"
  OUT="$ROOT_DIR/out"
  mkdir -p "$OUT"
  javac -d "$OUT" $(find "$SRC" -name "*.java")
  echo "Built to $OUT"
fi
