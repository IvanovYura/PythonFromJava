package com.MyApp;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;


class Python {
    // its probably better to keep in properties file, as other settings
    private static final String PATH_TO_PYTHON_SCRIPT = "src/main/resources/functions.py";
    private static final String DEDUPLICATE_FUNCTION = "deduplicate";

    private static PythonInterpreter interpreter = new PythonInterpreter();

    static int deduplicate(String input, String output) {
        interpreter.execfile(PATH_TO_PYTHON_SCRIPT);
        // get function deduplicate(input: str, output: str) -> int
        PyObject someFunc = interpreter.get(DEDUPLICATE_FUNCTION);

        // call the function and get status code of operation from Python code
        // I also wanted to do something abstract to call any function with any arguments,
        // but did not want to over-engineer it
        PyObject status_code = someFunc.__call__(new PyString(input), new PyString(output));
        return ((PyInteger) status_code).getValue();
    }
}
