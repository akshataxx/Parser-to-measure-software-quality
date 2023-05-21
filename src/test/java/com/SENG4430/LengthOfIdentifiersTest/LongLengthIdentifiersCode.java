package com.SENG4430.LengthOfIdentifiersTest;

public class LongLengthIdentifiersCode {
    public static void main(String[] args) {
        int r = 7;
        int er = 9;
        int bee = 3;
        int numberOfStudentsInClass = 20;
        int averageScoreOfStudents = 85;
        int totalNumberOfAssignmentsCompletedByEachStudent = 10;
        int minimumPassingScore = 70;

        if (numberOfStudentsInClass > 0) {
            System.out.println("There are students in the class.");

            if (averageScoreOfStudents >= minimumPassingScore) {
                System.out.println("The average score of students is satisfactory.");

                if (totalNumberOfAssignmentsCompletedByEachStudent > 0) {
                    System.out.println("Assignments have been completed by each student.");

                    if (numberOfStudentsInClass <= 30) {
                        System.out.println("The class size is within the limit.");

                        if (averageScoreOfStudents >= 90) {
                            System.out.println("The average score of students is excellent.");

                            if (totalNumberOfAssignmentsCompletedByEachStudent >= 12) {
                                System.out.println("Each student has completed a substantial number of assignments.");

                                if (numberOfStudentsInClass == 20) {
                                    System.out.println("The class has the expected number of students.");

                                    if (averageScoreOfStudents > 95) {
                                        System.out.println("The average score of students is outstanding.");
                                    } else {
                                        System.out.println("The average score of students is very good.");
                                    }
                                } else {
                                    System.out.println("The class does not have the expected number of students.");
                                }
                            } else {
                                System.out.println("Each student has not completed a sufficient number of assignments.");
                            }
                        } else {
                            System.out.println("The average score of students is good.");
                        }
                    } else {
                        System.out.println("The class size exceeds the limit.");
                    }
                } else {
                    System.out.println("Assignments have not been completed by each student.");
                }
            } else {
                System.out.println("The average score of students is unsatisfactory.");
            }
        } else {
            System.out.println("There are no students in the class.");
        }
    }
}
