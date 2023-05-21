package com.SENG4430.CyclomaticDensity;

import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.util.Map;
import java.util.*;

public class CyclomaticDensityList extends MetricsList {

    private final CyclomaticDensityChk CyclomaticDensityChk; // instances cyclomatic complexity class

    public CyclomaticDensityList (String[] Args) { CyclomaticDensityChk = new CyclomaticDensityChk(); }

    @Override
    public void execute(Launcher launcher) {CyclomaticDensityChk.check(launcher); }

    public String toJson() {

        String json = "Cyclomatic Density: \n";

        for (Map.Entry<String, Double> entry : CyclomaticDensityChk.getClassDensity().entrySet())
        // for every method do
        {
            json += "["+String.format("%.0f%%", entry.getValue())+"] ";
            json += entry.getKey()+"\n";
        }
        return json;
    }
}
