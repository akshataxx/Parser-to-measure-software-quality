/*
 * Developer: Akshata Dhuraji
 * Program Name: FogIndexChk
 * Description: This program computes FogIndex of given file passed as input by TestApplication
 *  and returns the FogIndexChk values to FogIndexList
 */
package com.SENG4430.FogIndex;

import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;
public class FogIndexChk {
    // LinkedList to store the results of Fog Index analysis for each class
    private final LinkedList<Map.Entry<String, TreeMap<String, Double>>> classFogChk;

    /**
     *Constructor to initialize the classFogChk LinkedList
     */
    public FogIndexChk() {      //Constructor
        classFogChk = new LinkedList<>();
    }

    /**
     *Getter method to return the classFogChk LinkedList
     */
    public LinkedList<Map.Entry<String, TreeMap<String, Double>>>  getFogIndex(){   //getter
        return classFogChk;
    }

    /**
     *Method to assess the code using Spoon framework
     */
    public void check(Launcher launcher) {
        // Loop through all the classes in the code using Spoon framework
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            // Calculate the Fog Index for each method in the class and add the result to classFogChk LinkedList
            classFogChk.addLast(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), computeFogIndex(classObject)));

        }
    }

    /**
     * Reads class object and gets all its functions and comments for each function
     * @param classObject
     * @return function name and its fog index number
     */
    public TreeMap<String, Double> computeFogIndex(CtClass<?> classObject) {
        // Calculate the Fog Index for each method in the class and add the result to classFogChk LinkedList
        TreeMap<String, Double> methodComments = new TreeMap<>();
        for(CtMethod<?> methodObject : classObject.getMethods()){
            List<CtComment> functionComments = methodObject.getElements(new TypeFilter<>(CtComment.class));
            //Calculate the Fog Index for each comment and add the result to methodComments TreeMap
            methodComments.put(methodObject.getSimpleName(), calculateFogIndex(functionComments));
        }
        return methodComments;
    }
    /**
     * Reads comments of a function and calculates its fog index
     * @param functionComments
     * @return fog index number for that function
     */
    public Double calculateFogIndex(List<CtComment> functionComments){
        // Initialize variables to store the number of words, sentences, and complex words in the comments
        int numWords = 0;
        int numSentences = 0;
        int numComplexWords = 0;
        double fogIndexOfFunction = 0.0;
        // If there are comments for the method, loop through all the comments
        if(!functionComments.isEmpty()) {
            for (CtComment comment : functionComments) {
                // Get the content of the comment and count the number of words and sentences
                String commentContent = comment.getContent();
                numWords += commentContent.split("\\s+").length;
                numSentences += commentContent.split("[.!?]+").length;
                /*Debug code begins
                System.out.println("Comment: " + commentContent);
                System.out.println("Num words: " + numWords);
                System.out.println("Num sentences: " + numSentences);
                //Debug code ends*/
                // Count the number of complex words in the comment
                numComplexWords += countComplexWords(commentContent);
            }
            // Calculate the Fog Index for the comments using the formula
            fogIndexOfFunction = 0.4 * ((numWords / (double) numSentences) + (100 * numComplexWords / (double) numWords));
        }
        return fogIndexOfFunction;
    }

    /**
     * This method computes the complex words
     * @param  string text
     * @return count of complex words
     */
    public static int countComplexWords(String text) {
        int numComplexWords = 0;
        String[] words = text.split("\\s+");
        for (String word : words) {
            int numSyllables = countSyllables(word);
            if (numSyllables >= 3) {
                numComplexWords++;
            }
        }
        return numComplexWords;
    }

    /**
     * This method computes the Syllables
     * @param  string word
     * @return number of Syllables
     */
    public static int countSyllables(String word) {
        word = word.toLowerCase();
        int numSyllables = 0;
        boolean prevVowel = false;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            boolean isVowel = isVowel(c);
            if (isVowel && !prevVowel) {
                numSyllables++;
            }
            prevVowel = isVowel;
        }
        if (word.endsWith("e")) {
            numSyllables--;
        }
        if (numSyllables == 0) {
            numSyllables = 1;
        }
        return numSyllables;
    }
    /**
     * This method checks if the character is a for vowel
     * @param  char
     * @return true or false
     */
    public static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
