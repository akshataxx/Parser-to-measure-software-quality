package com.SENG4430.CyclomaticComplexity;

import spoon.Launcher;
import spoon.reflect.code.*;
import java.util.Scanner;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;

public class CyclomaticComplexityChk {
    private HashMap<String, Integer> classComplexity;
    private String methods;
    private int complexity;

    // Constructor
    public CyclomaticComplexityChk() {
        classComplexity = new HashMap<>();
        complexity = 1;
    }

    // Returns hashmap of complexity values
    public HashMap<String, Integer> getClassComplexity()
    {
        return classComplexity;
    }

    public int getComplexity()
    {
        return complexity;
    }

    // Run the cyclomatic complexity checker
    public void check(Launcher launcher)
    {
        for (CtClass<?> _class : Query.getElements(launcher.getFactory(), new TypeFilter<>(CtClass.class)))
            // for each class
        {
            complexity = 1;
            methods = "class (1)";
            for (CtMethod<?> _method : Query.getElements(_class, new TypeFilter<>(CtMethod.class)))
                // for each method
            {
                complexity += sumComplexity(_method);
            }
            classComplexity.put(_class.getSimpleName() + ": " + methods, complexity);
        }
    }

    private int sumComplexity(CtMethod<?> _method)
        // sums all complexities for decisions
    {
        int sum = 0;
        sum += getIfStatements(_method);
        sum += getForStatements(_method);
        sum += getWhileStatements(_method);
        sum += getSwitchStatements(_method);
        return sum;
    }

    private int getIfStatements(CtMethod<?> _method)
        // get the sum of all if statements in the method
    {
        int localSum = 0;
        for (CtIf _if : _method.getElements(new TypeFilter<>(CtIf.class)))
            // for all if statements
        {
            String ifToString = _if.toString();
            localSum++;
            Scanner scanner = new Scanner(ifToString);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                int index = 0;
                localSum += countOperator(line, "&&");
                localSum += countOperator(line, "||");
            }
        }
        if (localSum > 0)
            // if there is a complexity within the method
        {
            methods += ", " + _method.getSimpleName() + " (" + localSum + ")";
        }
        return localSum;
    }

    private int getWhileStatements(CtMethod<?> _method)
        // get the sum of all While statements in the method
    {
        int localSum = 0;
        for (CtWhile _while : _method.getElements(new TypeFilter<>(CtWhile.class)))
            // for each while statement
        {
            String whileToString = _while.toString();
            localSum++;
            Scanner scanner = new Scanner(whileToString);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                int index = 0;
                localSum += countOperator(line, "&&");
                localSum += countOperator(line, "||");
            }
        }
        if (localSum > 0)
            // if there is a complexity within the method
        {
            methods += ", " + _method.getSimpleName() + " (" + localSum + ")";
        }
        return localSum;
    }

    private int getForStatements(CtMethod<?> _method)
        // gets the sum of all For and ForEach statements in the method
    {
        int localSum = 0;
        for (CtFor _for : _method.getElements(new TypeFilter<>(CtFor.class)))
            // for each for statement
        {
            localSum++;
        }
        if (localSum > 0)
            // if there is a complexity within the method
        {
            methods += ", " + _method.getSimpleName() + " (" + localSum + ")";
        }
        return localSum;
    }

    private int getSwitchStatements(CtMethod<?> _method)
        // gets the sum of all For and ForEach statements in the method
    {
        int localSum = 0;
        for (CtCase _case : _method.getElements(new TypeFilter<>(CtCase.class)))
            // for all cases in the method
        {
            localSum++;
        }
        if (localSum > 0)
            // if there is a complexity within the method
        {
            methods += ", " + _method.getSimpleName() + " (" + localSum + ")";
        }
        return localSum;
    }

    private int countOperator(String line, String check)
        // returns true if the statement matches the decision
    {
        int localsum = 0;
        int index = 0;

        while (index != -1){
            index = line.indexOf(check,index);
            if (index != -1){
                localsum++;
                index+= check.length();
            }
        }
        return localsum;
    }
}
