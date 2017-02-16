package com.edus.apollo;

import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void setTest(){
        SortedSet<String> sortedSet = new TreeSet<>();
        Collections.addAll(sortedSet, "one two three four five six seven eight".split(" "));
        Iterator<String> it = sortedSet.iterator();
        String low = null;
        String high = null;
        for(int i =0; i<=6; i++){
            if(i ==3)
                low = it.next();
            if(i ==6) high = it.next();
            else it.next();
        }
        System.out.println("low:"+low+",high:"+high);
    }
}