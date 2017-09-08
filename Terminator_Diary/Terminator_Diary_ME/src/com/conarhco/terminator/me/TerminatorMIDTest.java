package com.conarhco.terminator.me;

/*
 * MobileTest.java
 * JMUnit based test
 *
 * Created on 17.04.2011, 15:44:16
 */


import com.conarhco.terminator.me.TerminatorMID;
import java.util.Enumeration;
import java.util.Hashtable;
import jmunit.framework.cldc10.*;

/**
 * @author Конарх
 */
public class TerminatorMIDTest extends TestCase {
    String query;
    Hashtable map;

    public TerminatorMIDTest() {
        //The first parameter of inherited constructor is the number of test cases
        super(0,"MobileTest");
    }            

    public void test(int testNumber) throws Throwable {
        switch(testNumber){
            case 0:
                encodeTest();
                break;
            case 1:
                decodeTest();
            default:
                break;
        }
    }



    public void encodeTest(){
        Hashtable curRez = TerminatorMID.decode(query);
        Enumeration en=curRez.keys();
        while(en.hasMoreElements()){
            String key=(String) en.nextElement();
            String value=(String) map.get(key);
            assertNotEquals("Different values for the same key:(",query.indexOf(key+"="+value),-1);
            //assertEquals("Entity does not contains in a String:(" , query, key+"="value);
        }
    }

    public void decodeTest(){
        Hashtable curRez = TerminatorMID.decode(query);
        Enumeration en=curRez.keys();
        while(en.hasMoreElements()){
            String key=(String) en.nextElement();
            assertEquals("Different values for the same key:(",map.get(key),curRez.get(key));
        }
    }
}
