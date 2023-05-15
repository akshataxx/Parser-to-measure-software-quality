/*
 * Developer: Akshata Dhuraji
 * Program Name: FogIndexChkTest.java
 * Description: Program for Unit testing of FogIndexChk class
 */
package com.SENG4430;
import com.SENG4430.FogIndex.FogIndexChk;
// import junit libraries
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import spoon libraries
import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;


import java.util.*;

public class FogIndexChkTest {

    private FogIndexChk fogIndexChk;                    //Create an object of FogIndexChk

    @BeforeEach
    void setUp() {
        fogIndexChk = new FogIndexChk();
    }

    @Test
    void testGetFogIndex() {
        LinkedList<Map.Entry<String, TreeMap<String, Double>>> result = fogIndexChk.getFogIndex();
        Assertions.assertNotNull(result);
    }

    @Test
    void testCheck() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("D:\\Software Quality\\testResult.java");
        fogIndexChk.check(launcher);
        LinkedList<Map.Entry<String, TreeMap<String, Double>>> result = fogIndexChk.getFogIndex();
        for (Map.Entry<String, TreeMap<String, Double>> entry : result) {
            System.out.println("Method name: " + entry.getKey());
            TreeMap<String, Double> methodFogIndex = entry.getValue();
            for (Map.Entry<String, Double> fogIndexEntry : methodFogIndex.entrySet()) {
                System.out.println("Comment: " + fogIndexEntry.getKey() + ", Fog Index: " + fogIndexEntry.getValue());
            }
        }
    }

    @Test
    void testComputeFogIndex() {
        CtClass<?> classObject = new Launcher().getFactory().Class().create("public class TestClass { public void testMethod() {} }");
        TreeMap<String, Double> result = fogIndexChk.computeFogIndex(classObject);
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testCalculateFogIndex() {
        List<CtComment> functionComments = new ArrayList<>();
        functionComments.add(new Launcher().getFactory().createComment().setContent("This is a simple comment."));
        Double result = fogIndexChk.calculateFogIndex(functionComments);
        Assertions.assertEquals(2.0, result, 0.01);
    }

    @Test
    void testCountComplexWords() {
        int result = fogIndexChk.countComplexWords("It is interesting to see how this program works");
        Assertions.assertEquals(1, result);
    }

    @Test
    void testCountSyllables() {
        int result = fogIndexChk.countSyllables("syllable");
        Assertions.assertEquals(1, result);
    }

    @Test
    void testIsVowel() {
        boolean result = fogIndexChk.isVowel('a');
        Assertions.assertTrue(result);
    }
}
