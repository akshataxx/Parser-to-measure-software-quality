package com.SENG4430.HalsteadComplexity;

import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.util.*;
public class HalsteadComplexityList extends MetricsList  {
    private final HalsteadComplexityChk halsteadComplexityChk; //Create instance of HalsteadComplexityChk class

    public HalsteadComplexityList(String[] args) {          // Constructor to initialize a HalsteadComplexityChk object
        halsteadComplexityChk = new HalsteadComplexityChk();
    }
    // Implementation of the execute method from the metricsList abstract class
    // Executes the check method on the HalsteadComplexityChk object with the provided Spoon Launcher
    @Override
    public void execute(Launcher launcher) {
        halsteadComplexityChk.check(launcher);
    }

    public String toJson() {

        // Initialize the JSON string with the Halstead Complexity root object
        String json = "Halstead Complexity: ";
        // Add the Halstead Numbers object to the JSON string
        json += "\n\t Halstead Numbers: ";
        for (Map.Entry<String, Integer> entry : halsteadComplexityChk.getHalsteadNumbers().entrySet()) {
            json += "\n\t\t" + entry.getKey() + ": " + entry.getValue() + ",";
        }
        json += "\n";

        // Add the Halstead Complexity Measures object to the JSON string
        json += "\n\t Halstead Complexity Measures: ";
        for (Map.Entry<String, Double> entry : halsteadComplexityChk.getHalsteadAttributes().entrySet()) {
            // Special formatting for the 'Time required to program' measure
            if(entry.getKey().equals("Time required to program T  ")){
                json += "\n\t\t"+entry.getKey()+": "+entry.getValue()+" seconds,";
            }
            else if(entry.getKey().equals("Delivered bugs B            ")){
                json += "\n\t\t"+entry.getKey()+": "+entry.getValue()+ " ";
            }
            else{
                json += "\n\t\t" + entry.getKey() + ": " + entry.getValue() + ",";
            }
        }
        json += "\n";
        // Return the complete JSON string
        return json;
    }
}
