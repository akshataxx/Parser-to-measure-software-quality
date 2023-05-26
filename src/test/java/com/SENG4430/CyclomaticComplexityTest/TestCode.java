package com.SENG4430.CyclomaticComplexityTest;

public class TestCode {
    // total complexity should be 33
    private void ifCheck()
    {
        // checks if it handles if and how it handles nested if
        // checks if it handles if on same line
        // should return (13) for 3 if statements, and 10 AND/OR decisions
        if(true && true || true && true || true && true || true && true || true && true || true){
            if (true){if(true){}}}
    }

    private void forCheck(){
        // checks if for works how it handles nested for
        // checks if it handles for on same line
        // should return (3) for 3 for loops
        int loop = 3;
        for (int i=0; i < loop; i++){
            for(int p=0; p < loop; p++){for(int q=0; q < loop; q++){}}
        }
    }

    private void whileCheck(){
        // checks if while statement works and how it handles nested while
        // checks how it handles multiple statements in same decision
        // checks if it handles while on same line
        // should return (13) for 3 while loops and 10 decisions
        int i = 0;

        while(i<3 && i<3 || i<3 && i<3 || i<3 && i<3 || i<3 && i<3 || i<3 && i<3 || i<3){
            int p = 1;
            int q = 1;
            while (p < 3){while(q < 3){i++; p++; q++;}}
        }
    }

    private void caseCheck(){
        // checks how it handles nested case
        // checks if it handles switch on same line
        // should return (3) for 3 switch cases
        int i=0;
        switch(i){
            default:
                switch(i){default: switch(i){default: break;}}break;
        }
    }
}
