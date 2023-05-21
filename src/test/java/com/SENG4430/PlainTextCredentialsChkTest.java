/*
 * Developer: Don Manula Ransika Udugahapattuwa
 * Student ID: C3410266
 * Program Name: PlainTextCredentialsChkTest.java
 * Description: Program for Unit testing of PlainTextCredentialsChk method
 */


package com.SENG4430;

import com.SENG4430.PlainTextCredentials.PlainTextCredentialsChk;
import org.junit.Assert;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlainTextCredentialsChkTest {

    @Test
    public void testPlainTextCredentialsChkConstructor() {
        PlainTextCredentialsChk plainTextCredentialsChk = new PlainTextCredentialsChk();
        LinkedList<Map.Entry<String, List<String>>> classPlainTextCredentials = plainTextCredentialsChk.getClassPlainTextCredential();
        Assert.assertTrue(classPlainTextCredentials.isEmpty());
    }

    @Test
    public void testGetClassPlainTextCredential() {
        PlainTextCredentialsChk plainTextCredentialsChk = new PlainTextCredentialsChk();
        LinkedList<Map.Entry<String, List<String>>> classPlainTextCredentials = plainTextCredentialsChk.getClassPlainTextCredential();

        List<String> creds1 = new ArrayList<>();
        creds1.add("api = 123");
        creds1.add("password = 'abc'");
        classPlainTextCredentials.add(new AbstractMap.SimpleEntry<>("TestClass1", creds1));

        List<String> creds2 = new ArrayList<>();
        creds2.add("secret = 'xyz'");
        classPlainTextCredentials.add(new AbstractMap.SimpleEntry<>("TestClass2", creds2));

        LinkedList<Map.Entry<String, List<String>>> retrievedCredentials = plainTextCredentialsChk.getClassPlainTextCredential();
        Assert.assertEquals(classPlainTextCredentials, retrievedCredentials);
    }

    @Test
    public void testFindCredentialsInClassFromFile() throws IOException {
        PlainTextCredentialsChk plainTextCredentialsChk = new PlainTextCredentialsChk();
        String filePath = "C:\\Manula\\Academics\\Master in Cyber Security\\Software Quality\\Assignment\\final from May\\Software-Quality\\src\\main\\java\\com\\SENG4430\\WeightedMethods\\WeightedMethodsChk.java";
        String fileContents = readFileAsString(filePath);
        CtClass<?> ctClass = Launcher.parseClass(fileContents);
        List<String> foundCredentials = plainTextCredentialsChk.findCredentialsInClass(ctClass);

        Assert.assertFalse(foundCredentials.isEmpty());
        for (String credential : foundCredentials) {
            System.out.println("Found credential: " + credential);
        }
    }

    private String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }


    @Test
    public void testFindCredentialsInClassWithNoCredentials() {
        PlainTextCredentialsChk plainTextCredentialsChk = new PlainTextCredentialsChk();
        String testSourceCode = "public class TestClass {\n" +
                "    private int value = 123;\n" +
                "}";
        CtClass<?> ctClass = Launcher.parseClass(testSourceCode);

        List<String> foundCredentials = plainTextCredentialsChk.findCredentialsInClass(ctClass);
        Assert.assertTrue(foundCredentials.isEmpty());
    }

}

