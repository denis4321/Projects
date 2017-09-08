package org.denis.secondtask;

import java.util.List;


/**
 * Test task
 */

public class ReverseList
{
    /**
     * reverse the list given as the param
     * !!! don't change the signature of the method
     * @param list to be reversed
     */
    public static void reverseList(List list)
    {
        for (int i=0; i<list.size()/2; i++){
            Object tmp = list.get(i);
            list.set(i, list.get(list.size()-1-i));
            list.set(list.size()-1-i, tmp);
        }
    }
}
