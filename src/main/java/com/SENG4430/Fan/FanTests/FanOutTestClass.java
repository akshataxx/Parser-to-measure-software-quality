package com.SENG4430.Fan.FanTests;

public class FanOutTestClass {
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
        FanOutTestClass testClass = new FanOutTestClass();
        testClass.methodE();
    }
}
