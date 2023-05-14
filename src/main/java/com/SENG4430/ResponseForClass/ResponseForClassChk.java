package com.SENG4430.ResponseForClass;

import com.SENG4430.ClassCoupling.ClassCoupling;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResponseForClassChk
{
    //Public Methods
    public ResponseForClassChk()
    {
        //init class vars
        ctClassMap = new HashMap<>();


    }

    public void check(Launcher launcher)
    {
        List<CtClass<?>> classInput = Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class));

        //for each class perform a coupling check
        for(CtClass c: classInput)
        {
            responseForClassComputation(c);
        }
    }

    public void responseForClassComputation(CtClass<?> ctClass)
    {
        //recorded methods hashmap
        HashMap<String, Integer> recordedMethods = new HashMap<>();

        int rfc = 0;
        //for each method
            //count method
            //count unique invocations

        System.out.println(ctClass.getQualifiedName() + " " + ctClass.getSuperclass());


        for(CtConstructor constructor : ctClass.getElements(new TypeFilter<>(CtConstructor.class)))
        {
            //System.out.println("Adding: " + constructor.toString());
            rfc++;
        }

        for(CtMethod method : ctClass.getElements(new TypeFilter<>(CtMethod.class)))
        {
            String methodStr = ctClass.getQualifiedName() + "." + method.getSimpleName() + "(";

            List<CtElement> methChildren = method.getDirectChildren();
            for(int i = 1; i < methChildren.size() - 1; i++)
            {
                methodStr += methChildren.get(i).getDirectChildren().get(0).toString();

                if(i + 1 != methChildren.size() - 1)
                    methodStr += ",";
            }

            methodStr += ")";

            //System.out.println(methodStr + "\n----------------");

            if(!recordedMethods.containsKey(methodStr))
            {
                //System.out.println("Adding: " + method.getSimpleName() + " " + method.getDirectChildren() +
                //        " " + method.getDirectChildren().get(1).getDirectChildren() + " " + ctClass.getQualifiedName());
                rfc++;

                recordedMethods.put(methodStr, 0);
            }

            for(CtInvocation invoc : method.getElements(new TypeFilter<>(CtInvocation.class)))
            {
                String invocStr = invoc.getDirectChildren().get(0) + "." + invoc.getDirectChildren().get(1);

                //System.out.println(invocStr + "\n----------------");

                if(!recordedMethods.containsKey(invocStr))
                {
                    //System.out.println("Adding: " + invoc.toString() + " " + invoc.getDirectChildren()
                    //        + " " + invoc.getDirectChildren().get(1).getDirectChildren());

                    rfc++;
                    recordedMethods.put(invocStr, 0);
                }
            }

        }

        ctClassMap.put(ctClass.getSimpleName(), Integer.valueOf(rfc));
    }

    public Map<String, Integer> getResponseForClass()
    {
        Map<String, Integer> out = new HashMap<>();

        for(Map.Entry<String, Integer> entry : ctClassMap.entrySet())
        {
            out.put(entry.getKey(), entry.getValue());
        }

        return out;
    }

    //Private Variables
    private HashMap<String, Integer> ctClassMap;
}
