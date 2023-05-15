package com.SENG4430.ResponseForClass;

import com.SENG4430.MetricsList;
import spoon.Launcher;

import java.util.Map;

//This class is called by the main program execution and calls the rfc check class
//and then converts the output information from the response for class chk class to json output for the main program
//Authored by: Ewart Stone c3350508
//Modified: 15/5/2023


public class ResponseForClassList extends MetricsList
{
    //public methods

    //constructor
    public ResponseForClassList(String[] args) { responseForClassChk = new ResponseForClassChk(); }

    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void execute(Launcher launcher) { responseForClassChk.check(launcher); }

    //preconditon: responseForClassChk.check has been executed
    //postcondition: outputs the classes and their rfc values in json form
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

    //private vars
    private final ResponseForClassChk responseForClassChk;
}
