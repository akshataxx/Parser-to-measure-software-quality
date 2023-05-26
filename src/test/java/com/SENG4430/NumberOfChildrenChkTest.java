package com.SENG4430;

import com.SENG4430.NumberOfChildren.NumberOfChildrenChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;

public class NumberOfChildrenChkTest {

    @Test
    public void testCheck() {
        // Create an instance of NumberOfChildrenChk
        NumberOfChildrenChk numberOfChildrenChk = new NumberOfChildrenChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("D:/Software Quality/testResult.java");

        // Call the check method of NumberOfChildrenChkTest class with the launcher instance
        numberOfChildrenChk.check(launcher);

        // Assert that the number of children is less than 1
        Assert.assertTrue(numberOfChildrenChk.getNumberOfChildrenChk().size() < 1);

    }
}
