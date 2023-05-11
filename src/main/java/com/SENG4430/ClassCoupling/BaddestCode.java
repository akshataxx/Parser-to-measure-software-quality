package com.SENG4430.ClassCoupling;

public class BaddestCode {
    public BaddestCode()
    {
        int b = 2;
    }

    public static int getThree()
    {
        return BadCode.getOne() + BadCode.getTwo(0);
    }
}
