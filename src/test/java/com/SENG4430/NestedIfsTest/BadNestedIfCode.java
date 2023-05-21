package com.SENG4430.NestedIfsTest;

public class BadNestedIfCode {
    public static void main(String[] args) {
        int x = 10;
        int y = 5;
        int z = 2;
        int a = 8;
        int b = 12;

        if (x > y) {
            System.out.println("x is greater than y");

            if (x > z) {
                System.out.println("x is also greater than z");

                if (x > a) {
                    System.out.println("x is also greater than a");

                    if (x > b) {
                        System.out.println("x is also greater than b");

                        if (x > 15) {
                            System.out.println("x is also greater than 15");
                        } else {
                            System.out.println("x is not greater than 15");
                        }
                    } else {
                        System.out.println("x is not greater than b");
                    }
                } else {
                    System.out.println("x is not greater than a");
                }
            } else {
                System.out.println("x is not greater than z");
            }
        } else {
            System.out.println("x is not greater than y");
        }
    }
}
