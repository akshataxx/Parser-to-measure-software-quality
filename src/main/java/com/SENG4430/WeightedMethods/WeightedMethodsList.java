package com.SENG4430.WeightedMethods;
import com.SENG4430.MetricsList;
import spoon.Launcher;
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
        return jsonBuilder.toString();
    }
}
