package com.SENG4430;
import spoon.Launcher;
public abstract class MetricsList {
    protected boolean hasComments = true;

    public abstract void execute(Launcher launcher);

    public boolean isHasComments() {
        return hasComments;
    }

    public abstract String toJson();
}