package com.SENG4430.NumberOfChildren;

import java.util.LinkedList;
import java.util.Map;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

public class NumberOfChildrenChk {

    private final LinkedList<Map.Entry<String, Integer>> numberOfChildrenChk;

    public NumberOfChildrenChk() { // Constructor
        numberOfChildrenChk = new LinkedList<>();
    }

    public LinkedList<Map.Entry<String, Integer>> getNumberOfChildrenChk() {
        return numberOfChildrenChk;
    }

    /**
     * Method to assess the code using Spoon framework
     */
    public void check(Launcher launcher) {
        // Loop through all the classes in the code using Spoon framework
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {

            int numberOfChildren = classObject.getNestedTypes().size();
            numberOfChildrenChk
                    .add(new java.util.AbstractMap.SimpleEntry<>(classObject.getSimpleName(), numberOfChildren));
        }

    }

}
