package com.SENG4430.LengthOfIdentifiersTest;

import com.SENG4430.LengthOfIdentifiers.LengthOfIdentifiersChk;
import org.junit.Test;
import spoon.Launcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class LengthOfIdentifiersTest {
    @Test
    public void getAverageLengthOfIdentifiers() {
        Launcher launcher = new Launcher();
        launcher.addInputResource(
                "src\\test\\java\\com\\SENG4430\\LengthOfIdentifiersTest\\LongLengthIdentifiersCode.java");
        launcher.buildModel();


        LengthOfIdentifiersChk lengthOfIdentifiersChk = new LengthOfIdentifiersChk();
        lengthOfIdentifiersChk.check(launcher);
        final double averageValue = lengthOfIdentifiersChk.getAverageLengthOfIdentifiers().get("LongLengthIdentifiersCode");
        assertEquals("Length of Identifiers: \n" +
                "\t Class average scores: \n" +
                "\t\t Class Name: NestedIfExample\n" +
                "\t\t Value: 14.9", 14.9, averageValue);
    }

    @Test
    public void getLengthOfIdentifiersLessThanOrEqualToCutoff() {
        Launcher launcher = new Launcher();
        launcher.addInputResource(
                "src\\test\\java\\com\\SENG4430\\LengthOfIdentifiersTest\\LongLengthIdentifiersCode.java");
        launcher.buildModel();


        LengthOfIdentifiersChk lengthOfIdentifiersChk = new LengthOfIdentifiersChk();
        lengthOfIdentifiersChk.check(launcher);
        HashMap<String, List<String>> lengthOfIdentifiersLessThanOrEqualToCutoff =
                lengthOfIdentifiersChk.getLengthOfIdentifiersLessThanOrEqualToCutoff();
        ArrayList<String> identifiersLessThanOrEqualToCutoff =
                (ArrayList<String>) lengthOfIdentifiersLessThanOrEqualToCutoff.get("LongLengthIdentifiersCode");
        assertTrue(
                "Length of identifiers less than or equal to the cutoff: \n" +
                "\t\t Cut off Limit: 4\n" +
                "\t\t Class Name: LongLengthIdentifiersCode\n" +
                "\t\t Value(s): [main, args, r, er, bee]", (identifiersLessThanOrEqualToCutoff.contains("main")
                && identifiersLessThanOrEqualToCutoff.contains("args")
                && identifiersLessThanOrEqualToCutoff.contains("r")
                && identifiersLessThanOrEqualToCutoff.contains("er")
                && identifiersLessThanOrEqualToCutoff.contains("bee")
                && identifiersLessThanOrEqualToCutoff.size() == 5));
    }
}
