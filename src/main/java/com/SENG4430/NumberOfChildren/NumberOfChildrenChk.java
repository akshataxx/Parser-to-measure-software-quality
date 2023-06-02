package com.SENG4430.NumberOfChildren;

import java.util.HashMap;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

public class NumberOfChildrenChk {

    private final HashMap<String, Integer> numberOfChildrenChk;

    public NumberOfChildrenChk() { // Constructor
        numberOfChildrenChk = new HashMap<>();
    }

    public HashMap<String, Integer> getNumberOfChildrenChk() {
        return numberOfChildrenChk;
    }

    /**
     * Method to assess the code using Spoon framework
     */
    public void check(Launcher launcher) {
        // Loop through all the classes in the code using Spoon framework
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            if (!classObject.isInterface()) {
                int numberOfChildren = calculateNumberOfChildren(classObject);
                numberOfChildrenChk
                        .put(classObject.getSimpleName(), numberOfChildren);
            }
        }

    }

    /**
     * Calculates the number of direct children for a given class.
     *
     * @param classObject The CtClass representing the class to calculate the number
     *                    of children for.
     * @return The number of direct children for the class.
     */
    private int calculateNumberOfChildren(CtClass<?> classObject) {
        int numberOfChildren = 0;
        for (CtElement element : classObject.getPackage().getElements(new TypeFilter<>(CtClass.class))) {

            CtTypeReference<?> superClass = ((CtClass<?>) element).getSuperclass();

            // Check if the element has a superclass and its superclass is the current class
            if (superClass != null && superClass.getQualifiedName().equals(classObject.getQualifiedName())) {
                numberOfChildren++;
            }
        }
        return numberOfChildren;
    }

}
