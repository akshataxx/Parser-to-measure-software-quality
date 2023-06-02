package com.SENG4430.DepthInheritanceTreeTest;

import com.SENG4430.DepthInheritanceTree.DepthInheritanceTreeChk;
import com.SENG4430.NumberOfChildren.NumberOfChildrenChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;


public class DepthInheritanceTreeChkTest {

    @Test
    public void singleClassTest() {
        // Create an instance of DepthInheritanceTreeChk
        DepthInheritanceTreeChk depthInheritanceTreeChk = new DepthInheritanceTreeChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\DepthInheritanceTreeTest\\TestFiles\\DepthTestClass.java");
        launcher.buildModel();

        // Call the check method of DepthInheritanceTreeChk class with the launcher instance
        depthInheritanceTreeChk.check(launcher);

        // Assert that the single class (root class) has a depth of 0
        Assert.assertEquals(depthInheritanceTreeChk.getDepthInheritanceTreeCheck().get("DepthTestClass").intValue(), 0);
    }

    @Test
    public void linearHierarchyTest() {
        // Create an instance of DepthInheritanceTreeChk
        DepthInheritanceTreeChk depthInheritanceTreeChk = new DepthInheritanceTreeChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\DepthInheritanceTreeTest\\TestFiles\\DepthTestClass.java");
        launcher.buildModel();

        // Call the check method of DepthInheritanceTreeChk class with the launcher instance
        depthInheritanceTreeChk.check(launcher);

        // Assert that the Child1 class has a depth of 1
        Assert.assertEquals(depthInheritanceTreeChk.getDepthInheritanceTreeCheck().get("Child1").intValue(), 1);

    }

    @Test
    public void branchingHierarchyTest() {
        // Create an instance of DepthInheritanceTreeChk
        DepthInheritanceTreeChk depthInheritanceTreeChk = new DepthInheritanceTreeChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\DepthInheritanceTreeTest\\TestFiles\\DepthTestClass.java");
        launcher.buildModel();

        // Call the check method of DepthInheritanceTreeChk class with the launcher instance
        depthInheritanceTreeChk.check(launcher);

        // Assert that the GrandChild1 class has a depth of 2
        Assert.assertEquals(depthInheritanceTreeChk.getDepthInheritanceTreeCheck().get("GrandChild1").intValue(), 2);

    }

    @Test
    public void complexHierarchyTest() {
        // Create an instance of DepthInheritanceTreeChk
        DepthInheritanceTreeChk depthInheritanceTreeChk = new DepthInheritanceTreeChk();

        // Create a launcher for your program
        Launcher launcher = new Launcher();
        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\DepthInheritanceTreeTest\\TestFiles\\DepthTestClass.java");
        launcher.buildModel();

        // Call the check method of DepthInheritanceTreeChk class with the launcher instance
        depthInheritanceTreeChk.check(launcher);

        // Assert that the GreatGrandChild class has a depth of 3
        Assert.assertEquals(depthInheritanceTreeChk.getDepthInheritanceTreeCheck().get("GreatGrandChild").intValue(), 3);

    }

}
