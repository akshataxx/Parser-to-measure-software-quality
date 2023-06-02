package com.SENG4430.CyclomaticDensityTest;

import com.SENG4430.CyclomaticDensity.CyclomaticDensityChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class CyclomaticDensityChkTest {
    @Test
    public void testCheck() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\CyclomaticDensityTest\\TestCode.java");
        launcher.buildModel();

        CyclomaticDensityChk cyclomaticDensity = new CyclomaticDensityChk();
        cyclomaticDensity.check(launcher);

        // Assert that the complexity is greater than 1 (base complexity)
        Assert.assertTrue(cyclomaticDensity.getDensity() >= 0);
        String results = "";

        // Assert that the complexity for the class and method is correct
        for (Map.Entry<String, Double> entry : cyclomaticDensity.getClassDensity().entrySet())
        // for every method do
        {
            results += String.format("%.0f%%", entry.getValue()) + " ";
            results += entry.getKey();
        }

        Assert.assertTrue(results.equals("80% TestCode: ifCheck (70%), forCheck (67%), whileCheck (92%), caseCheck (85%), "));
    }
}