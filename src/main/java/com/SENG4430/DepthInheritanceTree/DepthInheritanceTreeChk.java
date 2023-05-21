package com.SENG4430.DepthInheritanceTree;

import java.util.LinkedList;
import java.util.Map;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

public class DepthInheritanceTreeChk {

    private final LinkedList<Map.Entry<String, Integer>> depthInheritanceTreeCheck;

    public DepthInheritanceTreeChk() { // Constructor
        depthInheritanceTreeCheck = new LinkedList<>();
    }

    public LinkedList<Map.Entry<String, Integer>> getDepthInheritanceTreeCheck() {
        return depthInheritanceTreeCheck;
    }

    public void check(Launcher launcher) {
        // Loop through all the classes in the code
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {

            int depthOfInheritanceTree = calculateDepthOfInheritanceTree(classObject);
            depthInheritanceTreeCheck
                    .add(new java.util.AbstractMap.SimpleEntry<>(classObject.getSimpleName(), depthOfInheritanceTree));
        }

    }


    /**
     * Calculates the depth of inheritance tree for a given class.
     *
     * @param classObject The CtClass representing the class to calculate the depth for.
     * @return The depth of inheritance tree for the class.
     */
    private int calculateDepthOfInheritanceTree(CtClass<?> classObject) {
        int depth = 0;
        while (classObject.getSuperclass() != null) {
            depth++;
            CtType<?> ctType = classObject.getSuperclass().getTypeDeclaration();
            classObject = (CtClass<?>) ctType;
        }
        return depth;
    }

}
