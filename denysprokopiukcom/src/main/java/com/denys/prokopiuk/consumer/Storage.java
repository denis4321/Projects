/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer;

import com.denys.prokopiuk.consumer.mongo.DataModule;
import com.denys.prokopiuk.xml.data.Persons;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Denys
 */
public class Storage {
    
    private final DateFormat DATE_FORMAT;
    private StorageSettings storageSettings;
    
    public Storage() {
        storageSettings = new StorageSettings();
        DATE_FORMAT = new SimpleDateFormat(storageSettings.getDatePattern());
    }
    
    public void storeXmlToTmp(String xml) {
        try {
            String tmpFile = storageSettings.getDirPath();
            System.out.println(tmpFile);
            File currentFile = new File(tmpFile, String.format(storageSettings.getFileMask(), DATE_FORMAT.format(new Date())));
            try (OutputStream out = new FileOutputStream(currentFile)) {
                out.write(xml.getBytes());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void storeToDatabase(String xml) {
        try {
            JAXBContext ctx=JAXBContext.newInstance(Persons.class);
            Unmarshaller unmarshaller=ctx.createUnmarshaller();
            try(ByteArrayOutputStream out = new ByteArrayOutputStream();){
                out.write(xml.getBytes());
                InputStream in = new ByteArrayInputStream(xml.getBytes());
                Persons persons = (Persons) unmarshaller.unmarshal(in);
                DataModule dataModule = new DataModule();
                dataModule.save(persons);                
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
