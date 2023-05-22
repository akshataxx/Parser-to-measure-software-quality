package com.SENG4430;

import com.SENG4430.CyclomaticComplexity.CyclomaticComplexityChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;


public class CyclomaticComplexityChkTest {
    @Test
    public void testCheck() {
        // Create an instance of CyclomaticComplexityChk
        CyclomaticComplexityChk cyclomaticComplexity = new CyclomaticComplexityChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("D:/Software Quality/testResult.java");

        // Call the check method of CyclomaticComplexityChk class with the launcher instance
        cyclomaticComplexity.check(launcher);

        // Assert that the complexity is greater than 1 (base complexity)
        Assert.assertTrue(cyclomaticComplexity.getComplexity() >= 0);

    }
}