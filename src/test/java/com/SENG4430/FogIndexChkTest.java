/*
 * Developer: Akshata Dhuraji
 * Program Name: FogIndexChkTest.java
 * Description: Program for Unit testing of FogIndexChk class
 */
package com.SENG4430;
import com.SENG4430.FogIndex.FogIndexChk;
// import junit libraries
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//import spoon libraries
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.code.CtComment;
import spoon.reflect.visitor.filter.TypeFilter;


import java.util.*;

public class FogIndexChkTest {
    private FogIndexChk fogIndexChk;
    private Launcher launcher;
    private CtClass<?> ctClass;

    @BeforeEach
    public void setUp() {
        fogIndexChk = new FogIndexChk();
        launcher = mock(Launcher.class);
        ctClass = mock(CtClass.class);
    }

    @Test
    public void testGetFogIndex() {                              // Test method for GetFogIndex
        LinkedList<Map.Entry<String, TreeMap<String, Double>>> classFogChk = fogIndexChk.getFogIndex();
        assertEquals(0, classFogChk.size());
    }

    @Test
    public void testComputeFogIndex() {                          // Test method for ComputeFogIndex
        CtMethod<?> function1 = mock(CtMethod.class);            // Create mock CtMethod objects
        CtMethod<?> function2 = mock(CtMethod.class);
        CtComment comment1 = mock(CtComment.class);              // Create mock CtComment objects
        CtComment comment2 = mock(CtComment.class);

        List<CtMethod<?>> methods = new ArrayList<>();
        methods.add(function1);
        methods.add(function2);

        when(ctClass.getMethods()).thenReturn(new HashSet<>(methods));
        when(function1.getSimpleName()).thenReturn("method1");
        when(function2.getSimpleName()).thenReturn("method2");

        List<CtComment> comments1 = new ArrayList<>();
        comments1.add(comment1);

        List<CtComment> comments2 = new ArrayList<>();
        comments2.add(comment2);

        when(function1.getElements(any(TypeFilter.class))).thenReturn(comments1);
        when(function2.getElements(any(TypeFilter.class))).thenReturn(comments2);

        when(comment1.getContent()).thenReturn("This is Test Comment 1 ");      // Mock contents are set up for comments
        when(comment2.getContent()).thenReturn("This is Test Comment 2 ");

        TreeMap<String, Double> expected = new TreeMap<>();
        expected.put("method1", 2.0);                                             // Expected fog index based on the formula
        expected.put("method2", 2.0);

        TreeMap<String, Double> result = fogIndexChk.computeFogIndex(ctClass);

        System.out.println("Expected Result: " + expected);
        System.out.println("Computed Result: " + result);

        assertEquals(expected, result);
    }

    @BeforeEach
    @Test
    public void  testCalculateFogIndex() {                         // Test method for calculateFogIndex
        FogIndexChk fogIndexChk = new FogIndexChk();               // Create an instance of the class under test
        CtComment comment1 = mock(CtComment.class);                // Create mock CtComment objects
        CtComment comment2 = mock(CtComment.class);

        String comment1Dtls = "This is a sample comment.";         // Set up mocked content for the comments
        String comment2Dtls = "Another comment to test.";
        when(comment1.getContent()).thenReturn(comment1Dtls);
        when(comment2.getContent()).thenReturn(comment2Dtls);

        List<CtComment> comments = new ArrayList<>();              // Create a list of mock comments
        comments.add(comment1);
        comments.add(comment2);

        // Calculate the expected fog index manually based on the formula
        int numWords = comment1Dtls.split("\\s+").length + comment2Dtls.split("\\s+").length;
        int numSentences = comment1Dtls.split("[.!?]+").length + comment2Dtls.split("[.!?]+").length;
        int numComplexWords = fogIndexChk.countComplexWords(comment1Dtls) + fogIndexChk.countComplexWords(comment2Dtls);
        double expectedFogIndex = 0.4 * ((numWords / (double) numSentences) + (100 * numComplexWords / (double) numWords));

        // Calculate the fog index value using the method from FogIndexChk class and assert the result
        double result = fogIndexChk.calculateFogIndex(comments);
        assertEquals(expectedFogIndex, result, 0.0001);        // Specify a delta for floating-point comparison
    }

    @Test
    public void testCountComplexWords() {                           // Test method for CountComplexWords method
        int expected = 2;
        int result = fogIndexChk.countComplexWords("It is interesting to make my program tractable.");

        assertEquals(expected, result);
    }

    @Test
    public void testCountSyllables() {                              // Test method for CountSyllables method
        int expected = 2;
        int result = fogIndexChk.countSyllables("syllables");

        assertEquals(expected, result);
    }

    @Test
    public void testIsVowel() {                                     // Test method for IsVowel method
        boolean result = fogIndexChk.isVowel('a');
        assertEquals(true, result);
        System.out.println("Is it a vowel: "+ result);
    }
}
