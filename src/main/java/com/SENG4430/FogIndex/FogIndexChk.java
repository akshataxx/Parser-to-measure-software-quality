package com.SENG4430.FogIndex;

import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;
public class FogIndexChk {
    private static final String[] ADD_SYLLABLES = { "ia", "riet", "dien", "iu", "io", "ii", "[aeiouym]bl$", "[aeiou]{3}", "^mc", "ism$",
            "[^aeiouy][^aeiouy]l$", "[^l]lien", "^coa[dglx].", "[^gq]ua[^auieo]", "dnt$" };
    private static final String[] SUBTRACT_SYLLABLES = { "cial", "tia", "cius", "cious", "giu", "ion", "iou", "sia$", ".ely$" };
    private final LinkedList<Map.Entry<String, TreeMap<String, Double>>> classFogChk;

    public FogIndexChk() {      //Constructor
        classFogChk = new LinkedList<>();
    }
    public LinkedList<Map.Entry<String, TreeMap<String, Double>>>  getFogIndex(){   //getter
        return classFogChk;
    }

    public void check(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            classFogChk.addLast(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), calculateMethodFogIndex(classObject)));

        }
    }

    /**
     * Takes a class object and gets all its methods along with comments for each method
     * @param classObject
     * @return method and its fog index score
     */
    private TreeMap<String, Double> calculateMethodFogIndex(CtClass<?> classObject) {
        TreeMap<String, Double> methodComments = new TreeMap<>();
        for(CtMethod<?> methodObject : classObject.getMethods()){
            List<CtComment> comments = methodObject.getElements(new TypeFilter<>(CtComment.class));
            methodComments.put(methodObject.getSimpleName(), calculateFogIndex(comments));
        }
        //System.out.println("The collection is: " + methodComments.values());  //Debug code
        return methodComments;
    }
    /**
     * Takes comments of a method and calculates its fog index score
     * @param methodComments
     * @return fog index score for that method
     */
    private Double calculateFogIndex(List<CtComment> methodComments){
        double words = 0.0, sentences = 0.0, complexWords = 0.0;
        if(!methodComments.isEmpty()) {
            for (CtComment comment : methodComments) {
                complexWords += countComplexWords(comment.getContent());
                sentences++; //Assumption: each comment is one sentence.
                words += wordcount(comment.getContent());
            }
        }
        return words == 0 ? 0.0 : 0.4 * (words / sentences + 100 * (complexWords / words));
    }
    /**
     * This method was taken from: https://www.javatpoint.com/java-program-to-count-the-number-of-words-in-a-string
     * @param string
     * @return Amount of words in a string
     */
    static int wordcount(String string)
    {
        int count=0;

        char[] ch = new char[string.length()];
        for(int i=0;i<string.length();i++)
        {
            ch[i]= string.charAt(i);
            if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                count++;
        }
        return count;
    }
    /**
     * This method computes the complex words
     * @param string
     * @return count of complex words
     */
    private static int countComplexWords(String sentence) {
        String[] words = sentence.toLowerCase().replaceAll("'", "").split("\\s+");
        int complexWordCount = 0;

        for (String word : words) {
            if (word.equals("i") || word.equals("a")) {
                continue;
            }
            if (word.endsWith("e")) {
                word = word.substring(0, word.length() - 1);
            }

            int syllableCount = 0;
            for (String subtractSyllable : SUBTRACT_SYLLABLES) {
                if (word.matches(subtractSyllable)) {
                    syllableCount--;
                }
            }
            for (String addSyllable : ADD_SYLLABLES) {
                if (word.matches(addSyllable)) {
                    syllableCount++;
                }
            }
            if (word.length() == 1) {
                syllableCount++;
            }
            syllableCount += word.split("[^aeiouy]+").length - 1;
            syllableCount = Math.max(1, syllableCount);

            if (syllableCount >= 3) {
                complexWordCount++;
            }
        }

        return complexWordCount;
    }
}
