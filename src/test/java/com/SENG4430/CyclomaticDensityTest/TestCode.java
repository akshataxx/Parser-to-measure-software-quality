package com.SENG4430.CyclomaticDensityTest;

public class TestCode {
    // there are a total of 44 method lines with 9 outside of the loops (35/44 = 80%)

    // statements are automatically folded out when evaluated
    // this is 10 lines with 3 lines out of the loop (70% within the loop)
    private void ifCheck()
    {
        int a = 1;
        int b = 2;
        int c = 3;
        if(true && true || true && true || true && true || true && true || true && true || true){
            if (true){if(true){int i = 0;}}}
    }

    // statements are automatically folded out when evaluated
    // this is 9 lines with 3 lines out of the loop (67% within the loop)
    private void forCheck(){
        int a = 1;
        int b = 2;
        int loop = 3;
        for (int i=0; i < loop; i++){
            for(int p=0; p < loop; p++){for(int q=0; q < loop; q++){}}
        }
    }

    // statements are automatically folded out when evaluated
    // this is 12 lines with 1 line out of the loop (92% within the loop)
    private void whileCheck(){
        int i = 0;
        while(i<3 && i<3 || i<3 && i<3 || i<3 && i<3 || i<3 && i<3 || i<3 && i<3 || i<3){
            int p = 1;
            int q = 1;
            while (p < 3){while(q < 3){i++; p++; q++;}}
        }
    }

    // statements are automatically folded out when evaluated
    // this is 13 lines with 2 lines out of the loop (85% within the loop)
    private void caseCheck(){
        int a = 1;
        int i=0;
        switch(i){
            default:
                switch(i){default: switch(i){default: break;}}break;
        }
    }
}
