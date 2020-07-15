# The Basics: Finding duplicates in streams and comparing runtime

This project build an application which checks file for duplications or deduplicate the input and save the output.

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
2. run the command `java -jar MyApp-1.0-jar-with-dependencies.jar -input <path_to_terms_txt_file> -operation [checkduplicates|deduplicate] -output <path_to_output_deduplicated_file>
`

If -output is not specified, output will be printed into STDOUT

NOTE: a test `terms.txt` file you can find in `resources` directory