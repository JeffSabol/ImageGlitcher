#!/bin/bash

SRC_DIR="com/jeffsabol/databender"
OUT_DIR="out"
MANIFEST_FILE="MANIFEST.MF"
JAR_FILE="DataBender.jar"

# Create output directory if it doesn't exist
mkdir -p $OUT_DIR

# Compile Java files
echo "Compiling Java files..."
javac -d $OUT_DIR $SRC_DIR/*.java $SRC_DIR/ui/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# Create the manifest file
echo "Creating manifest file..."
echo "Manifest-Version: 1.0" > $MANIFEST_FILE
echo "Main-Class: com.jeffsabol.databender.Main" >> $MANIFEST_FILE

# Package into JAR file
echo "Creating JAR file..."
jar cfm $JAR_FILE $MANIFEST_FILE -C $OUT_DIR .

# Check if JAR creation was successful
if [ $? -ne 0 ]; then
    echo "JAR creation failed."
    exit 1
fi

# Clean up
echo "Cleaning up..."
rm $MANIFEST_FILE

echo "Build successful. Executable JAR created: $JAR_FILE"

# Provide execution instruction
echo "You can run the JAR file with: java -jar $JAR_FILE"
