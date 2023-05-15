package com.SENG4430.ResponseForClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This class provides a wrapper class representing a class
//and information required for the RFC calculation of the class
//Authored by: Ewart Stone c3350508
//Modified: 15/5/2023

public class ResponseForClass
{
    //public methods

    //constructor
    public ResponseForClass(String classId)
    {
        this.classId = classId;
        this.superClass = null;
        this.superClassStr = "";
        this.methods = new HashMap<>();
    }

    //getters and setts
    public String getClassId()
    {
        return classId;
    }

    public void setSuperClass(ResponseForClass rfc)
    {
        superClass = rfc;
    }

    public String getSuperClassStr() { return this.superClassStr; }
    public void setSuperClassStr(String superClassStr) { this.superClassStr = superClassStr; }

    public void addMethod(String id, HashMap<String, Integer> methodRfc)
    {
        methods.put(id, methodRfc);
    }

    //precondition: RFC method parse trees have populated the class via the ResponseForClassChk RFC computation method
    //postcondition: returns and int of the RFC value for the current class
    public int getRFC(HashMap<String, Integer> methods, ArrayList<String> subclasses)
    {
        //System.out.println("\nnew run: " + classId);

        //if first class iteration (not a super class calculation)
            //instantiate methods as copy of recordedMethods
            //and count the number of unique methods
        int out = 0;

        if(subclasses == null)
            subclasses = new ArrayList<>();

        if(methods == null)
        {
            methods = new HashMap<>();

            //for each method parse tree
            for(Map.Entry<String, HashMap<String, Integer>> entry : this.methods.entrySet())
            {
                //if method definition is not already counted
                //then count and record to methods
                if(!methods.containsKey(entry.getKey()))
                {
                    out++;
                    methods.put(entry.getKey(), 0);
                }

                //for each method invocation, constructor call, etc. in the method tree
                //check if already recorded and count if unrecorded and record that it
                //is counted
                for(Map.Entry<String, Integer> invoc : entry.getValue().entrySet())
                {
                    if(!methods.containsKey(invoc.getKey()))
                    {
                        out++;
                        methods.put(invoc.getKey(), 0);
                    }
                }
            }
        }
        //Else a super class calculation is being counted
        //i.e., Functions must be checked if they have been overridden by the subclass
            //prior in the calculation
        else
        {
            //for each method that is not recorded and is not overridden
            for(Map.Entry<String, HashMap<String, Integer>> entry : this.methods.entrySet())
            {

                //Check if function is overridden by checking the descendants previously calculated
                //if not an overridden function
                // i.e., A.func() && ADerivative.func()
                String funcId = entry.getKey().substring(entry.getKey().lastIndexOf('.'));
                boolean overridden = false;

                for(String e : subclasses)
                {
                    String func = e + funcId;

                    if(methods.containsKey(func))
                    {
                        //overridden function
                        //System.out.println(func + " is overridden");
                        overridden = true;
                        break;
                    }
                }

                //if not overridden, count the method and it's aprse tree
                if(!overridden)
                {
                    //if method definition is not already counted
                    //then count and record to methods
                    if(!methods.containsKey(entry.getKey()))
                    {
                        out++;
                        methods.put(entry.getKey(), 0);
                    }

                    //for each method invocation, constructor call, etc. in the method tree
                    //check if already recorded and count if unrecorded and record that it
                    //is counted
                    for(Map.Entry<String, Integer> invoc : entry.getValue().entrySet())
                    {
                        if(!methods.containsKey(invoc.getKey()))
                        {
                            out++;
                            methods.put(invoc.getKey(), 0);
                        }
                    }
                }
            }
        }

        //if class has a superclass recusrively call this function
        //on that superclass and add the result to this result
        if(superClass != null)
        {
            subclasses.add(classId);
            //System.out.println("checking superclass");
            out += superClass.getRFC(methods, subclasses);
        }

        //System.out.println(methods);

        return out;
    }

    //private variables
    private String classId;
    private ResponseForClass superClass;
    private String superClassStr;
    private HashMap<String, HashMap<String, Integer>> methods;

}
