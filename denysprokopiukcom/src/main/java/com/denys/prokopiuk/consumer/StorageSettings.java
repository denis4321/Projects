/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Denys
 */
public class StorageSettings {
    private Properties props;

    public StorageSettings() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("/configs/storage/storage.properties");) {
            props = new Properties();
            props.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getDatePattern() {
        return props.getProperty("date.pattern", "yyyy-MM-dd_HH-mm-ss");
    }

    public String getFileMask() {
        return props.getProperty("file.mask", "data_%s.xml");
    }

    public String getDirPath() {
        return props.getProperty("dir.path", System.getProperty("java.io.tmpdir"));
    }

}
