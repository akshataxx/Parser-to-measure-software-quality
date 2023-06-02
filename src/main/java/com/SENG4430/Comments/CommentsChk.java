/*****************************************************************************************************
Student: Daniel Beiers
UID: c3039134
Course: SENG4430 Software Quality
Assessment: Assignment 2
 ****************************************************************************************************/
package com.SENG4430.Comments;

import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;


import java.util.*;
/*********************************************************************************************************
 This class allows for the parsing of a java project directory and the detection of all comments contained within
 each class and method, including orphaned comments, i.e. comments that are outside of classes or don't have parents.
 The class is called via the 'commentChkLauncher' and iteratively adds a compounded map of class name, attributed comments,
 and a linked map of included method names and their own comments.
 *********************************************************************************************************/

public class CommentsChk{

    // Declaring the Linked List of objects for the class as a compounded Map Linked List.
    private final LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> commentChk;


    //Constructor instantiating a new linked list.
    //This holds all the data to be returned.
    public CommentsChk() {
        commentChk = new LinkedList<>();
    }

    // This method is called to instigate the search through the project. It continuously adds elements to the LinkedList
    //@param launcher the Spoon implementation of traversing a project directory.
    public void commentChkLauncher(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            // Calculate the Fog Index for each method in the class and add the result to classFogChk LinkedList
           commentChk.addLast(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), computeComments(classObject)));

        }
    }

    //Method to traverse each class object and all the methods contained in it.
    //Appending the linked list with the class and then all the methods details
    //@param classObject A Spoon element to represent a class object
    //@return LinkedList<Map<String, Integer>> A linkedList containing a class name, with number of comments, and all method names and comments.
    private LinkedList<Map<String, Integer>> computeComments(CtClass<?> classObject) {
        LinkedList<Map<String, Integer>> temp = new LinkedList<>();
        Map<String, Integer> classComments = measureClassComments(classObject);
        Map<String, Integer> methodComments = measureMethodComments(classObject);
        temp.addLast(classComments);
        temp.addLast(methodComments);
        return temp;
    }

    //Method to travers each class in the project and store the number of comments in each.
    //It calls a method to also traverse each method within a class.
    //@param classObject A Spoon element to represent a class object
    //@return Map<String, Integer> A linked list mapping each class to the amount of comments in each.
    public Map<String, Integer> measureClassComments(CtClass<?> classObject) {
        List<?> comments = classObject.getElements(new TypeFilter<>(CtComment.class));
        int classCommentCount = comments.size();

        // Iterate over the methods in the class
        for (CtMethod<?> ctMethod : classObject.getElements(new TypeFilter<>(CtMethod.class))) {
            // Exclude the comments within methods
            List<?> methodComments = ctMethod.getElements(new TypeFilter<>(CtComment.class));
            classCommentCount -= methodComments.size();
        }
            return Map.of(classObject.getSimpleName(), classCommentCount);
    }

    //Method to iterate over each method object within a class object and count the number of comments along with the method name.
    //@param classObject The Spoon class object to map
    //@return Map<String, Integer> A map of each method name and how many comments belonging to each.
    public static Map<String, Integer> measureMethodComments(CtClass<?> classObject) {

        Map<String, Integer> temp = new HashMap<>();
        // Iterate over all methods in the class
        for (CtMethod<?> ctMethod : classObject.getMethods()) {
            List<CtComment> functionComments = ctMethod.getElements(new TypeFilter<>(CtComment.class));
            temp.put(ctMethod.getSimpleName(),functionComments.size());
        }
        return temp;
    }

    //Getter to return the results of the private LinkedList
    public LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> getComments() {
        return commentChk;
    }
}

