# Combining Java and Python

This one uses Java and Jython to reuse Python scripts in Runtime.

## Dependencies

1. Java 1.8
2. Maven
3. Jython-Standalone

See the `pom.xml` for details.

## HOW TO

I was trying to keep and build all dependencies in one place.
Thats why I used Maven with `assembly-plugin`.

To install Maven:
- MacOS
```
brew install maven
```
- Debian Linux
```
apt-get update && apt-get install maven
```

1. run the command `mvn clean compile assembly:single`
2. run the command `java -jar MyApp-1.0-jar-with-dependencies.jar -input <path_to_terms_txt_file> -output <path_to_output_deduplicated_file>
`

NOTE: a test `terms.txt` file you can find in `resources` directory

## Comments

1. There is no unit-tests
2. I used Jython because IMHO its the easiest way to run Python in JVM
3. Its nice to add normal logging
4. Maybe more error handling like: check extension of input file,
check existence of file before invoking Python function
(because for me whole Python thing looks a bit slower than normal Python),
maybe different exceptions to handle (if I need something more custom, apart printing error to STDOUT)