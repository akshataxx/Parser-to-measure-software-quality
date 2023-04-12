package com.SENG4430.WeightedMethods;

import com.SENG4430.MetricsList;
import spoon.Launcher;

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
        return weightedMethodsChk.getWeightedMethods().toString();
    }
}
