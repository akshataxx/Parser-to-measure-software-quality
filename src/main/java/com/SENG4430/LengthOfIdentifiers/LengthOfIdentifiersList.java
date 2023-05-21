package com.SENG4430.LengthOfIdentifiers;

import com.SENG4430.MetricsList;
import org.apache.commons.cli.*;
import spoon.Launcher;

import java.util.List;
import java.util.Map;

public class LengthOfIdentifiersList extends MetricsList {
    private final LengthOfIdentifiersChk lengthOfIdentifiersChk;

    // Constructor
    public LengthOfIdentifiersList(String[] args) {
        Options options = new Options();
        options.addOption("cutoff", true, "Noteworthy identifiers cutoff value to display");

        CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parseCommandLine(args, parser, options);

        final int cutOffLimit = cutOffValue(cmd);

        lengthOfIdentifiersChk = new LengthOfIdentifiersChk(cutOffLimit);
    }

    @Override
    public void execute(Launcher launcher) {
        // Execute the length of identifiers checks using the provided Launcher
        lengthOfIdentifiersChk.check(launcher);
    }


    @Override
    public String toJson() {
        // Initialize the JSON string with the Length of Identifiers root object
        String json = "Length of Identifiers: ";
        // Add the Class average scores object to the JSON string
        json += "\n\t Class average scores: ";
        for (Map.Entry<String, Double> entry : lengthOfIdentifiersChk.getAverageLengthOfIdentifiers().entrySet()) {
            json += "\n\t\t Class Name: " + entry.getKey() + "\n\t\t Value: " + entry.getValue();
        }
        json += "\n";

        // Add the Noteworthy identifiers object to the JSON string
        json += "\n\t Length of identifiers less than or equal to the cutoff: ";
        for (Map.Entry<String, List<String>> entry
                : lengthOfIdentifiersChk.getLengthOfIdentifiersLessThanOrEqualToCutoff().entrySet()) {
            json += "\n\t\t Cut off Limit: " + lengthOfIdentifiersChk.getCutOffLimit()
                    + "\n\t\t Class Name: " + entry.getKey() + "\n\t\t Value(s): " + entry.getValue();
        }
        json += "\n";
        // Return the complete JSON string
        return json;
    }

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

    private int cutOffValue(final CommandLine cmd) {
        final String cutOffValue = cmd.getOptionValue("cutoff");
        int cutOffLimit = 4;
        if (cutOffValue != null) {
            try {
                cutOffLimit = Integer.parseInt(cutOffValue);
            } catch (NumberFormatException e) {
                System.out.println("Length of identifier cutoff must be an integer value");
                System.exit(1);
            }
        }
        return cutOffLimit;
    }
}
