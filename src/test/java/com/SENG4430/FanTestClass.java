/*****************************************************************************************************
 Student: Daniel Beiers
 UID: c3039134
 Course: SENG4430 Software Quality
 Assessment: Assignment 2
 ****************************************************************************************************/

package com.SENG4430;

public class FanTestClass {
    public void methodA() {
        methodB();
        methodC();
        methodD();
    }

    public void methodB() {
        // Method B implementation
    }

    public void methodC() {
        // Method C implementation
    }

    public void methodD() {
        methodB();
    }

    public void methodE() {
        methodA();
        methodD();
    }

    public static void main(String[] args) {
        FanTestClass testClass = new FanTestClass();
        testClass.methodE();
    }
}
