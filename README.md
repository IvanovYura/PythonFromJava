# Combining Java and Python

This one uses Java and separated process to run Python script.
The output of the ran script is in STDOUT.

## Dependencies

1. Java 1.8
2. Maven

See the `pom.xml` for details.

## HOW TO

Here is used Maven `assembly-plugin` to build JAR will all needed dependencies.
JAR then can be run as standalone application as `java -jar <PATH_TO_ASSEMBLED_JAR>`.

To install Maven:
- MacOS
```
brew install maven
```
- Debian Linux
```
apt-get update && apt-get install maven
```

THEN:

1. run the command `mvn clean compile assembly:single`
2. run the command `java -jar MyApp-1.0-jar-with-dependencies.jar -input <path_to_terms_txt_file> -output <path_to_output_deduplicated_file>
`

NOTE: a test `terms.txt` file you can find in `resources` directory