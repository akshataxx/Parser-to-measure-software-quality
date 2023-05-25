package com.SENG4430.ResponseForClassTest;

//This class defines a series of JUNIT tests to check all of the
//possible ways in which response for class can be counted in the codebase and their corresponding duplicate checks
//Possible cases:
//  constructor definitions, constructor calls, method definitions, method invocations,
//   same name constructors / methods w/ different params and internal operations
//  duplicate method invocations (should not be counted), overloaded methods (should not be counted)
//Written by Ewart Stone, c3350508
//Last modified 22/5/23

import com.SENG4430.ResponseForClass.ResponseForClassChk;
import org.junit.Test;
import spoon.Launcher;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseForClassChkTest
{

    //Preconditions: none
    //Postconditions: Tests a class with a constructor and 3 unique methods defintions, RFC = 4
    @Test
    public void rfcSingleClassTest()
    {
        //Tests rfc of a single class with methods

        Launcher launcher = new Launcher();

        launcher.addInputResource("./TestFiles/BadCode.java");

        ResponseForClassChk rfcChk = new ResponseForClassChk();

        rfcChk.check(launcher);

        for (Map.Entry<String, Integer> entry: rfcChk.getResponseForClass().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 4);
        }
    }

    //Preconditions: none
    //Postconditions: Tests an empty class, completed by compilers to have an empty constructor, RFC = 1
    @Test
    public void rfcEmptyClassTest()
    {
        //Tests RFC of an empty class
        //Only the implied empty constructor is counted

        Launcher launcher = new Launcher();

        launcher.addInputResource("./TestFiles/EmptyClass.java");

        ResponseForClassChk rfcChk = new ResponseForClassChk();
        rfcChk.check(launcher);

        for (Map.Entry<String, Integer> entry: rfcChk.getResponseForClass().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 1);
        }
    }

    //Preconditions: none
    //Postconditions: Tests a class with multiple distinct constructors, and multiple distinct methods of the same name
    //                  and different param inputs, 3 unique method calls from BadCode.java, and a duplicate internal method call
    //                  RFC = 8
    @Test
    public void multipleMethodsTest()
    {
        //test a class with multiple constructors and methods of the same name with different parameters
        //checks false duplicates are counted since they are still different

        Launcher launcher = new Launcher();

        launcher.addInputResource("./TestFiles/BaddestCode.java");

        ResponseForClassChk rfcChk = new ResponseForClassChk();
        rfcChk.check(launcher);

        for (Map.Entry<String, Integer> entry: rfcChk.getResponseForClass().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 8);
        }
    }

    //Preconditions: none
    //Postconditions: Tests a class with a superclass and no overridden methods
    //                  all methods in both classes should be counted
    //                  RFC = 10
    @Test
    public void inheritanceWithoutOverloadTest()
    {
        //test an inherited class without any overloaded methods
        //each method is included in the rfc value

        Launcher launcher = new Launcher();

        launcher.addInputResource("./TestFiles/NotAsBadCode.java");

        ResponseForClassChk rfcChk = new ResponseForClassChk();
        rfcChk.check(launcher);

        for (Map.Entry<String, Integer> entry: rfcChk.getResponseForClass().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 10);
        }
    }

    //Preconditions: none
    //Postconditions: Tests a class with a superclass that has overridden methods
    //                  with internal method calls that should both not be counted in the overridden methods
    //                  RFC = 8
    @Test
    public void inheritanceWithOverloadTest()
    {
        //test an inherited class with overloaded methods
        //overloaded methods and their number of method invocations are nto included in the rfc

        Launcher launcher = new Launcher();

        launcher.addInputResource("./TestFiles/Badderest.java");

        ResponseForClassChk rfcChk = new ResponseForClassChk();

        rfcChk.check(launcher);

        for (Map.Entry<String, Integer> entry: rfcChk.getResponseForClass().entrySet())
        {
            assertEquals(entry.getValue().intValue(), 8);
        }
    }
}
