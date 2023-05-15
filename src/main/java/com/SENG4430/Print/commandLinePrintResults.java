/*
 * Developer: Akshata Dhuraji
 * Program Name: commandLinePrintResults
 * Description: This program prints the results of various complexity metrics on command line
 * i.e. also the default print option
 */
package com.SENG4430.Print;

import com.SENG4430.TestResult;

import java.util.LinkedList;

public class commandLinePrintResults  extends TestResult {
    public commandLinePrintResults(String[] args) {}

    // Output string to the command line
    @Override
    public void create(LinkedList<String> results) {
        for (String s : results) {
            System.out.println(s);
        }
    }
}
