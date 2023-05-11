package com.SENG4430.ClassCoupling;

public class Test
{
    public static void main(String[] args)
    {
        bruh();

        int a = BadCode.getTwo(BadCode.getOne()) + 2;

        BadCode b = new BadCode();

        BaddestCode c = new BaddestCode();

        for (boolean i = true; i == true; i = !i)
        {
            System.out.println(i);
        }

        // Calculate class coupling
        int classCoupling = CouplingMetricCalculator.calculateClassCoupling("C:/Users/ewart/OneDrive/Documents/GitHub/backend/Software-Quality/src/main/java/com/SENG4430/ClassCoupling/Test.java");

        // Print result
        System.out.println("Class coupling: " + classCoupling);
    }

    public static void bruh()
    {

    }

    private Integer skipper;
}
