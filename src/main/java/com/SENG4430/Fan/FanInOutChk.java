/*****************************************************************************************************
 Student: Daniel Beiers
 UID: c3039134
 Course: SENG4430 Software Quality
 Assessment: Assignment 2
 ****************************************************************************************************/

package com.SENG4430.Fan;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.reference.CtExecutableReferenceImpl;

import java.util.*;

/*********************************************************************************************************
 This class allows for the parsing of a java project directory and the detection of all methods and their fan in and fan
 out properties.
 Fan-in represents the number of methods or functions that call a specific method or function. It measures how many
 other methods or functions rely on a particular method.
- A high fan-in value indicates that a method is widely used and serves as a central point of functionality.
- A low fan-in value may suggest that a method is less reusable or has limited usage within the codebase.

 Fan-out represents the number of methods or functions called by a specific method or function. It measures how many
 other methods or functions a particular method relies on.
- A high fan-out value suggests that a method depends on multiple other methods, indicating potential complexity or
 coupling.
- A low fan-out value implies that a method has fewer dependencies and may be more focused or modular.
 *********************************************************************************************************/

public class FanInOutChk {

    //Declare a variable to retain the information of each method and there associated fan in/fan out count
    private final LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> fanInOutChk;

    //Constructor
    public FanInOutChk() {
        fanInOutChk = new LinkedList<>();
    }

    //Method that iterates through each class and calls to compute the fan in fan out method.
    //Iteratively adds the data to the local linked list.
    //@param launcher The Spoon method to traverse java project directories
    public void FanInOutChkLauncher(Launcher launcher) {
        // Loop through all the classes in the code using Spoon framework
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            // Calculate the Fog Index for each method in the class and add the result to classFogChk LinkedList
            fanInOutChk.addLast(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), computeFanInOut(classObject)));
        }

    }

    //This method creates two Map objects that each measure the fan in and fan out of each method object within a class.
    //@param classObject The Spoon representation of a class object
    //@return LinkedList<Map<String, Integer>> A Linked List of Maps of each methods fan in then fan out
    private LinkedList<Map<String, Integer>> computeFanInOut(CtClass<?> classObject) {
        LinkedList<Map<String, Integer>> temp = new LinkedList<>();
        Map<String, Integer> fanInMap = measureFanIn(classObject);
        Map<String, Integer> fanOutMap = measureFanOut(classObject);
        temp.addLast(fanInMap);
        temp.addLast(fanOutMap);
        return temp;
    }

    //A getter method to return the results of the computation.
    public LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> getFanInOut() {
        return fanInOutChk;
    }


    //Measure the fan-in of each method in a class
    //@param targetClass The Spoon class object
    //@return Map<String, Integer> A map object containing each classes method names and the associated fan in
    public static Map<String, Integer> measureFanIn(CtClass<?> targetClass) {
        Map<String, Integer> fanInMap =  new HashMap<>();
        for (CtMethod<?> method : targetClass.getMethods()) {
            int fanIn = 0;
            //Set<CtTypeReference<?>> referencedTypes = method.getReferencedTypes();
            for (CtMethod<?> otherMethod : targetClass.getMethods()) {
                if (!otherMethod.equals(method)) {
                    if(otherMethod.getElements(new TypeFilter<>(CtExecutableReference.class)).toString().contains(method.getSignature()))
                        fanIn++;
                }
            }
            fanInMap.put(method.getSimpleName(), fanIn);
        }
        return fanInMap;
    }
    //Measure the fan-in of each method in a class
    //@param targetClass The Spoon class object
    //@return Map<String, Integer> A map object containing each classes method names and the associated fan out
    public static Map<String, Integer> measureFanOut(CtClass<?> targetClass) {
        Map<String, Integer> fanOutMap =  new HashMap<>();
        for (CtMethod<?> method : targetClass.getMethods()) {
            int fanOut = method.getElements(new TypeFilter<>(CtExecutableReferenceImpl.class)).size();
            fanOutMap.put(method.getSimpleName(), fanOut);
        }
        return  fanOutMap;
    }
}
