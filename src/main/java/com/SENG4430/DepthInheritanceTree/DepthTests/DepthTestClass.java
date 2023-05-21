package com.SENG4430.DepthInheritanceTree.DepthTests;

public class DepthTestClass {

}

class Child1 extends DepthTestClass {
    // Child class 1
}

class Child2 extends DepthTestClass {
    // Child class 2
}

class GrandChild1 extends Child1 {
    // GrandChild class 1
}

class GrandChild2 extends Child1 {
    // GrandChild class 2
}

class GrandChild3 extends Child2 {
    // GrandChild class 3
}

class GreatGrandChild extends GrandChild2 {
    // GreatGrandChild class
}