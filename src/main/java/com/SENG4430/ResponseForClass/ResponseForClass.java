package com.SENG4430.ResponseForClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResponseForClass
{
    //public methods
    public ResponseForClass(String classId)
    {
        this.classId = classId;
        this.superClass = null;
        this.superClassStr = "";
        this.methods = new HashMap<>();
        this.recordedMethods = null;
    }

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

    public void setRecordedMethods(HashMap<String, Integer> map) { this.recordedMethods = map; }

    public void addMethod(String id, HashMap<String, Integer> methodRfc)
    {
        methods.put(id, methodRfc);
    }

    public int getRFC(HashMap<String, Integer> methods, ArrayList<String> subclasses)
    {
        //System.out.println("\nnew run: " + classId);
        //if first class iteration
            //instantiate methods as copy of recordedMethods
        int out = 0;

        if(subclasses == null)
            subclasses = new ArrayList<>();

        if(methods == null)
        {
            methods = new HashMap<>();

            for(Map.Entry<String, HashMap<String, Integer>> entry : this.methods.entrySet())
            {
                if(!methods.containsKey(entry.getKey()))
                {
                    out++;
                    methods.put(entry.getKey(), 0);
                }

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
        else
        {
            //for each method that is not recorded and is not overridden
            for(Map.Entry<String, HashMap<String, Integer>> entry : this.methods.entrySet())
            {
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

                if(!overridden)
                {
                    if(!methods.containsKey(entry.getKey()))
                    {
                        out++;
                        methods.put(entry.getKey(), 0);
                    }

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

    private HashMap<String, Integer> recordedMethods;
}
