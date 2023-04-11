package com.SENG4430.FogIndex;

import com.SENG4430.MetricsList;
import spoon.Launcher;

public class FogIndexList extends MetricsList {

    private final FogIndexChk fogIndexChk;

    public FogIndexList(String[] args) {
        fogIndexChk = new FogIndexChk();
    }

    /**
    * Runs the analysis
    * @param launcher
    */
    @Override
    public void execute(Launcher launcher) {
         fogIndexChk.check(launcher);
    }

   public String toJson() {
        return fogIndexChk.getFogIndex().toString();
    }
}
