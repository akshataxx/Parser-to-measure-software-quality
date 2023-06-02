package com.SENG4430.DepthInheritanceTree;

import java.util.HashMap;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

public class DepthInheritanceTreeChk {

    private final HashMap<String, Integer> depthInheritanceTreeCheck;

    public DepthInheritanceTreeChk() { // Constructor
        depthInheritanceTreeCheck = new HashMap<>();
    }

    public HashMap<String, Integer> getDepthInheritanceTreeCheck() {
        return depthInheritanceTreeCheck;
    }

    /**
     * Method to assess the code using Spoon framework
     */
    public void check(Launcher launcher) {
        // Loop through all the classes in the code
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {

            int depthOfInheritanceTree = calculateDepthOfInheritanceTree(classObject);
            depthInheritanceTreeCheck
                    .put(classObject.getSimpleName(), depthOfInheritanceTree);
        }

    }
    /**
     * Calculates the depth of inheritance tree for a given class.
     *
     * @param classObject The CtClass representing the class to calculate the depth for.
     * @return The depth of inheritance tree for the class.
     */
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
