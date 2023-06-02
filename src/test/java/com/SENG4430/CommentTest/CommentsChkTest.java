package com.SENG4430.CommentTest;

import com.SENG4430.Comments.CommentsChk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentsChkTest {

    private CommentsChk commentsChk;
    private Launcher launcher;
    String testClassPath = "src/test/java/com/SENG4430/CommentTest/CommentTestClass.java";

    @BeforeEach
    void setUp() {
        commentsChk = new CommentsChk();
        launcher = new Launcher();
        launcher.addInputResource(testClassPath);
        launcher.buildModel();
    }

    @Test
    void commentChkLauncher_AddsCommentsToLinkedList() {
        commentsChk.commentChkLauncher(launcher);
        LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> comments = commentsChk.getComments();
        assertEquals(1, comments.size());

        Map.Entry<String, LinkedList<Map<String, Integer>>> classEntry = comments.getFirst();
        String className = classEntry.getKey();
        LinkedList<Map<String, Integer>> classComments = classEntry.getValue();

        assertEquals("CommentTestClass", className);

        assertEquals(2, classComments.size());
        Map<String, Integer> classComment = classComments.getFirst();
        assertEquals(1, classComment.get("CommentTestClass"));

        assertEquals(2, classComments.size());
        Map<String, Integer> methodComment = classComments.getLast();
        assertEquals(1, methodComment.get("methodA"));
    }

    @Test
    void measureMethodComments_ReturnsMethodComments() {
        CtClass<?> classObject = Query.getElements(launcher.getFactory(),
                new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        Map<String, Integer> methodComments = CommentsChk.measureMethodComments(classObject);
        assertEquals(1, methodComments.get("methodA"));
        assertEquals(1, methodComments.get("methodB"));
        assertEquals(1, methodComments.get("methodB"));
    }
    @Test
    void measureClassComments_ReturnsClassCommentsCount() {
        CtClass<?> classObject = Query.getElements(launcher.getFactory(),
                new TypeFilter<CtClass<?>>(CtClass.class)).get(0);
        Map<String, Integer> classComments = commentsChk.measureClassComments(classObject);
        assertEquals(1, classComments.get("CommentTestClass"));
    }

    @Test
    void getComments_ReturnsLinkedListOfComments() {
        LinkedList<Map.Entry<String, LinkedList<Map<String, Integer>>>> comments = new LinkedList<>();

        LinkedList<Map<String, Integer>> classComments = new LinkedList<>();
        Map<String, Integer> classComment = Map.of("CommentTestClass", 1);
        classComments.add(classComment);

        Map<String, Integer> methodComment = Map.of("methodA", 1,"methodB", 1,"main", 2);

        classComments.add(methodComment);

        comments.add(Map.entry("CommentTestClass", classComments));

        commentsChk.commentChkLauncher(launcher);
        assertEquals(comments, commentsChk.getComments());
    }
}
