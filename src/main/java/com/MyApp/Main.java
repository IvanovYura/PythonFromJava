package com.MyApp;

import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {
        CommandLine.run(new Engine(), args);
    }
}
