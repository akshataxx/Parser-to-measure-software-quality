package com.SENG4430.ClassCouplingTest.TestFiles;

public class Test
{
    public static void main(String[] args)
    {
        bruh();

        int a = BadCode.getTwo(BadCode.getOne(), 1) + 2;

        BadCode b = new BadCode();

        BaddestCode c = new BaddestCode();

        for (boolean i = true; i == true; i = !i)
        {
            //System.out will count as 2 class dependencies
            //the standard syntax just abstracts this
            //java.lang.System.out and java.io.PrintStream
            System.out.println(i);
        }

        // Calculate class coupling
        int classCoupling = CouplingMetricCalculator.calculateClassCoupling("C:/Users/ewart/OneDrive/Documents/GitHub/backend/Software-Quality/src/main/java/com/SENG4430/ClassCoupling/Test.java");

        // Print result
        System.out.println("Class coupling: " + classCoupling);
    }

    public static Integer bruh()
    {
        return Integer.valueOf(500);
    }

    private Integer skipper;
}
