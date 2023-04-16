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
      // Initialize the JSON string with the FogIndex root object
       String json = " FogIndex: ";
       // Add the FogIndex to JSON string
       json += "\t"+ fogIndexChk.getFogIndex().toString();
       return json;
      // return fogIndexChk.getFogIndex().toString();
    }
}
