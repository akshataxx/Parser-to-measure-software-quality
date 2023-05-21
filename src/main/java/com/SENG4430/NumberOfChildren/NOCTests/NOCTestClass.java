package com.SENG4430.NumberOfChildren.NOCTests;

public class NOCTestClass {

}

// Child class 1
class Child1 extends NOCTestClass {
}

// Child class 2
class Child2 extends NOCTestClass {
}
// Child class 3
class Child3 extends NOCTestClass {
}
// GrandChild class 1
class GrandChild1 extends Child2 {
}

// GrandChild class 2
class GrandChild2 extends Child2 {
}
// GrandChild class 3
class GrandChild3 extends Child3 {
}

class GreatGrandChild extends GrandChild2 {
    // GreatGrandChild class
}