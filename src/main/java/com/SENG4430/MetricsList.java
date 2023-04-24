package com.SENG4430;
import spoon.Launcher;
/*
* Class created to provide uniform structure to be used while creating all other metrics class
 */
public abstract class MetricsList {
//function to be implemented for metrics computation
    public abstract void execute(Launcher launcher);
//function to be implemented to show metrics results in a formatted way
    public abstract String toJson();
}