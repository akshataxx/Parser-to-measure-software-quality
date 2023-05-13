package com.SENG4430.Comments;

import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;


import java.util.*;

public class CommentsChk{

    public CommentsChk() {
        commentChk = new LinkedList<>();
    }
    private final LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> commentChk;

    public void commentChkLauncher(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            // Calculate the Fog Index for each method in the class and add the result to classFogChk LinkedList
           commentChk.addLast(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), computeComments(classObject)));

        }
    }
    private LinkedList<Map<String, Integer>> computeComments(CtClass<?> classObject) {
        LinkedList<Map<String, Integer>> temp = new LinkedList<>();
        Map<String, Integer> classComments = measureClassComments(classObject);
        Map<String, Integer> methodComments = measureMethodComments(classObject);
        temp.addLast(classComments);
        temp.addLast(methodComments);
        return temp;
    }

    private Map<String, Integer> measureClassComments(CtClass<?> classObject) {
        List<?> comments = classObject.getElements(new TypeFilter<>(CtComment.class));
        int classCommentCount = comments.size();
        //System.out.println(comments);
        //System.out.println(classObject.getElements(new TypeFilter<>(CtMethod.class)));

        // Iterate over the methods in the class
        for (CtMethod<?> ctMethod : classObject.getElements(new TypeFilter<>(CtMethod.class))) {
            // Exclude the comments within methods
            List<?> methodComments = ctMethod.getElements(new TypeFilter<>(CtComment.class));
            classCommentCount -= methodComments.size();
        }
            return Map.of(classObject.getSimpleName(), classCommentCount);
    }

    public static Map<String, Integer> measureMethodComments(CtClass<?> classObject) {

        Map<String, Integer> temp = new HashMap<>();
        // Iterate over all methods in the class
        for (CtMethod<?> ctMethod : classObject.getMethods()) {
            List<CtComment> functionComments = ctMethod.getElements(new TypeFilter<>(CtComment.class));
            temp.put(ctMethod.getSimpleName(),functionComments.size());
        }

        return temp;

        // Print the results
        //System.out.println("Number of comments per class: " + classCommentCount);
        //System.out.println("Number of comments per method: " + methodCommentCount);
    }

    public LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> getComments() {
        return commentChk;
    }
}

