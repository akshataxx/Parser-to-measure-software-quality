package com.SENG4430.ClassCouplingTest;

//This class defines a series of JUNIT tests to check all of the
//possible ways in which class coupling can occur in the code and their corresponding duplicate checks
//Possible cases:
//  constructor calls, method invocations, super class, variable declerations
//  return statement method invocations
//Written by Ewart Stone, c3350508
//Last modified 22/5/23

import com.SENG4430.ClassCoupling.ClassCouplingChk;
import org.junit.Test;
import spoon.Launcher;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ClassCouplingChkTest
{

    //Preconditions: none
    //Postconditions: Tests a class without any class coupling
    @Test
    public void emptyClassTest()
    {
        //Class with no external classes
        Launcher launcher = new Launcher();

        launcher.addInputResource("./src/test/java/com/SENG4430/ClassCouplingTest/TestFiles/BadCode.java");
        launcher.buildModel();

        ClassCouplingChk classCouplingChk = new ClassCouplingChk();
        classCouplingChk.check(launcher);

        for (Map.Entry<String, Integer> entry: classCouplingChk.getClassCouplings().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 0);
        }
    }

    //Preconditions: none
    //Postconditions: Tests a class with only a superclass coupling
    @Test
    public void superClassTest()
    {
        //Class with a super class coupling
        Launcher launcher = new Launcher();

        launcher.addInputResource("./src/test/java/com/SENG4430/ClassCouplingTest/TestFiles/BadderestCode.java");
        launcher.buildModel();

        ClassCouplingChk classCouplingChk = new ClassCouplingChk();
        classCouplingChk.check(launcher);


        for (Map.Entry<String, Integer> entry: classCouplingChk.getClassCouplings().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 1);
        }
    }

    //Preconditions: none
    //Postconditions: Tests a clas with external class invocations on BadCode and duplicates
    @Test
    public void externalClassTest()
    {
        //External class calls in methods
        Launcher launcher = new Launcher();

        launcher.addInputResource("./src/test/java/com/SENG4430/ClassCouplingTest/TestFiles/BaddestCode.java");
        launcher.buildModel();

        ClassCouplingChk classCouplingChk = new ClassCouplingChk();
        classCouplingChk.check(launcher);

        for (Map.Entry<String, Integer> entry: classCouplingChk.getClassCouplings().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 1);
        }
    }

    //Preconditions: none
    //Postconditions: Tests a class with all external class calls including:
    //  constructor calls, method invocations, super class, variable declerations
    //  return statement method invocations
    @Test
    public void allClassLocationsTest()
    {
        //tests class invocations in constructors, methods, return statements,
        //typecasting, etc.
        Launcher launcher = new Launcher();

        launcher.addInputResource("./src/test/java/com/SENG4430/ClassCouplingTest/TestFiles/Test.java");
        launcher.buildModel();

        ClassCouplingChk classCouplingChk = new ClassCouplingChk();
        classCouplingChk.check(launcher);

        for (Map.Entry<String, Integer> entry: classCouplingChk.getClassCouplings().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 7);
        }
    }
}
