package com.SENG4430.FanTest;

import com.SENG4430.Fan.FanInOutChk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spoon.Launcher;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.LinkedList;
import java.util.Map;

import static com.SENG4430.Fan.FanInOutChk.measureFanIn;
import static com.SENG4430.Fan.FanInOutChk.measureFanOut;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FanInOutChkTest {

    private FanInOutChk fanInOutChk;
    private Launcher launcher;
    String testClassPath = "src/test/java/com/SENG4430/FanTest/FanTestClass.java";

    @BeforeEach
    public void setUp() {
        fanInOutChk = new FanInOutChk();
        launcher = new Launcher();
        launcher.addInputResource(testClassPath);
        launcher.buildModel();
    }

    @Test
    public void testFanInOutChkLauncher() {
        fanInOutChk.FanInOutChkLauncher(launcher);
        LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> fanInOut = fanInOutChk.getFanInOut();

        // Assert the expected number of classes
        assertEquals(1, fanInOut.size());

        // Assert the fan-in and fan-out values for a specific class and method
        Map.Entry<String, LinkedList<Map<String, Integer>>> classEntry = fanInOut.get(0);
        assertEquals("FanTestClass", classEntry.getKey());

        LinkedList<Map<String, Integer>> methodData = classEntry.getValue();
        Map<String, Integer> fanInMap = methodData.get(0);
        Map<String, Integer> fanOutMap = methodData.get(1);

        assertEquals(1, fanInMap.get("methodA"));
        assertEquals(0, fanOutMap.get("methodB"));
    }

    @Test
    public void testMeasureFanIn() {
        fanInOutChk.FanInOutChkLauncher(launcher);

        Map<String, Integer> testFanIn = measureFanIn(launcher.getModel().getElements(new TypeFilter<>(CtClass.class)).get(0));

        assertEquals(1, testFanIn.get("methodA"));
        assertEquals(2, testFanIn.get("methodB"));
        assertEquals(1, testFanIn.get("methodC"));
    }

    @Test
    public void testMeasureFanOut() {
        fanInOutChk.FanInOutChkLauncher(launcher);

        Map<String, Integer> testFanIn = measureFanOut(launcher.getModel().getElements(new TypeFilter<>(CtClass.class)).get(0));

        assertEquals(3, testFanIn.get("methodA"));
        assertEquals(0, testFanIn.get("methodB"));
        assertEquals(0, testFanIn.get("methodC"));
    }
}
