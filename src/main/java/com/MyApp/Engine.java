package com.MyApp;

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
        int status_code = 0;

        try {
            status_code = deduplicate();
        } catch (Exception e) {
            // to handle something we do not expect at all
            System.out.println(String.format("Something went wrong: %s", e));
            status_code = 42;
        }

        System.exit(status_code);
    }

    private int deduplicate() {
        return Python.deduplicate(this.input, this.output);
    }
}
