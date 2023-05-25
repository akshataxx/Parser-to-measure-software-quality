package com.SENG4430.Print;

import com.SENG4430.TestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class filePrintResults extends TestResult {
    public filePrintResults(String[] args) {}

    // Output string to the command line
    @Override
    public void create(LinkedList<String> results) {
        try {
            File myObj = new File("Results.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("Results.txt");
            for (String s : results) {
                myWriter.write(s);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}
