package com.SENG4430.DepthInheritanceTree;

import java.util.Map;

import com.SENG4430.MetricsList;

import spoon.Launcher;

public class DepthInheritanceTreeList extends MetricsList {
    private final DepthInheritanceTreeChk depthInheritanceTreeChk; // Create instance of DepthOfInheritanceTree class

    public DepthInheritanceTreeList(String[] args) { // Constructor to initialize a DepthOfInheritanceTree object
        depthInheritanceTreeChk = new DepthInheritanceTreeChk();
    }

    /**
     * Runs the analysis
     * 
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) {
        depthInheritanceTreeChk.check(launcher);
    }

    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\n");
        // Add the Depth Inheritance Tree object to the JSON string
        jsonBuilder.append("\"Depth Inheritance Per Class\": {\n");
        for (Map.Entry<String, Integer> entry : depthInheritanceTreeChk.getDepthInheritanceTreeCheck()) {
            jsonBuilder.append("\t\t\"" + entry.getKey() + "\": " + entry.getValue());
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("\t\t}\n");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
