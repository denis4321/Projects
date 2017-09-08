/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.denis.secondtask;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Denis Prokopiuk
 */
public class SimpleArrayListTest {
    private SimpleArrayList list = null;

    public SimpleArrayListTest() {
    }

    @Before
    public void setUp() {
        list = new SimpleArrayList();
    }

    @After
    public void tearDown() {
        list = null;
    }

    /**
     * Tests add-set-get methods
     * If we assume list as a black box which internal implementation is mostly unknown
     * (as it is for unit tests in real life), we can't test methods one by one because
     * we unaware of internal list realisation. Some thing such as get(-1) or get(size+1)
     * can be tested separately, but usually even these tests cant be proof of methods
     * incorrectness.
     * For instance, if we want to test method get, and get some assertion errors, the cause
     * might be that get method does not work as well as add method does not work - we can't
     * really say which one is bugged until we look on realization.
     * Therefore, it's much more efficient to test not the methods, but the key features,
     * which usually includes several method which work together.
     */
    @Test
    public void testAddGet() {
        try{
            list.get(0);
            fail("Exception not thorwn when getting behind list borders");
            //note thart this also assures that inital list is empty which is important for future work
        } catch (ArrayIndexOutOfBoundsException ex){
            //ok
        }
        try {
            list.get(-1);
            fail("Exception not thorwn when getting behind list borders");
        } catch (ArrayIndexOutOfBoundsException ex) {
            //ok
        }
        int size = (int)(SimpleArrayList.STEP*1.5);
        for (int i=0; i<size; i++){
            list.add(i, i);
        }
        list.add(SimpleArrayList.STEP, -1);
        for (int i=0; i<size+1; i++){
            int val = -1;//i==STEP
            if (i<SimpleArrayList.STEP){
                val = i;
            } else
            if (i>SimpleArrayList.STEP){
                val = i-1;
            }
            assertEquals("Invalid value returned for "+i+" position", val, list.get(i));
        }
        try{
            list.add(size+3, -1);
            fail("Exception not thorwn when adding behind list borders");
        } catch (ArrayIndexOutOfBoundsException ex){
            //ok
        }
        try {
            list.add(-1, -1);
            fail("Exception not thorwn when adding behind list borders");
        } catch (ArrayIndexOutOfBoundsException ex) {
            //ok
        }
    }

    @Test
    public void testSet(){
        try{
            list.set(0, -1);
            fail("Exception not thorwn when setting behind list borders");
            //note thart this also assures that inital list is empty which is important for future work
        } catch (ArrayIndexOutOfBoundsException ex){
            //ok
        }
        try {
            list.set(-1, -1);
            fail("Exception not thorwn when setting behind list borders");
        } catch (ArrayIndexOutOfBoundsException ex) {
            //ok
        }
        int size = SimpleArrayList.STEP/2;
        for (int i=0; i<size; i++){
            list.add(i, 0);
        }
        for (int i=0; i<size; i++){
            int val = (Integer)list.get(i);
            int old = (Integer)list.set(i, -i);
            assertEquals("Invalid value returned for "+i+" position", val, old);
        }
        for (int i=0; i<size; i++){
            int val = (Integer)list.get(i);
            assertEquals("Invalid value returned for "+i+" position", -i, val);
        }
    }

    @Test
    public void testIterator(){
        int size = SimpleArrayList.STEP;
        for (int i=0; i<size; i++){
            list.add(i, i);
        }
        Iterator itr = list.iterator();
        try {
            itr.remove();
            fail("Exception not thorwn when trying remove before next");
        } catch (IllegalStateException ex) {
            //ok
        }
        int size2 = size/2;
        for (int i=0; i<size; i++){
            assertTrue("Iterator does not have any elements while it has to", itr.hasNext());
            int val = (Integer)itr.next();
            if (i==size2){
                itr.remove();
                try {
                    itr.remove();
                    fail("Exception not thorwn when trying duplicate remove");
                } catch (IllegalStateException ex) {
                    //ok
                }
                for (int j=size2; j<size-1; j++){
                    assertEquals("Invalid value returned for "+i+" position", j+1, list.get(j));
                }
                try {
                    list.get(size);
                    fail("List stil has last element after element was removed");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    //ok
                }
                try {
                    itr.remove();
                    fail("Exception not thorwn when trying duplicate remove");
                } catch (IllegalStateException ex) {
                    //ok
                }
            }
            assertEquals("Invalid value returned for "+i+" position", i, val);
        }
        assertFalse("Iterator still has elements", itr.hasNext());
        try {
            itr.next();
            fail("Exception not thorwn when trying to get unexisting elements");
        } catch (NoSuchElementException ex) {
            //ok
        }
        itr = list.iterator();
        for (int i=0; i<size2; i++){
            itr.next();
        }
        list.add(size2, -1);
        checkConcurrent(itr);
        itr = list.iterator();
        for (int i=0; i<size2; i++){
            itr.next();
        }
        list.set(size2, -2);
        checkConcurrent(itr);
        itr = list.iterator();
        Iterator itr2 = list.iterator();
        for (int i=0; i<size2; i++){
            itr.next();
            itr2.next();
        }
        itr2.remove();
        checkConcurrent(itr);
        try{
            itr2.hasNext();
            itr2.next();
            itr2.remove();
        } catch (ConcurrentModificationException ex){
            fail("Exception shoudn't be thrown for iterator which changes list via it's own remove");
        }
    }

    private void checkConcurrent(Iterator itr){
        try {
            itr.hasNext();
            fail("ConcrurrentModifgicationException not thorwn");
        } catch (ConcurrentModificationException ex) {
            //ok
        }
        try {
            itr.next();
            fail("ConcrurrentModifgicationException not thorwn");
        } catch (ConcurrentModificationException ex) {
            //ok
        }
        try {
            itr.remove();
            fail("ConcrurrentModifgicationException not thorwn");
        } catch (ConcurrentModificationException ex) {
            //ok
        }
    }

    @Test
    public void testEqualsHashCode(){
        int size = SimpleArrayList.STEP;
        SimpleArrayList list2 = new SimpleArrayList();
        for (int i=0; i<size; i++){
            list.add(i, i);
            list2.add(i, i);
        }
        assertTrue("List with equal elements are not equal", list.equals(list2));
        assertTrue("List with equal elements has different hash codes", list.hashCode()==list2.hashCode());
        list2.add(size, size);
        assertFalse("List with different elements are  equal", list.equals(list2));
        assertFalse("List with different elements has same hash codes", list.hashCode()==list2.hashCode());
        assertFalse("List is equal to some other obejct", list.equals(new Object()));
        assertFalse("List is equal to null", list.equals(null));
    }

    @Test
    public void testIndexOf(){
        int size = SimpleArrayList.STEP;
        int target = size/2;
        assertEquals("found target in empty list", -1, list.indexOf(target));
        assertEquals("null found in empty list", -1, list.indexOf(null));
        for (int i=0; i<size; i++){
            list.add(i, i);
        }
        assertEquals("Target not found", target, list.indexOf(target));
        assertEquals("null found", -1, list.indexOf(null));
        list.set(target, null);
        assertEquals("Invalid target found", -1, list.indexOf(target));
        assertEquals("null not found", target, list.indexOf(null));
    }


}
