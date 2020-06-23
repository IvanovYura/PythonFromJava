package com.MyApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

class Python {
    // has to be in the properties file
    private static final String PATH_TO_PYTHON_SCRIPT = "src/main/resources/";

    static int deduplicate(String input, String output) {
        int status_code;
        String statement = prepareFunctionForCall("functions", "deduplicate", input, output);

        ProcessBuilder pythonProcess = preparePythonProcess(statement);
        Process process = null;

        try {
            process = pythonProcess.start();
            // to see the output in STDOUT from the child process
            printProcessOutput(process);

            // wait by main process until child is done
            // returns status_code of the process
            status_code = process.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
            status_code = 1;
        }

        return status_code;
    }

    private static ProcessBuilder preparePythonProcess(String statementToRun) {
        // I make an assumption that the python command is available via the PATH variable.
        ProcessBuilder processBuilder = new ProcessBuilder("python", "-c", statementToRun);
        // merge error stream to main process
        processBuilder.redirectErrorStream(true);

        // I need to python knows where to look my module
        Map<String, String> env = processBuilder.environment();
        String pythonPath = String.format("%s/%s", env.get("PWD"), PATH_TO_PYTHON_SCRIPT);
        env.put("PYTHONPATH", pythonPath);

        return processBuilder;
    }

    private static String prepareFunctionForCall(String module, String functionName, String input, String output) {
        return String.format(
                "from %s import %s; %s('%s', '%s')",
                module,
                functionName,
                functionName,
                input,
                output
        );
    }

    private static void printProcessOutput(Process process) throws IOException {
        BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stream.readLine()) != null) {
            System.out.println(s);
        }
    }
}
