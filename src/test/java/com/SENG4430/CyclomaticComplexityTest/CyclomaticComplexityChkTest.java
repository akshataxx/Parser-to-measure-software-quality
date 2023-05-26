package com.SENG4430.CyclomaticComplexityTest;

import com.SENG4430.CyclomaticComplexity.CyclomaticComplexityChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class CyclomaticComplexityChkTest {
    @Test
    public void testCheck() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\CyclomaticComplexityTest\\TestCode.java");
        launcher.buildModel();

        CyclomaticComplexityChk cyclomaticComplexity = new CyclomaticComplexityChk();
        cyclomaticComplexity.check(launcher);

        // Assert that the complexity is greater than 1 (base complexity)
        Assert.assertTrue(cyclomaticComplexity.getComplexity() >= 0);
        String results = "";

        // Assert that the complexity for the class and method is correct
        for (Map.Entry<String, Integer> entry : cyclomaticComplexity.getClassComplexity().entrySet())
        // for every method do
        {
            results += entry.getValue() +" ";
            results += entry.getKey()+" ";
        }
        Assert.assertTrue(results.equals("33 TestCode: class (1), ifCheck (13), forCheck (3), whileCheck (13), caseCheck (3) "));
    }
}