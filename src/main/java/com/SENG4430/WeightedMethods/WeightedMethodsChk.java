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
    public WeightedMethodsChk() {      //Constructor
        classWeightedMethodsChk = new LinkedList<>();
    }
    public void WeightedMethodsChk(Launcher launcher) {
        //classWeightedMethodsChk = new ArrayList<>();
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
        }
        // System.out.println("Class name: " + classObject.getSimpleName());
        // System.out.println("Total weighted method count: " + totalWeightedMethods);
        // System.out.println("------------------------------");
        classWeightedMethodsChk.add(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), totalWeightedMethods));
        // System.out.println(classWeightedMethodsChk);

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
}
