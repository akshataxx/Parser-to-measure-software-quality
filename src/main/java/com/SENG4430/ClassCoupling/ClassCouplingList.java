package com.SENG4430.ClassCoupling;

import com.SENG4430.MetricsList;
import spoon.Launcher;

import java.util.Map;

public class ClassCouplingList extends MetricsList
{
    private final ClassCouplingChk classCouplingChk;

    public ClassCouplingList(String[] args) { classCouplingChk = new ClassCouplingChk(); }

    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) { classCouplingChk.check(launcher); }

    public String toJson()
    {
        //init json string with class coupling as root
        String json = "ClassCoupling: ";

        //add the class couplings to the json
        for (Map.Entry<String, Integer> entry: classCouplingChk.getClassCouplings().entrySet())
        {
            json += "\n\t" + entry.getKey() + ": " + entry.getValue() + ",";
        }

        return json;
    }
}
