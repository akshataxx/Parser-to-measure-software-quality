package com.SENG4430.ClassCoupling;

import spoon.reflect.declaration.CtClass;

public class ClassCoupling
{
    public ClassCoupling()
    {
        coupling = 0;
        ctClass = null;
    }

    public ClassCoupling(CtClass ctClass)
    {
        coupling = 0;
        this.ctClass = ctClass;
    }

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

    private int coupling;
    private CtClass ctClass;
}
