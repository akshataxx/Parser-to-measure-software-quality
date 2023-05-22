package com.SENG4430.CyclomaticComplexity;

import com.SENG4430.MetricsList;
import spoon.Launcher;
import java.util.Map;
import java.util.*;

public class CyclomaticComplexityList extends MetricsList {

    private final CyclomaticComplexityChk CyclomaticComplexityChk; // instances cyclomatic complexity class

    public CyclomaticComplexityList(String[] Args) { CyclomaticComplexityChk = new CyclomaticComplexityChk(); }

    @Override
    public void execute(Launcher launcher) {CyclomaticComplexityChk.check(launcher); }

    public String toJson() {

        String json = "Cyclomatic Complexity: \n";

        for (Map.Entry<String, Integer> entry : CyclomaticComplexityChk.getClassComplexity().entrySet())
        // for every method do
        {
            if (entry.getValue() <= 5)
            {
                json += "[Very low - ";
            }
            else if (entry.getValue() <= 10)
            {
                json += "[Low - ";
            }
            else if (entry.getValue() <=15)
            {
                json += "[Acceptable - ";
            }
            else if (entry.getValue() <= 20)
            {
                json += "[High - ";
            }
            else
            {
                json += "[Very high - ";
            }
            json += entry.getValue() +"] ";
            json += entry.getKey() +"\n";
        }
        return json;
    }
}
