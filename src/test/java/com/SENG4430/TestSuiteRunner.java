/*
* Developer: Akshata Dhuraji
* Program Name: TestSuiteRunner.java
* Description: Application to run test suite for TestApplication, FogIndex metrics, HalsteadComplexity metrics
 */
package com.SENG4430;

import org.junit.runner.JUnitCore;                                  // Imports necessary classes from the JUnit framework
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestSuiteRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JunitTestSuite.class);  // Run the test suite
        for (Failure failure : result.getFailures()) {               // Loop to check and print all test failures
            System.out.println("Failure Message from TestSuiteRunner: " + failure.toString());                  // Prints failure details to the console
        }

        String testResultMsg;
        if (result.wasSuccessful()) {                               // Checks if all tests passed successfully
            testResultMsg = "\nAll " + result.getRunCount() + " test(s) ran with no errors.";
        } else {                                                    // If any tests failed, prints the number of failed tests
            testResultMsg = "\nOut of, " + result.getRunCount() + " test(s), "+ result.getFailureCount() +" test failed "  ;
        }
        System.out.println(testResultMsg);                      // Prints the test result message to the console
    }
}





