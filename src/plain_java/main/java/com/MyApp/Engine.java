package com.MyApp;

import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Engine implements Runnable {

    private static final String INPUT_DESCRIPTION = "The input TXT file containing the data, separated by newline.";
    private static final String OUTPUT_DESCRIPTION = "The operation to write the (deduplicated or modified) output to";
    private static final String OPERATION_DESCRIPTION = "What to do with the data in the file: checkduplicates or deduplicate";

    private static final String CHECK_DUPLICATES = "checkduplicates";
    private static final String DEDUPLICATE = "deduplicate";
    // available operations
    private static final String[] OPERATION_OPTIONS = {CHECK_DUPLICATES, DEDUPLICATE};

    @CommandLine.Option(
            names = {"-input"},
            description = INPUT_DESCRIPTION,
            required = true
    )
    private String input;

    @CommandLine.Option(
            names = {"-operation"},
            description = OPERATION_DESCRIPTION,
            required = true
    )
    private String operation;

    @CommandLine.Option(
            names = {"-output"},
            description = OUTPUT_DESCRIPTION
    )
    private String output;

    public void run() {
        try (BufferedReader reader = openFile(this.input)) {
            // if operation is not available -> exit
            if (Arrays.stream(OPERATION_OPTIONS).noneMatch(op -> op.equals(this.operation))) {
                throw new InvalidOperationException("Invalid operation.");
            }
            processFile(reader, this.operation);

        } catch (FileNotFoundException |
                InvalidOperationException |
                InvalidFileExtensionException e) {

            // I would definitely go for logging
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            System.exit(1);
        }
    }

    private BufferedReader openFile(String pathToTxtFile) throws FileNotFoundException, InvalidFileExtensionException {
        String extension = FilenameUtils.getExtension(pathToTxtFile);
        // not allow to use some other extension for input,
        // yes, it says nothing, because text file with terms probably can be without extension,
        // but I expect to see input as TXT with extension only
        if (!extension.equals("txt")) {
            throw new InvalidFileExtensionException("Invalid file extension.");
        }
        File file = new File(pathToTxtFile);
        return new BufferedReader(new FileReader(file));
    }

    private void processFile(BufferedReader reader, String operation) throws IOException {
        switch (operation) {
            case CHECK_DUPLICATES:
                if (checkDuplicates(reader)) {
                    System.out.println("File has duplicates.");
                    System.exit(42);
                }
                break;
            case DEDUPLICATE:
                deduplicate(reader);
                System.exit(0);
                break;
        }
    }

    private Boolean checkDuplicates(BufferedReader reader) throws IOException {
        String line;
        Boolean hasDuplicates = false;

        Set<String> terms = new HashSet<>();

        while ((line = reader.readLine()) != null) {
            if (terms.contains(line)) {
                hasDuplicates = true;
                // if at least one duplication -> break
                break;
            } else {
                terms.add(line);
            }
        }

        return hasDuplicates;
    }

    private void deduplicate(BufferedReader reader) throws IOException {
        String line;
        Set<String> terms = new HashSet<>();
        OutputStreamWriter stream;

        // if no output specified -> write just to STDOUT
        if (this.output == null) {
            stream = new OutputStreamWriter(System.out);
        } else {
            File file = new File(this.output);
            stream = new FileWriter(file);
        }
        try (BufferedWriter writer = new BufferedWriter(stream)) {
            while ((line = reader.readLine()) != null) {
                if (!terms.contains(line)) {
                    terms.add(line);
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }
}
