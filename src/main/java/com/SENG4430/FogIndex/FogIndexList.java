/*
 * Developer: Akshata Dhuraji
 * Program Name: FogIndexList
 * Description: This program creates an instance of FogIndexChk to compute the FogIndex metrics of given file
 *  passed as input by TestApplication and returns the FogIndex values in a printable format
 */
package com.SENG4430.FogIndex;

import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.lang.String;
import java.util.*;

/**
 * This class represents a list of metrics, which includes a Fog Index check.
 * It extends the MetricsList class.
 */
public class FogIndexList extends MetricsList {

    private final FogIndexChk fogIndexChk;

    /**
     * Constructor that initializes the FogIndexChk object.
     * @param args arguments passed to the constructor
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
    @Override
    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("\n");

        LinkedList<Map.Entry<String, TreeMap<String, Double>>> fogIndexList = fogIndexChk.getFogIndex();

        if (!fogIndexList.isEmpty()) {
            jsonBuilder.append("Fog Index: \n");
            String className = "";
            for (Map.Entry<String, TreeMap<String, Double>> entry : fogIndexList) {
                className = entry.getKey();
                jsonBuilder.append("Class - \"").append(className).append("\": \n");
                int select = 0;
                for (Map.Entry<String, Double> methodEntry : entry.getValue().entrySet()) {
                    String methodName = methodEntry.getKey();
                    Double value = methodEntry.getValue();
                    if (select == 0) {
                        jsonBuilder.append("\t\t FogIndex: (MethodName:Value) \n");
                        select++;
                    } else {
                        jsonBuilder.append("\n\t\t FogIndex: (MethodName:Value) \n");
                    }
                    jsonBuilder.append("\t\t\t").append(methodName).append(":").append(value).append("\t");
                    jsonBuilder.append("\n");
                }
                jsonBuilder.append("\n");
            }
        } else {
            jsonBuilder.append("FogIndex\": \"No classes to traverse\"\n");
        }
        return jsonBuilder.toString();
    }
}
