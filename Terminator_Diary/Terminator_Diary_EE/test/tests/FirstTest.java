/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import com.conarhco.terminator.web.mobile.RestEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Конарх
 */
public class FirstTest {
    HashMap<String,String> map = new HashMap<String,String>();
    String query;

    public FirstTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        query = "first=TRAINING1\nsecond=TRAINING2\nthird=TRAINING3";
        map.put("first", "TRAINING1");
        map.put("second", "TRAINING2");
        map.put("third", "TRAINING3");
    }

    @After
    public void tearDown() {
        query="";
        map.clear();
    }

    @Test
    public void decodeTest(){
        Map curRez = RestEncoder.decode(query);
        Set<String> set = map.keySet();
        Iterator<String> iter=set.iterator();
        while(iter.hasNext()){
            String key=iter.next();
            assertEquals("Different values for the same key:(",map.get(key),curRez.get(key));
        }

    }

    @Test
    public void encodeTest(){
        Set<String> set = map.keySet();
        Iterator<String> iter=set.iterator();
        while(iter.hasNext()){
            String key=iter.next();
            String value=map.get(key);
            assertNotSame("Different values for the same key:(",query.indexOf(key+"="+value),-1);
            //assertEquals("Entity does not contains in a String:(" , query, key+"="value);
        }
        //assertTrue("Not equal strings",query,curRez);
    }

}