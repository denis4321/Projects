/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conarhco.terminator.web.mobile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author Конарх
 */
public class RestEncoder {
    public static String encode(Map<String, String> values){
        StringBuilder sb = new StringBuilder();
        Set<String> set = values.keySet();
        Iterator<String> iter=set.iterator();
        while(iter.hasNext()){
            String key=iter.next();
            sb.append(key+"="+values.get(key)+"\n");
        }
        return sb.toString();
    }

    public static Map<String, String> decode(String query){
        Map<String,String> map = new HashMap<String,String>();
        StringTokenizer st = new StringTokenizer(query,"\n");
        while(st.hasMoreElements()){
            String line=(String) st.nextElement();
            int separatorIndex = line.indexOf("=");
            String key=line.substring(0,separatorIndex);
            String value=line.substring(separatorIndex+1, line.length());
            map.put(key, value);
        }
        return map;
    }
}
