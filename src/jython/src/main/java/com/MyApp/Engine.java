package com.MyApp;

// I used this lib because never worked with it before
import picocli.CommandLine;


class Engine implements Runnable {

    private static final String INPUT_DESCRIPTION = "The input TXT file containing the data, separated by newline.";
    private static final String OUTPUT_DESCRIPTION = "The operation to write the (deduplicated or modified) output to";

    @CommandLine.Option(
            names = {"-input"},
            description = INPUT_DESCRIPTION,
            required = true
    )
    private String input;

    @CommandLine.Option(
            names = {"-output"},
            description = OUTPUT_DESCRIPTION,
            required = true
    )
    private String output;

    public void run() {
        try {
            // process file using Jython,
            // file is opened and processed in Python
            deduplicate();
            // there can be many other exceptions thrown/raised by Jython interpreter,
            // but Exception handles them all
        } catch (Exception e) {
            // I understand that maybe its not cool to print to console directly,
            // in PROD-ready I would use some Logging, for example
            System.out.println(String.format("Something went wrong: %s", e));
            System.exit(1);
        }
    }

    private void deduplicate() {
        // idea behind this was to hide ugly syntax of Python manipulation
        int status_code = Python.deduplicate(this.input, this.output);
        System.exit(status_code);
    }
}
