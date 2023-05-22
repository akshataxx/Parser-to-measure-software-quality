/*
 * Developer: Don Manula Ransika Udugahapattuwa
 * Student ID: C3410266
 * Program Name: WeightedMethodsChk.java
 * Description: Program calculates the weight of each method, compares them with a set threshold and warns the developer
 * which methods need refactoring.
 * Measures the sum of complexity of the methods in a class. A predictor of the
 * time and effort required to develop and maintain a class.
 * Higher WMC value suggests a higher complexity, making the class more difficult to understand, test, and maintain.
 */

package com.SENG4430.WeightedMethods;

import org.apache.maven.shared.invoker.SystemOutHandler;
import spoon.Launcher;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;

public class WeightedMethodsChk {

    private final LinkedList<Map.Entry<String, Double>> classWeightedMethodsChk;
    private final Map<String, Double> wmAttributes;
    public WeightedMethodsChk() {      //Constructor
        classWeightedMethodsChk = new LinkedList<>();
        wmAttributes = new LinkedHashMap<>();
        //String password = "test2";

    }


    public void WeightedMethodsChk(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            calculateWeightedMethodsChk(classObject);
        }
    }

    public LinkedList<Map.Entry<String, Double>> getWeightedMethods() {
        return classWeightedMethodsChk;
    }

    private void calculateWeightedMethodsChk(CtClass<?> classObject) {
        double totalWeightedMethods = 0;
        Map<String, Double> methodsChkMap = new TreeMap<>();
        for (CtMethod<?> ctMethod : classObject.getElements(new TypeFilter<>(CtMethod.class))) {
            double methodWeight = calculateWeightedMethodCount(ctMethod);
            methodsChkMap.put(ctMethod.getSimpleName(), methodWeight);
            totalWeightedMethods += methodWeight;
            //String password = "test3";
            //String password = "test4";
        }
        classWeightedMethodsChk.add(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), totalWeightedMethods));
    }



    private static double calculateWeightedMethodCount(CtMethod<?> ctMethod) {
        CtBlock<?> methodBody = ctMethod.getBody();
        if (methodBody == null) {
            return 0;
        }
        int cyclomaticComplexity = calculateCyclomaticComplexity(methodBody);
        return 1 + cyclomaticComplexity; // weight is always above 1
    }

    private static int calculateCyclomaticComplexity(CtBlock<?> methodBody) {
        int complexity = 0;

        // complexity of each method in the class is measured according to
        // the number of if statements, loops, and case statements in the method body
        List<CtIf> ifs = methodBody.getElements(new TypeFilter<>(CtIf.class));
        List<CtLoop> loops = methodBody.getElements(new TypeFilter<>(CtLoop.class));
        List<CtCase> cases = methodBody.getElements(new TypeFilter<>(CtCase.class));
        complexity += ifs.size() + loops.size() + cases.size();
        return complexity;
    }

    public boolean isThresholdExceeded(double threshold) {
        for (Map.Entry<String, Double> entry : classWeightedMethodsChk) {
            if (entry.getValue() > threshold) {
                return true;
            }
        }
        return false;
    }
}
