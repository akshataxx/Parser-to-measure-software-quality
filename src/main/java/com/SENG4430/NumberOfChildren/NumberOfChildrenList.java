package com.SENG4430.NumberOfChildren;

import java.util.Map;

import com.SENG4430.MetricsList;

import spoon.Launcher;

public class NumberOfChildrenList extends MetricsList {
    private final NumberOfChildrenChk numberOfChildrenChk; // Create instance of NumberOfChildrenChk class

    public NumberOfChildrenList(String[] args) { // Constructor to initialize a NumberOfChildrenChk object
        numberOfChildrenChk = new NumberOfChildrenChk();
    }

    /**
     * Runs the analysis
     * 
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) {
        numberOfChildrenChk.check(launcher);
    }

    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\n");
        // Add the Number Of Children object to the JSON string
        jsonBuilder.append("\"Number of Children Per Class\": {\n");
        for (Map.Entry<String, Integer> entry : numberOfChildrenChk.getNumberOfChildrenChk().entrySet()) {
            jsonBuilder.append("\t\t\"" + entry.getKey() + "\": " + entry.getValue());
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("\t\t}\n");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
