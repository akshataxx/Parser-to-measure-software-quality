package com.SENG4430.NestedIfs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.*;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.Launcher;

import java.util.*;

public class NestedIfsChk {
    // Depth limit for nested if statements
    private int nestedIfsLimit;
    // Stores nested if scores for each class
    private HashMap<String, HashMap<String, Integer>> nestedIfsScoresForClass;

    // Default constructor
    public NestedIfsChk() {
        // Default value for minimum if statement depth is 3
        this(3);
    }

    // Constructor for when the minimum statement depth is set by user
    public NestedIfsChk(final int minimumIfStatementDepth) {
        nestedIfsLimit = minimumIfStatementDepth;
        nestedIfsScoresForClass = new HashMap<>();
    }

    // Get nested if scores for each class in JSON format
    public String getNestedIfsScoredForClassJson() {
        return getNestedIfsScoresJson(true);
    }

    // Get nested if scores for each class as a HashMap
    public HashMap<String, HashMap<String, Integer>> getNestedIfsScoresForClass() {
        HashMap<String, HashMap<String, Integer>> finalNestedIfsScoresForClass = new HashMap<>();

        // Iterate over classes and their nested if scores
        for (Map.Entry<String, HashMap<String, Integer>> classObject : nestedIfsScoresForClass.entrySet()) {
            String classString = classObject.getKey();
            HashMap<String, Integer> methodMaxDepth = classObject.getValue();

            HashMap<String, Integer> finalNestedIfScores = new HashMap<>();
            // Filter methods based on the maximum depth and add them to the final scores
            for (Map.Entry<String, Integer> methodObject : methodMaxDepth.entrySet()) {
                String methodString = methodObject.getKey();
                int maxDepth = methodObject.getValue();
                if (maxDepth >= nestedIfsLimit) {
                    finalNestedIfScores.put(methodString, maxDepth);
                }
            }

            // Add class and its nested if scores to the final result if there are any scores
            if (!finalNestedIfScores.isEmpty()) {
                finalNestedIfsScoresForClass.put(classString, finalNestedIfScores);
            }
        }

        return finalNestedIfsScoresForClass;
    }

    // This method performs the Nested Ifs Checks
    public void check(Launcher launcher) {
        // Get all the classes present in the source code
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            HashMap<String, Integer> methodConditionalNestingScores = new HashMap<>();

            // Get all the methods present in the source code
            for (CtExecutable<?> methodObject : getMethods(classObject)) {
                // Calculate the maximum depth of nested if statements in the method
                int maxDepth = doDepth(methodObject);
                methodConditionalNestingScores.put(methodObject.getSimpleName(), maxDepth);
            }

            // Add class and its nested if scores to the overall nested if scores
            nestedIfsScoresForClass.put("Class: " + classObject.getQualifiedName(), methodConditionalNestingScores);
        }
    }

    // Check if an if statement has an else block
    public boolean isElseInIfStatement(CtIf ifStatement) {
        Iterator<CtElement> iterator = ifStatement.getElseStatement().descendantIterator();
        iterator.next();
        CtElement firstElementInElseBlock = iterator.next();

        try {
            // If the first element in the else block is an if statement, it means there is no else block
            CtIf elseIfBlock = (CtIf) firstElementInElseBlock;
            return false;
        } catch (Exception e) {
            // Returns true if there is an else block
            return true;
        }
    }

    // Calculate the depth of nested if statements in a method
    public int doDepth(CtExecutable<?> methodObject) {
        // If the method has only one child, it means there are no if statements
        if (methodObject.getDirectChildren().size() <= 1) {
            return 0;
        } else {
            int maxDepth = 0;
            // Iterate over if statements in the method
            for (CtIf ifStatement : Query.getElements(methodObject, new TypeFilter<>(CtIf.class))) {
                // Check if the if statement is in the body of the method
                if (ifStatement.getParent().getRoleInParent() == CtRole.BODY) {
                    // Calculate the depth of nested if statements in the if block
                    int currentDepth = doDepthOnCodeBlock(-1, ifStatement);
                    if (currentDepth > maxDepth) {
                        maxDepth = currentDepth;
                    }
                }
            }
            return maxDepth;
        }
    }

    // Calculate the depth of nested if statements in a code block
    public int doDepthOnCodeBlock(int depth, CtElement codeBlock) {
        depth++;
        ArrayList<Integer> depthList = new ArrayList<>();
        CtIf ifStatement = getFirstIfStatementFromCodeBlock(codeBlock);

        // Iterate over nested if statements in the code block
        while (ifStatement != null) {
            // Calculate the depth of nested if statements in the if block
            int currentDepth = doDepthOnCodeBlock(depth, ifStatement.getThenStatement());
            depthList.add(currentDepth);

            // Check if there is an else block
            if (ifStatement.getElseStatement() != null) {
                boolean isElse = isElseInIfStatement(ifStatement);
                if (isElse) {
                    // Calculate the depth of nested if statements in the else block
                    currentDepth = doDepthOnCodeBlock(depth, ifStatement.getElseStatement());
                    depthList.add(currentDepth);
                    ifStatement = null;
                } else {
                    // Get the first if statement in the else block
                    ifStatement = getFirstIfStatementFromCodeBlock(ifStatement.getElseStatement());
                }
            } else {
                break;
            }
        }

        // Return the maximum depth from the depth list or the current depth if there are no nested if statements
        if (depthList.isEmpty()) {
            return depth;
        } else {
            return Collections.max(depthList);
        }
    }

    // Get the first if statement in a code block
    public CtIf getFirstIfStatementFromCodeBlock(CtElement codeBlock) {
        List<CtIf> ifStatements = Query.getElements(codeBlock, new TypeFilter<>(CtIf.class));
        if (!ifStatements.isEmpty()) {
            return ifStatements.get(0);
        } else {
            return null;
        }
    }

    // Get methods from a class object
    public ArrayList<CtExecutable<?>> getMethodsFromClassObject(CtClass<?> classObject) {
        return getMethods(classObject);
    }

    // Get all methods and constructors from a class object
    private ArrayList<CtExecutable<?>> getMethods(CtClass<?> classObject) {
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        ArrayList<CtExecutable<?>> methodsArrayList = new ArrayList<>(methodsCollection);
        ArrayList<CtExecutable<?>> constructorArrayList = getConstructors(classObject);
        methodsArrayList.addAll(constructorArrayList);
        return methodsArrayList;
    }

    // Get all constructors from a class object
    private ArrayList<CtExecutable<?>> getConstructors(CtClass<?> classObject) {
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        return new ArrayList<>(constructorCollection);
    }

    // Generate JSON string for nested if scores
    private String getNestedIfsScoresJson(boolean prettyPrinting) {
        HashMap<String, HashMap<String, Integer>> finalNestedIfsScoresForClass = getNestedIfsScoresForClass();
        HashMap<String, HashMap<String, HashMap<String, Integer>>> finalNestedIfsScoresForClassDescription = new HashMap<>();
        finalNestedIfsScoresForClassDescription.put(
                String.format("Depth of Nested Ifs where nesting is %s or more", nestedIfsLimit),
                finalNestedIfsScoresForClass
        );

        GsonBuilder gsonBuilder = new GsonBuilder();
        if (prettyPrinting) {
            gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJson(finalNestedIfsScoresForClassDescription);
    }
}
