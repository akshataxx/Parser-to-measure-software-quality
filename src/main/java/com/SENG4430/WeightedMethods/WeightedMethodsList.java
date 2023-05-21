package com.SENG4430.WeightedMethods;
import com.SENG4430.MetricsList;
import spoon.Launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeightedMethodsList extends MetricsList {

    private final WeightedMethodsChk weightedMethodsChk;
    public WeightedMethodsList(String[] args) {
        weightedMethodsChk = new WeightedMethodsChk();
    }


    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) {
        weightedMethodsChk.WeightedMethodsChk(launcher);
    }

    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("\t\t},\n");
        jsonBuilder.append("\n");
        // Add the Weighted Method Count object to the JSON string
        jsonBuilder.append("Weighted Methods Per Class\": {\n");
        for (Map.Entry<String, Double> entry : weightedMethodsChk.getWeightedMethods()) {
            jsonBuilder.append("\t\t\"" + entry.getKey() + "\": " + entry.getValue());
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("\t\t}\n");

        final double THRESHOLD = 10.0; // Set the threshold value

        if (weightedMethodsChk.isThresholdExceeded(THRESHOLD)) {
            jsonBuilder.append("\n");
            jsonBuilder.append("\u001B[33m"); // Yellow color escape code
            jsonBuilder.append("\t\tWarning: Weighted methods threshold exceeded for the following classes. Consider Code Refactoring:\n");
            jsonBuilder.append("\u001B[0m"); // Reset color escape code
            for (Map.Entry<String, Double> entry : weightedMethodsChk.getWeightedMethods()) {
                if (entry.getValue() > THRESHOLD) {
                    jsonBuilder.append("\t\t- " + entry.getKey() + "\n");
                }
            }
        }
        return jsonBuilder.toString();
    }
}
