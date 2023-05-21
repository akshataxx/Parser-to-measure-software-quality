package com.SENG4430.LengthOfIdentifiers;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LengthOfIdentifiersChk {
    private static final int DEFAULT_CUTOFF_LIMIT = 4;

    // Stores average length of identifiers for each class
    private final HashMap<String, Double> averageLengthOfIdentifiers;

    // Stores class and identifier of noteworthy identifiers
    private final HashMap<String, List<String>> lengthOfIdentifiersLessThanOrEqualToCutoff;

    // Cutoff limit for length of Identifiers
    private final int cutOffLimit;

    public LengthOfIdentifiersChk() {
        // Use default cutoff limit
        this(DEFAULT_CUTOFF_LIMIT);
    }

    // Constructor that allows for custom cutOffLimit
    public LengthOfIdentifiersChk(final int cutOffLimit) {
        this.cutOffLimit = cutOffLimit;
        averageLengthOfIdentifiers = new HashMap<>();
        lengthOfIdentifiersLessThanOrEqualToCutoff = new HashMap<>();
    }

    public HashMap<String, Double> getAverageLengthOfIdentifiers() {
        return averageLengthOfIdentifiers;
    }

    public HashMap<String, List<String>> getLengthOfIdentifiersLessThanOrEqualToCutoff() {
        return lengthOfIdentifiersLessThanOrEqualToCutoff;
    }

    public int getCutOffLimit() {
        return cutOffLimit;
    }

    // Method that performs the Length of Identifiers check
    public void check(Launcher launcher) {
        List<CtClass<?>> clazz = Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class));
        calculateLengthOfIdentifierAverage(clazz);
    }

    // This method takes a list of classes goes through them and calculates average length of identifiers for each.
    // These averages are stored in averageLengthOfIdentifiers.
    private void calculateLengthOfIdentifierAverage(List<CtClass<?>> classes) {
        for (CtClass<?> clazz : classes) {
            // Resets all these variables for every new class
            SumResult classNames = new SumResult();
            SumResult methodNames = new SumResult();
            SumResult variableNames = new SumResult();
            int classNameLength = clazz.getSimpleName().length();

            if (classNameLength <= cutOffLimit) {
                // Add class name to lengthOfIdentifiersLessThanOrEqualToCutoff,
                // if it is less than or equal to the cuttOff
                lengthOfIdentifiersLessThanOrEqualToCutoff.computeIfAbsent(
                        clazz.getSimpleName(),
                        k -> new ArrayList<>()
                ).add(clazz.getSimpleName());
            }

            classNames.setSum(clazz.getSimpleName().length());
            classNames.incrementAmountOfNumbers();

            calculateAverageLengthOfIdentifiersWithinClass(clazz, methodNames, variableNames);

            // For every class, stores the average length of identifiers for that class
            averageLengthOfIdentifiers.put(clazz.getSimpleName(),
                    calculateClassAverage(classNames, methodNames, variableNames));
        }
    }

    // This method calculates average length of all methods, variables and parameters within a given class
    // Since class is a reserved name so variable is named "clazz" to represent class
    private void calculateAverageLengthOfIdentifiersWithinClass(
            CtClass<?> clazz, SumResult methodNames, SumResult variableNames) {
        calculateSumMethods(clazz, methodNames);
        calculateSumVariables(clazz, variableNames);
    }

    // This method calculates average length of all methods within a given class.
    private void calculateSumMethods(CtClass<?> clazz, SumResult methodNames) {
        Set<CtMethod<?>> methods = clazz.getMethods();
        for (CtMethod<?> method : methods) {
            int methodLength = method.getSimpleName().length();
            if (methodLength <= cutOffLimit) {
                // Add method name to lengthOfIdentifiersLessThanOrEqualToCutoff if it is noteworthy
                lengthOfIdentifiersLessThanOrEqualToCutoff.computeIfAbsent(
                        clazz.getSimpleName(),
                        k -> new ArrayList<>()
                ).add(method.getSimpleName());
            }
            methodNames.incrementSum(methodLength);
            methodNames.incrementAmountOfNumbers();
        }
    }

    // This method calculates average length of all variables within a given class.
    // This includes parameters to methods and constructors
    private void calculateSumVariables(CtClass<?> clazz, SumResult variableNames) {
        List<CtVariable<?>> variables = clazz.getElements(new TypeFilter<>(CtVariable.class));
        for (CtVariable<?> variable : variables) {
            int variableLength = variable.getSimpleName().length();
            if (variableLength <= cutOffLimit) {
                // Add variable name to lengthOfIdentifiersLessThanOrEqualToCutoff if it is noteworthy
                lengthOfIdentifiersLessThanOrEqualToCutoff.computeIfAbsent(
                        clazz.getSimpleName(),
                        k -> new ArrayList<>()
                ).add(variable.getSimpleName());
            }
            variableNames.incrementSum(variableLength);
            variableNames.incrementAmountOfNumbers();
        }
    }

    // Takes the sum of the length of all the identifiers,
    // divides with the amount of identifiers,
    // the calculates and returns the average length of all the identifiers
    private double calculateClassAverage(SumResult classNames, SumResult methodNames, SumResult variableNames) {
        double sumOfAllIdentifiers = classNames.getSum() + methodNames.getSum() + variableNames.getSum();
        double totalNumberOfIdentifiers = classNames.getAmountOfNumbers()
                + methodNames.getAmountOfNumbers() + variableNames.getAmountOfNumbers();
        return sumOfAllIdentifiers / totalNumberOfIdentifiers;
    }

    // Static nested class to monitor the different averages so that their sums can be calculated.
    // This allows for the calculation of new averages from two or more separate averages.
    static class SumResult {
        private int sum;
        private int amountOfNumbers;

        public SumResult() {
            this.sum = 0;
            this.amountOfNumbers = 0;
        }

        public int getSum() {
            return sum;
        }

        public int getAmountOfNumbers() {
            return amountOfNumbers;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void incrementSum(int value) {
            sum += value;
        }

        public void incrementAmountOfNumbers() {
            amountOfNumbers++;
        }
    }
}
