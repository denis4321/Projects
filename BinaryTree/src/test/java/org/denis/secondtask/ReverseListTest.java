/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.denis.secondtask;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Denis Prokopiuk
 *
 *   */

public class ReverseListTest {

    public ReverseListTest() {
    }

    @Test
    public void testReverseList() {
        List l = new ArrayList();
        ReverseList.reverseList(l);
        assertTrue("Empty list was not reversed", l.isEmpty());
        //checking list with even size
        for (int i=0; i<10; i++){
            l.add(i);
        }
        assertTrue("Size not even", l.size()%2==0);
        checkList(l);
        ReverseList.reverseList(l);
        //checking list with non-even size
        l.add(l.size());
        assertTrue("Size even", l.size()%2!=0);
        checkList(l);
    }

    private void checkList(List l){
        ReverseList.reverseList(l);
        for (int i=0; i<l.size(); i++){
           int pos = l.size()-1-i;
           assertEquals("Invalid element returned for "+pos, i, l.get(pos));
        }
    }

}
