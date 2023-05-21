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

    /**
     * Method to assess the code using Spoon framework
     */
    public void check(Launcher launcher) {
        // Loop through all the classes in the code using Spoon framework
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {

            int depthOfInheritanceTree = calculateDepthOfInheritanceTree(classObject);
            depthInheritanceTreeCheck
                    .add(new java.util.AbstractMap.SimpleEntry<>(classObject.getSimpleName(), depthOfInheritanceTree));
        }

    }

    private static int calculateDepthOfInheritanceTree(CtClass<?> classObject) {
        int depth = 0;
        while (classObject.getSuperclass() != null) {
            depth++;
            CtType<?> ctType = classObject.getSuperclass().getTypeDeclaration();
            classObject = (CtClass<?>) ctType;
        }
        return depth;
    }

}
