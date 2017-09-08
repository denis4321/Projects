/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.processor;

import com.feed.startup.Constants;
import java.io.InputStream;
import java.util.Properties;

public class ProcessorUtils {

    public boolean needToRunOnServer() {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.PROPS_PATH);) {
            Properties props = new Properties();
            props.load(in);
            String val = String.valueOf(props.get(Constants.KEY_NEED_TO_RUN_ON_SERVER));
            return Boolean.parseBoolean(val);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
