/*
 * Developer: Akshata Dhuraji
 * Program Name: HalsteadComplexityChkTest.java
 * Description: Program for Unit testing of HalsteadComplexityChk class
 */
package com.SENG4430;
import com.SENG4430.HalsteadComplexity.HalsteadComplexityChk;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;
import spoon.Launcher;

public class HalsteadComplexityChkTest {
    @Test
    public void testCheck() {
        // Create an instance of HalsteadComplexityChk
        HalsteadComplexityChk halsteadComplexityChk = new HalsteadComplexityChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("D:/Software Quality/testResult.java");

        // Call the check method of HalsteadComplexityChk class with the launcher instance
        halsteadComplexityChk.check(launcher);

        // Assert that n1 and n2 are greater than or equal to 0
        Assert.assertTrue(halsteadComplexityChk.getN1() >= 0);
        Assert.assertTrue(halsteadComplexityChk.getN2() >= 0);
        Assert.assertTrue(halsteadComplexityChk.sumValues(halsteadComplexityChk.getDistinctOperators()) >= 0);
        Assert.assertTrue(halsteadComplexityChk.sumValues(halsteadComplexityChk.getDistinctOperands()) >= 0);
    }
}