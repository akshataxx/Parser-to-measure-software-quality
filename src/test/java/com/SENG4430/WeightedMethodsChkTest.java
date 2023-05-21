/*
 * Developer: Don Manula Ransika Udugahapattuwa
 * Student ID: C3410266
 * Program Name: WeightedMethodsChkTest.java
 * Description: Program for Unit testing of WeightedMethodsChk method
 */

package com.SENG4430;

import com.SENG4430.WeightedMethods.WeightedMethodsChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;

public class WeightedMethodsChkTest {

    @Test
    public void testClassWeightedMethodsChkPopulation() {
        String resourceDirectory = "C:\\Manula\\Academics\\Master in Cyber Security\\Software Quality\\Assignment\\final from May\\Software-Quality";

        Launcher launcher = new Launcher();
        launcher.addInputResource(resourceDirectory);
        launcher.buildModel();

        WeightedMethodsChk weightedMethodsChk = new WeightedMethodsChk();
        weightedMethodsChk.WeightedMethodsChk(launcher);

        LinkedList<Map.Entry<String, Double>> classWeightedMethodsChk = weightedMethodsChk.getWeightedMethods();

        System.out.println("Number of classes analyzed: " + classWeightedMethodsChk.size());

        for (Map.Entry<String, Double> entry : classWeightedMethodsChk) {
            System.out.println("Class: " + entry.getKey() + ", Weighted Methods: " + entry.getValue());
        }

        Assert.assertFalse("classWeightedMethodsChk should not be empty", classWeightedMethodsChk.isEmpty());
    }

    @Test
    public void testCalculateWeightedMethodsChk() {
        String resourceDirectory = "C:\\Manula\\Academics\\Master in Cyber Security\\Software Quality\\Assignment\\final from May\\Software-Quality";

        Launcher launcher = new Launcher();
        launcher.addInputResource(resourceDirectory);
        launcher.buildModel();

        WeightedMethodsChk weightedMethodsChk = new WeightedMethodsChk();
        weightedMethodsChk.WeightedMethodsChk(launcher);

        LinkedList<Map.Entry<String, Double>> classWeightedMethodsChk = weightedMethodsChk.getWeightedMethods();

        Assert.assertFalse("classWeightedMethodsChk should not be empty", classWeightedMethodsChk.isEmpty());
    }


    @Test
    public void testThresholdExceeded() {
        WeightedMethodsChk weightedMethodsChk = new WeightedMethodsChk();
        LinkedList<Map.Entry<String, Double>> classWeightedMethodsChk = weightedMethodsChk.getWeightedMethods();

        // Add entries to classWeightedMethodsChk list
        classWeightedMethodsChk.add(new AbstractMap.SimpleEntry<>("TestClass", 2.5));
        classWeightedMethodsChk.add(new AbstractMap.SimpleEntry<>("AnotherClass", 0.8));

        // Test threshold
        double threshold = 2.0;
        Assert.assertTrue("Threshold should be exceeded", weightedMethodsChk.isThresholdExceeded(threshold));

        threshold = 3.0;
        Assert.assertFalse("Threshold should not be exceeded", weightedMethodsChk.isThresholdExceeded(threshold));
    }
}
