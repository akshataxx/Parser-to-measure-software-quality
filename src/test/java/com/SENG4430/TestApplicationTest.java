/*
 * Developer: Akshata Dhuraji
 * Program Name: TestApplicationTest.java
 * Description: Program for Unit testing of TestApplication class
 */
package com.SENG4430;
import com.SENG4430.TestApplication;
import com.SENG4430.FogIndex.FogIndexList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import spoon.Launcher;
import java.util.*;

// Unit test for the TestApplication class.

public class TestApplicationTest {

     // Test the program with arguments used by TestApplication
    @Test
    public void testProcessArgs() {
        String[] testArgs = {
                "src\\main\\java\\com\\SENG4430\\TestApplication.java", "-m",
                "halstead_complexity fog_index", "-r","cmd"};
        Launcher launcher = TestApplication.processArgs(testArgs);
        assertNotNull(launcher);
    }
    @Test
    public void testCreateMetrics() {
        String[] listOfMetrics = {"fog_index", "halstead_complexity"};
        TestApplication.createMetrics(listOfMetrics);
        LinkedList<MetricsList> metricLists = TestApplication.metricLists;
        assertNotNull(metricLists);
        assertEquals(2, metricLists.size());
    }
    @Test
    public void testExecuteMetrics() {
        String[] testArgs = {"src\\main\\java\\com\\SENG4430\\TestApplication.java", "-m", "fog_index", "-r", "cmd"}; //Arguments passed
        Launcher launcher = TestApplication.processArgs(testArgs);   //pass testArguments to launcher
        TestApplication.executeMetrics(launcher);                   //calls exeucuteMetrics of TestApplication class
        LinkedList<String> results = TestApplication.getResults();
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }
    @Test
    public void testTestResults() {
        String[] testOutput = {"cmd"};
        TestApplication.testResults(testOutput);
        LinkedList<TestResult> testResults = TestApplication.testresults; // instance of TestResult CLass is created
        assertNotNull(testResults);                                       //check if testResult object is not Null
        assertEquals(1, testResults.size());
    }

    @Test
    public void testCreate() {
        // test resul, printed to the command line in the defined format
        LinkedList<String> mockresultlist = new LinkedList<String>();
        mockresultlist.add("metric: fog_index, value : 10");
        mockresultlist.add("metric: halstead_complexity, value : 20");
        String[] testResultsDtl = {"cmd"};
        TestApplication.testResults(testResultsDtl);
        TestApplication.create(mockresultlist);
    }

}