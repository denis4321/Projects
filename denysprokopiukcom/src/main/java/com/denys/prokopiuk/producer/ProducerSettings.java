/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Администратор
 */
public class ProducerSettings {
    
    private Properties props;

    public ProducerSettings() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("/configs/rest_producer/producer.properties");) {
            props = new Properties();
            props.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getDirPath() {
        return props.getProperty("dir.path", System.getProperty("java.io.tmpdir"));
    }
    
    public boolean needAuthentification(){
        return Boolean.parseBoolean(props.getProperty("need.auth", "false"));
    }
    
    public String getRestPassword(){
        return props.getProperty("password", "");
    }
    
    public String getRestLogin(){
        return props.getProperty("login", "");
    }
    
    public int getAmountOfUrls(){
        return Integer.parseInt(props.getProperty("urls.amount", "0"));
    }
    
    public String getUrl(int index){
        return props.getProperty("url."+index, "");
    }
    
}
