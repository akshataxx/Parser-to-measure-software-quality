package com.SENG4430.NOCTest;

import com.SENG4430.NumberOfChildren.NumberOfChildrenChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;

public class NumberOfChildrenChkTest {

    @Test
    public void noChildrenTest() {
        // Create an instance of NumberOfChildrenChk
        NumberOfChildrenChk numberOfChildrenChk = new NumberOfChildrenChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\NOCTest\\TestFiles\\NOCTestClassParent.java");
        launcher.buildModel();

        // Call the check method of NumberOfChildrenChkTest class with the launcher instance
        numberOfChildrenChk.check(launcher);

        //Asserts that the class Child1 has no children
        Assert.assertEquals(numberOfChildrenChk.getNumberOfChildrenChk().get("Child1").intValue(), 0);

    }

    @Test
    public void oneChildTest() {
        // Create an instance of NumberOfChildrenChk
        NumberOfChildrenChk numberOfChildrenChk = new NumberOfChildrenChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\NOCTest\\TestFiles\\NOCTestClassParent.java");
        launcher.buildModel();

        // Call the check method of NumberOfChildrenChkTest class with the launcher instance
        numberOfChildrenChk.check(launcher);

        //Asserts that the class GrandChild2 has 1 child
        Assert.assertEquals(numberOfChildrenChk.getNumberOfChildrenChk().get("GrandChild2").intValue(), 1);

    }

    @Test
    public void multipleChildrenTest() {

        // Create an instance of NumberOfChildrenChk
        NumberOfChildrenChk numberOfChildrenChk = new NumberOfChildrenChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\NOCTest\\TestFiles\\NOCTestClassParent.java");
        launcher.buildModel();

        // Call the check method of NumberOfChildrenChkTest class with the launcher instance
        numberOfChildrenChk.check(launcher);
        //Asserts that the class NOCTestClassParent (root class) has 3 children
        Assert.assertEquals(numberOfChildrenChk.getNumberOfChildrenChk().get("NOCTestClassParent").intValue(), 3);

    }
    @Test
    public void testInvalidInput() {

        /// Create an instance of NumberOfChildrenChk
        NumberOfChildrenChk numberOfChildrenChk = new NumberOfChildrenChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\NOCTest\\TestFiles\\NOCTestClassParent.java");
        launcher.buildModel();

        // Call the check method of NumberOfChildrenChkTest class with the launcher instance
        numberOfChildrenChk.check(launcher);

        //Asserts that the class TestInvalidInput doesn't exist
        Assert.assertNull(numberOfChildrenChk.getNumberOfChildrenChk().get("TestInvalidInput"));

    }
}
