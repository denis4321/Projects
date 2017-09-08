/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertor;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Denis
 */
public class ConvertorImpl<T> implements Convertor<T> {

    @Override
    public String convertToJSON(T t) {
        JSONObject object = new JSONObject(t);
        return object.toString();
    }

    @Override
    public String convertToJSON(String key, List<T> t) {
        JSONObject array = new JSONObject();
        array.put(key, t);
        return array.toString();
    }
    

}
