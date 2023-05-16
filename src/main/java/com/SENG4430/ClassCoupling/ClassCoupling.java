package com.SENG4430.ClassCoupling;

//This class provides a wrapper class that contains a reference to a ctClass
//and stores class coupling data for that class in ClassCoupling
//Authored by: Ewart Stone c3350508
//Modified: 10/5/2023

import spoon.reflect.declaration.CtClass;

public class ClassCoupling
{
    //Public Methods

    //constructor
    public ClassCoupling()
    {
        coupling = 0;
        ctClass = null;
    }

    //alternate constructor
    public ClassCoupling(CtClass ctClass)
    {
        coupling = 0;
        this.ctClass = ctClass;
    }

    //getters and setters
    public void setCoupling(int coupling)
    {
        this.coupling = coupling;
    }

    public void setCtClass(CtClass ctClass)
    {
        this.ctClass = ctClass;
    }

    public int getCoupling()
    {
        return coupling;
    }

    public CtClass getCtClass()
    {
        return ctClass;
    }

    //private variables

    //coupling represents the coupling value of the class
    private int coupling;
    private CtClass ctClass;
}
