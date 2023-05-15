package com.SENG4430.ResponseForClass;

import com.SENG4430.ClassCoupling.ClassCoupling;
import spoon.Launcher;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.*;
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

        //factor in superclass method counts to descendant classes
        //not counting overidden methods
        for(Map.Entry<String, ResponseForClass> entry : ctClassMap.entrySet())
        {
            //if entry has super class
                //if superclass is in the map
                    //set superclass

            if(ctClassMap.get(entry.getValue().getSuperClassStr()) != null)
            {
                //System.out.println(entry.getValue().getSuperClassStr());
                entry.getValue().setSuperClass(ctClassMap.get(entry.getValue().getSuperClassStr()));
            }
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

        ResponseForClass rfcObj = new ResponseForClass(ctClass.getQualifiedName());

        if(ctClass.getSuperclass() != null)
            rfcObj.setSuperClassStr(ctClass.getSuperclass().toString());

        rfcObj.setRecordedMethods(recordedMethods);

        //System.out.println(ctClass.getQualifiedName() + " " + ctClass.getSuperclass());


        for(CtConstructor constructor : ctClass.getElements(new TypeFilter<>(CtConstructor.class)))
        {
            //System.out.println("Adding: " + constructor.toString());
            HashMap<String, Integer> methodInvocations = new HashMap<>();

            //create constructor method id
            String constructorId = constructor.getType() + "(";

            List<Object> params = constructor.getParameters();

            for(int i = 0; i < params.size(); i++)
            {
                constructorId += params.get(i).toString().substring(0, params.get(i).toString().indexOf(" "));

                if(i != params.size() - 1)
                {
                    constructorId += ",";
                }
            }
            constructorId += ")";

            recordedMethods.put(constructorId, 0);

            for(CtInvocation invoc : constructor.getElements(new TypeFilter<>(CtInvocation.class)))
            {
                if(!invoc.toString().equals("super()"))
                {
                    String invocStr = invoc.getDirectChildren().get(0) + "." + invoc.getDirectChildren().get(1);

                    methodInvocations.put(invocStr, 0);
                    recordedMethods.put(invocStr, 0);
                }
            }

            for(CtConstructorCall constructorCall : constructor.getElements(new TypeFilter<>(CtConstructorCall.class)))
            {
                String constructorInvocId = constructorCall.toString().substring(constructorCall.toString().indexOf(" ") + 1);

                methodInvocations.put(constructorInvocId, 0);
            }

            rfcObj.addMethod(constructorId, methodInvocations);
        }

        for(CtMethod method : ctClass.getElements(new TypeFilter<>(CtMethod.class)))
        {
            HashMap<String, Integer> methodInvocations = new HashMap<>();

            String methodStr = ctClass.getQualifiedName() + "." + method.getSimpleName() + "(";

            List<CtElement> methChildren = method.getDirectChildren();
            for(int i = 1; i < methChildren.size() - 1; i++)
            {
                methodStr += methChildren.get(i).getDirectChildren().get(0).toString();

                if(i + 1 != methChildren.size() - 1)
                    methodStr += ",";
            }

            methodStr += ")";


            if(!recordedMethods.containsKey(methodStr))
            {
                recordedMethods.put(methodStr, 0);
            }

            for(CtInvocation invoc : method.getElements(new TypeFilter<>(CtInvocation.class)))
            {
                String invocStr = invoc.getDirectChildren().get(0) + "." + invoc.getDirectChildren().get(1);

                methodInvocations.put(invocStr, 0);
                recordedMethods.put(invocStr, 0);
            }

            for(CtConstructorCall constructorCall : method.getElements(new TypeFilter<>(CtConstructorCall.class)))
            {
                String constructorInvocId = constructorCall.toString().substring(constructorCall.toString().indexOf(" ") + 1);

                methodInvocations.put(constructorInvocId, 0);
            }

            rfcObj.addMethod(methodStr, methodInvocations);
        }

        ctClassMap.put(ctClass.getQualifiedName(), rfcObj);
    }

    public Map<String, Integer> getResponseForClass()
    {
        Map<String, Integer> out = new HashMap<>();

        for(Map.Entry<String, ResponseForClass> entry : ctClassMap.entrySet())
        {
            out.put(entry.getKey(), entry.getValue().getRFC(null, null));
        }

        return out;
    }

    //Private Variables
    private HashMap<String, ResponseForClass> ctClassMap;
}
