package com.SENG4430.ResponseForClass;

import com.SENG4430.MetricsList;
import spoon.Launcher;

import java.util.Map;

public class ResponseForClassList extends MetricsList
{
    private final ResponseForClassChk responseForClassChk;

    public ResponseForClassList(String[] args) { responseForClassChk = new ResponseForClassChk(); }

    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) { responseForClassChk.check(launcher); }

    public String toJson()
    {
        //init json string with class coupling as root
        String json = "Response for Class: ";

        //add the Response for Class values to the json
        for (Map.Entry<String, Integer> entry: responseForClassChk.getResponseForClass().entrySet())
        {
            json += "\n\t" + entry.getKey() + ": " + entry.getValue() + ",";
        }

        return json;
    }
}
