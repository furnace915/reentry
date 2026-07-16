#!/usr/bin/env bash
set -euo pipefail

# Lightweight bootstrap script for environments where the official Gradle wrapper jar
# is not already present. If Gradle is installed, use it. Otherwise, download the
# distribution declared in gradle/wrapper/gradle-wrapper.properties and run it.

if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
fi

PROP_FILE="gradle/wrapper/gradle-wrapper.properties"
DIST_URL=$(grep '^distributionUrl=' "$PROP_FILE" | cut -d= -f2- | sed 's#\\:#:#g')
GRADLE_USER_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"
DIST_DIR="$GRADLE_USER_HOME/reentry-bootstrap"
ZIP_FILE="$DIST_DIR/gradle-bin.zip"
mkdir -p "$DIST_DIR"

if [ ! -d "$DIST_DIR/gradle" ]; then
  echo "Gradle is not installed. Downloading distribution from $DIST_URL"
  if command -v curl >/dev/null 2>&1; then
    curl -L "$DIST_URL" -o "$ZIP_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$ZIP_FILE" "$DIST_URL"
  else
    echo "Neither curl nor wget is available. Install Gradle or use an IDE with bundled Gradle."
    exit 1
  fi
  unzip -q "$ZIP_FILE" -d "$DIST_DIR"
  FOUND=$(find "$DIST_DIR" -maxdepth 1 -type d -name 'gradle-*' | head -n 1)
  mv "$FOUND" "$DIST_DIR/gradle"
fi

exec "$DIST_DIR/gradle/bin/gradle" "$@"
