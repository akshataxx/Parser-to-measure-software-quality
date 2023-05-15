package com.SENG4430.ClassCoupling;

import com.SENG4430.MetricsList;
import spoon.Launcher;

import java.util.Map;

//This class is called by the main program execution and calls the class coupling check class
//and then converts the output information from the class coupling chk class to json output for the main program
//Authored by: Ewart Stone c3350508
//Modified: 10/5/2023

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
