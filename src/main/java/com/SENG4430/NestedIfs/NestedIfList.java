package com.SENG4430.NestedIfs;

import com.SENG4430.MetricsList;
import org.apache.commons.cli.*;
import spoon.Launcher;


public class NestedIfList extends MetricsList {
    private final NestedIfsChk nestedIfsChk;

    // Constructor
    public NestedIfList(String[] args) {
        final Options options = new Options();
        options.addOption("depth", true, "");

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parseCommandLine(args, parser, options);

        // Parse the minimum depth from the command line arguments
        final int minDepth = parseMinDepth(cmd);

        // Create an instance of NestedIfsChk with the minimum depth
        nestedIfsChk = new NestedIfsChk(minDepth);
    }

    @Override
    public void execute(Launcher launcher) {
        // Execute the nested if checks using the provided Launcher
        nestedIfsChk.check(launcher);
    }

    @Override
    public String toJson() {
        // Convert the nested if scores to JSON format
        return nestedIfsChk.getNestedIfsScoredForClassJson();
    }

    // Parse the command line arguments
    private CommandLine parseCommandLine(String[] args, final CommandLineParser parser, final Options options) {
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return cmd;
    }

    // Parse the minimum depth value from the command line arguments
    private int parseMinDepth(final CommandLine cmd) {
        final String minDepthString = cmd.getOptionValue("depth");
        int minDepth = 3;
        if (minDepthString != null) {
            try {
                minDepth = Integer.parseInt(minDepthString);
            } catch (NumberFormatException e) {
                System.out.println("-depth flag value is not an integer");
                System.exit(1);
            }
        }
        return minDepth;
    }
}