package com.SENG4430.ClassCoupling.TestFiles;

public class BaddestCode {
    public BaddestCode()
    {
        int b = 2;
    }
    public BaddestCode(int a) { int b = a; }

    public static int getFour(char a, char b) { return getThree() + 1; }

    public static int getThree()
    {
        return BadCode.getOne() + BadCode.getTwo(0, 1);
    }
}
