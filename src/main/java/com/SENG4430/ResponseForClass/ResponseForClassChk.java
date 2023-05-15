package com.SENG4430.ResponseForClass;

import spoon.Launcher;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This class calculates the Response For Class values for a set of given classes
//That is the amount of unique methods callable by a class, including it's superclasses
//but excluding overridden classes
//Only classes in the input are considered for the superclass calculations
//Authored by: Ewart Stone c3350508
//Modified: 15/5/2023


public class ResponseForClassChk
{
    //Public Methods

    //constructor
    public ResponseForClassChk()
    {
        //init class vars
        ctClassMap = new HashMap<>();
    }

    //preconditions: launcher is initialised with a valid java file or folder input
    //postconditions: stores and populates responseforclass map with items containing the
        //response for class values and information of each class in the inpute
    public void check(Launcher launcher)
    {
        //get classes
        List<CtClass<?>> classInput = Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class));

        //for each class perform a rfc computation
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

    //precondition: ctClass is a non null ctClass object
    //postcondition: creates and stores a ResponseForClass object in the class map
        //with dual hashmap trees describing the methods initialised in each method of the class code
    public void responseForClassComputation(CtClass<?> ctClass)
    {
        //create new object and set super class
        ResponseForClass rfcObj = new ResponseForClass(ctClass.getQualifiedName());

        if(ctClass.getSuperclass() != null)
            rfcObj.setSuperClassStr(ctClass.getSuperclass().toString());


        //System.out.println(ctClass.getQualifiedName() + " " + ctClass.getSuperclass());


        //for each constructor
            //parse and add parsed method tree to rfcObj
        for(CtConstructor constructor : ctClass.getElements(new TypeFilter<>(CtConstructor.class)))
        {
            //System.out.println("Adding: " + constructor.toString());
            HashMap<String, Integer> methodInvocations = new HashMap<>();

            //create constructor method id
            String constructorId = constructor.getType() + "(";

            List<Object> params = constructor.getParameters();

            //parse constructor params and create id
            for(int i = 0; i < params.size(); i++)
            {
                constructorId += params.get(i).toString().substring(0, params.get(i).toString().indexOf(" "));

                if(i != params.size() - 1)
                {
                    constructorId += ",";
                }
            }
            constructorId += ")";


            //for each method invocation that is not a super() constructor ref, add to method tree in rfcObj
            for(CtInvocation invoc : constructor.getElements(new TypeFilter<>(CtInvocation.class)))
            {
                if(!invoc.toString().equals("super()"))
                {
                    String invocStr = invoc.getDirectChildren().get(0) + "." + invoc.getDirectChildren().get(1);

                    methodInvocations.put(invocStr, 0);
                }
            }

            //for each constructor call add to method tree in rfcObj
            for(CtConstructorCall constructorCall : constructor.getElements(new TypeFilter<>(CtConstructorCall.class)))
            {
                String constructorInvocId = constructorCall.toString().substring(constructorCall.toString().indexOf(" ") + 1);

                methodInvocations.put(constructorInvocId, 0);
            }

            //store method tree parsed from constructor in rfcObj
            rfcObj.addMethod(constructorId, methodInvocations);
        }

        //for each method definition in the class
        for(CtMethod method : ctClass.getElements(new TypeFilter<>(CtMethod.class)))
        {
            HashMap<String, Integer> methodInvocations = new HashMap<>();

            //parse and created string id for the method
            String methodStr = ctClass.getQualifiedName() + "." + method.getSimpleName() + "(";

            List<CtElement> methChildren = method.getDirectChildren();
            for(int i = 1; i < methChildren.size() - 1; i++)
            {
                methodStr += methChildren.get(i).getDirectChildren().get(0).toString();

                if(i + 1 != methChildren.size() - 1)
                    methodStr += ",";
            }

            methodStr += ")";


            //add each invocation in the class to the method parse map
            for(CtInvocation invoc : method.getElements(new TypeFilter<>(CtInvocation.class)))
            {
                String invocStr = invoc.getDirectChildren().get(0) + "." + invoc.getDirectChildren().get(1);

                methodInvocations.put(invocStr, 0);
            }

            //add each constructor call in the class to the method parse map
            for(CtConstructorCall constructorCall : method.getElements(new TypeFilter<>(CtConstructorCall.class)))
            {
                String constructorInvocId = constructorCall.toString().substring(constructorCall.toString().indexOf(" ") + 1);

                methodInvocations.put(constructorInvocId, 0);
            }

            //add method parse map to the rfc Object
            rfcObj.addMethod(methodStr, methodInvocations);
        }

        //store class object with the class name in the ctClassMap
        ctClassMap.put(ctClass.getQualifiedName(), rfcObj);
    }

    //preconditions: responseForClassComputation has been completed for a valid set of classes
    //postconditions: outputs a map with each class and it's RFC value
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
