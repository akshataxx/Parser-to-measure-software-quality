package com.SENG4430.ClassCoupling;

import spoon.Launcher;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import spoon.reflect.code.*;

import java.util.*;

//This class calculates the Class Coupling values for a set of given classes
//Authored by: Ewart Stone c3350508
//Modified: 10/5/2023

public class ClassCouplingChk
{
    //public methods

    //constructor
    public ClassCouplingChk()
    {
        //init class vars
        ctClass = new HashMap<>();
    }

    //preconditions: launcher is setup for a specific java file or folder
    //postconditions: calculates the class coupling for all java classes in the launcher input
    public void check(Launcher launcher)
    {
        List<CtClass<?>> classInput = Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class));

        //for each class perform a coupling check
        for(CtClass c: classInput)
        {
            ClassCoupling couplingClass = new ClassCoupling(c);

            ctClass.put(c.getQualifiedName(), couplingClass);

            classCouplingComputation(couplingClass);
        }
    }

    //preconditions: a CtClass has been contained and passed in a couplingClass
    //postconditions: calculates and stores the class coupling of the class in the couplingClass input
    public void classCouplingComputation(ClassCoupling couplingClass)
    {
        //get CtClass
        CtClass<?> ctClass = couplingClass.getCtClass();

        System.out.println("-----------------------\n Class: " + ctClass.getSimpleName());

        // Initialize the class coupling metric to zero
        int classCoupling = 0;

        HashMap<String, Integer> recordedClasses = new HashMap<>();

        //add base class to the hashmap so it is not counted
        recordedClasses.put(ctClass.getQualifiedName(), 0);

        //insert primitive types and keywords into recorded so they are not counted types
        recordedClasses.put("int", 0);
        recordedClasses.put("boolean", 0);
        recordedClasses.put("byte", 0);
        recordedClasses.put("short", 0);
        recordedClasses.put("long", 0);
        recordedClasses.put("float", 0);
        recordedClasses.put("double", 0);
        recordedClasses.put("char", 0);

        //add object base class of all java objects to the list so it is not counted
        //record void as a null type keyword to be not included
        recordedClasses.put("void", 0);

        //record Object as an exception to the count as all java files have an object due to the class inheritance of all classes in java
        recordedClasses.put("java.lang.Object", 0);

        //for each invocation in the class
        for (CtInvocation methodInvocation : ctClass.getElements(new TypeFilter<>(CtInvocation.class)))
        {
            //System.out.println("invoc: " + methodInvocation.toString() + " " + methodInvocation.getTarget());

            //if method invocation's class is not recorded
                //record class and increment class coupling
            if(methodInvocation.getTarget() != null)
            {
                if(!recordedClasses.containsKey(methodInvocation.getTarget().toString()))
                {
                    System.out.println("\n Adding: " + methodInvocation.getTarget());
                    classCoupling++;
                    recordedClasses.put(methodInvocation.getTarget().toString(), 0);
                }
            }
        }

        //for each typed element in the class
        for (CtTypedElement typedElement : ctClass.getElements(new TypeFilter<>(CtTypedElement.class)))
        {
            //System.out.println("element: " + typedElement.toString() + " " + typedElement.getType().toString());

            String typedElementStr = typedElement.getType().toString();

            //if array, trim array ending off to get base obj string
            if(typedElementStr.length() > 2)
            {
                if(typedElementStr.substring(typedElementStr.length() - 2,
                        typedElementStr.length()).equals("[]"))
                {
                    typedElementStr = typedElementStr.substring(0,
                            typedElementStr.length() - 2);
                }
            }

            //if element is an object and is not recorded
                //record class and increment class coupling
            if(!recordedClasses.containsKey(typedElementStr))
            {
                System.out.println("\n Adding: " + typedElementStr);
                classCoupling++;
                recordedClasses.put(typedElementStr, 0);
            }
        }

        //set coupling and store
        couplingClass.setCoupling(classCoupling);
    }

    //precondition: check has been run with a valid input
    //postcondition: outputs a Map<String, Integer> of all classes and their couplings
    public Map<String, Integer> getClassCouplings()
    {
        Map<String, Integer> out = new HashMap<>();

        for(Map.Entry<String, ClassCoupling> entry : ctClass.entrySet())
        {
            out.put(entry.getKey(), entry.getValue().getCoupling());
        }

        return out;
    }

    //private vars
    private HashMap<String, ClassCoupling> ctClass;
}