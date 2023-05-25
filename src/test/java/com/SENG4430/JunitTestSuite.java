/*
 * Developer: Akshata Dhuraji
 * Program Name: JunitTestSuite.java
 * Description: Program to list down test suite classes to be executed
 * Reference used : https://www.guru99.com/create-junit-test-suite.html
 */
package com.SENG4430;
import com.SENG4430.ClassCouplingTest.ClassCouplingChkTest;
import com.SENG4430.ResponseForClassTest.ResponseForClassChkTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)                     // Specifies that this class is a test suite
@Suite.SuiteClasses({                     // Specifies the test classes to include in the suite
        TestApplicationTest.class,        // calls TestApplicationTest class for execution
        HalsteadComplexityChkTest.class,  // calls HalsteadComplexityChkTest class for execution
        FogIndexChkTest.class,             // calls FogIndexChkTest class for execution
        ClassCouplingChkTest.class,       // calls class coupling chk class for test exec
        ResponseForClassChkTest.class     // calls responseForClassChk for test exec
})
public class JunitTestSuite {
    // left blank as it is used only as a holder for the above annotations
}
