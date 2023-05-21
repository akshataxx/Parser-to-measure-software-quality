package com.SENG4430.NestedIfsTest;

import com.SENG4430.NestedIfs.NestedIfsChk;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import static org.junit.Assert.assertEquals;


public class NestedIfsChkTest {
    @Test
    public void doDepthTest() {
        Launcher launcher = new Launcher();

        launcher.addInputResource("src\\test\\java\\com\\SENG4430\\NestedIfsTest\\BadNestedIfCode.java");
        launcher.buildModel();

        CtClass classObject = Query.getElements(launcher.getFactory(),
                new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        CtMethod method = (CtMethod) classObject.getMethodsByName("main").get(0);

        NestedIfsChk nestedIfsChk = new NestedIfsChk();
        assertEquals(nestedIfsChk.doDepth(method), 5);
    }
}
