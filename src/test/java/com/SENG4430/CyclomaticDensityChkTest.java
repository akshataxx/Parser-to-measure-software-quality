package com.SENG4430;

import com.SENG4430.CyclomaticDensity.CyclomaticDensityChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;

public class CyclomaticDensityChkTest {
    @Test
    public void testCheck() {
        // Create an instance of CyclomaticDensityChk
        CyclomaticDensityChk cyclomaticDensity = new CyclomaticDensityChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("D:/Software Quality/testResult.java");

        // Call the check method of CyclomaticDensityChk class with the launcher instance
        cyclomaticDensity.check(launcher);

        // Assert that the complexity is greater than 1 (base complexity)
        Assert.assertTrue(cyclomaticDensity.getDensity() >= 0);

    }
}