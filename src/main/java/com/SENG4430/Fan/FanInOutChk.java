package com.SENG4430.Fan;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.reference.CtExecutableReferenceImpl;

import java.util.*;

public class FanInOutChk {

    private final LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> fanInOutChk;

    //private final Map<String, LinkedList<Map<String, Integer>>> classMap = new HashMap<>();
    public FanInOutChk() {
        fanInOutChk = new LinkedList<>();
    }
    public void FanInOutChkLauncher(Launcher launcher) {
            //CtModel model = launcher.getModel();
        //for (CtClass<?> targetClass : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {

            //CtClass<?> targetClass = (CtClass<?>) Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class));
            // Get the target class
            //CtClass<?> targetClass = model.getElements(new TargetClassFilter()).get(0);
        //System.out.print(launcher.);
        // Loop through all the classes in the code using Spoon framework
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class))) {
            // Calculate the Fog Index for each method in the class and add the result to classFogChk LinkedList
            fanInOutChk.addLast(new AbstractMap.SimpleEntry<>(classObject.getSimpleName(), computeFanInOut(classObject)));

        }
            // Measure fan-in and fan-out for each method
            //Map<String, Integer> fanInMap = measureFanIn(targetClass);
            //Map<String, Integer> fanOutMap = measureFanOut(targetClass);
            //LinkedList<Map<String, Integer>> fanning = new LinkedList<>();
            //fanning.add(fanInMap);
            //fanning.add(fanOutMap);
            //classMap.put(targetClass.getSimpleName(), fanning);
            //System.out.println(fanning);
            // Print the results
            //System.out.println("Fan-in and fan-out for class " + targetClass.getSimpleName() + ":");
            //for (CtMethod<?> method : targetClass.getMethods()) {
            //    System.out.println("Method " + method.getSimpleName() + ":");
            //    System.out.println("  Fan-in: " + fanInMap.get(method.getSimpleName()));
            //    System.out.println("  Fan-out: " + fanOutMap.get(method.getSimpleName()));
            //}

        }

    private LinkedList<Map<String, Integer>> computeFanInOut(CtClass<?> classObject) {
        LinkedList<Map<String, Integer>> temp = new LinkedList<>();
        Map<String, Integer> fanInMap = measureFanIn(classObject);
        Map<String, Integer> fanOutMap = measureFanOut(classObject);
        temp.addLast(fanInMap);
        temp.addLast(fanOutMap);
        return temp;
    }
    //fanInOutChk.addAll(classMap.entrySet());
        //System.out.println(classMap);
    //}

    public LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> getFanInOut() {
        return fanInOutChk;
    }

    /**
     * Measure the fan-in of each method in a class
     */
    public static Map<String, Integer> measureFanIn(CtClass<?> targetClass) {
        Map<String, Integer> fanInMap =  new HashMap<>();
        for (CtMethod<?> method : targetClass.getMethods()) {
            int fanIn = 0;
            //Set<CtTypeReference<?>> referencedTypes = method.getReferencedTypes();
            for (CtMethod<?> otherMethod : targetClass.getMethods()) {
                if (!otherMethod.equals(method)) {
                    if(otherMethod.getElements(new TypeFilter<>(CtExecutableReference.class)).toString().contains(method.getSignature()))
                        fanIn++;
                    //if (Query.getElements(otherMethod.getFactory(),new TypeFilter<>(CtMethod.class)).contains(method)) {
                    //System.out.println("-----------------");
                    //System.out.println(method.getSignature());
                    //System.out.println(otherMethod.getElements(new TypeFilter<>(CtExecutableReference.class)));
                    //System.out.println(otherMethod.getElements(new TypeFilter<>(CtExecutableReference.class)).size());
                    //System.out.println(otherMethod.getDirectChildren());
                    //System.out.println(otherMethod.getDirectChildren().size());
                    //System.out.println();
                    //System.out.println("-----------------");
                    //    System.out.println(referencedTypes);
                        //fanIn++;
                    //}
                }
            }
            fanInMap.put(method.getSimpleName(), fanIn);
        }
        return fanInMap;
    }

    public static Map<String, Integer> measureFanOut(CtClass<?> targetClass) {
        Map<String, Integer> fanOutMap =  new HashMap<>();
        for (CtMethod<?> method : targetClass.getMethods()) {
            int fanOut = method.getElements(new TypeFilter<>(CtExecutableReferenceImpl.class)).size();
            fanOutMap.put(method.getSimpleName(), fanOut);
        }
        return  fanOutMap;
    }
}
