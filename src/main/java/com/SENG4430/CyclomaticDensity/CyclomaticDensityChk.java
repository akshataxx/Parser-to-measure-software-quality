package com.SENG4430.CyclomaticDensity;

import spoon.Launcher;
import java.util.Scanner;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.code.CtStatement;


import java.util.*;

public class CyclomaticDensityChk {
    private HashMap<String, Double> classDensity;
    private int complexity;
    private int total;
    private String density;

    //Constructor
    public CyclomaticDensityChk()
    {
        classDensity = new HashMap<String, Double>();
        complexity = 1;
        total = 0;
    }

    //Returns hashmap of complexity values
    public HashMap<String, Double> getClassDensity()
    {
        return classDensity;
    }
    public double getDensity()
    {
        return getPcent(complexity, total);
    }

    public int getComplexity()
    {
        return complexity;
    }

    // run the cyclomatic complexity checker
    public void check (Launcher launcher)
    {
        for (CtClass<?> _class : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class)))
        // for each class
        {
            complexity = 0;
            total = 0;
            density = "";
            for (CtMethod<?> _method : Query.getElements(_class, new TypeFilter <CtMethod<?>>(CtMethod.class)))
            // for each method
            {
                checkMethodForDecisions(_method);
            }
            classDensity.put(_class.getSimpleName()+": " + density, getPcent(complexity, total));
        }
    }

    private double getPcent(int numer, int denom)
    {
        if (numer > 0 || denom > 0)
        {
            return ((double) numer / denom) * 100;
        }
        return 0;
    }

    private void checkMethodForDecisions(CtMethod<?> _method)
    {
        int localComplexity = 0;
        int localTotal = 0;

        if (_method.getBody() != null)
        {
            for (CtStatement _statement : _method.getBody().getStatements())
            // for each statement
            {
                String statement = _statement.toString();
                Scanner scanner = new Scanner(statement);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    line = line.replaceAll("\\s+", " ");
                    line = line.replaceAll("/\\*(?:.|[\\n\\r])*?\\*/", "");
                    line = line.replaceAll("//.*", "");
                    if (line.contains("if") || line.contains("for") || line.contains("while") || line.contains("switch") || localComplexity > 0)
                    // count any decision, or once a decision has been found
                    {
                        localComplexity++;
                        complexity++;
                    }
                    total++;
                    localTotal++;
                }
            }
            if (localComplexity > 0 || localTotal > 0)
            {
                density += ""+ _method.getSimpleName()+" ("+ String.format("%.0f%%", getPcent(localComplexity,localTotal))+"), ";
            }
        }
    }
}