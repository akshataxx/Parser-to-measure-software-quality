package com.SENG4430.FogIndex;

import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.lang.String;

/**
 * This class represents a list of metrics, which includes a Fog Index check.
 * It extends the MetricsList class.
 */
public class FogIndexList extends MetricsList {

    private final FogIndexChk fogIndexChk;

    /**
     * Constructor that initializes the FogIndexChk object.
     * @param args arguments passed to the constructor.
     */
    public FogIndexList(String[] args) {
        fogIndexChk = new FogIndexChk();
    }

    /**
     * Runs the Fog Index analysis on the given launcher.
     * @param launcher the launcher to run the analysis on.
     */
    @Override
    public void execute(Launcher launcher) {
         fogIndexChk.check(launcher);
    }

    /**
     * Converts the FogIndex to JSON format for reporting purposes.
     *  @return a JSON-formatted string representation of the Fog Index.
     */
    public String toJson() {
        // Extract the Fog Index value from the FogIndexChk object and format it
        String fogIndex = fogIndexChk.getFogIndex().toString();
        String[] fogIndexParts = fogIndex.split("testResults=");
        String formattedFogIndex = fogIndexParts[1].replaceAll("[\\[\\],{}]", "").trim();
        // Create a JSON-formatted string with a root object that has a FogIndex property and the formatted Fog Index value
        return String.format(" FogIndex: %s ", formattedFogIndex);
    }
}
