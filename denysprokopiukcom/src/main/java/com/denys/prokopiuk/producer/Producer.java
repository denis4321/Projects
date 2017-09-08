/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.producer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;


/**
 *
 * @author Denys
 */
public class Producer {
    private Logger logger = Logger.getLogger("app-logger");
    private ProducerSettings producerSettings;

    public Producer() {
        producerSettings = new ProducerSettings();
    }
    
    public Producer(ProducerSettings producerSettings) {
        this.producerSettings = producerSettings;
    }
    
    public ClientResponse produce(String url, String content){
        Client client = Client.create();
        if(producerSettings.needAuthentification()){
            client.addFilter(new HTTPBasicAuthFilter(producerSettings.getRestLogin(), producerSettings.getRestPassword()));
        }
        client.addFilter(new ClientFilter() {
            int retriesCount = 3;
            
            @Override
            public ClientResponse handle(ClientRequest cr) throws ClientHandlerException {
                 for(int i=0;i<retriesCount;i++){
                    try {
                        logger.info("Call # "+i);
                        Thread.sleep(10*1000);
                        ClientResponse resp=getNext().handle(cr);
                        logger.info("Call.rez= "+resp);
                        if(resp.getStatus()==200){
                            return resp;
                        }                        
                    } catch (Exception e) {
                       logger.error(e.getMessage(), e);
                    }
                }                
                throw new ClientHandlerException("Connection retries limit exceeded.");
            }
        });
        WebResource webResource = client.resource(url);
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, content);
    }
    
    
    public ClientResponse produce(String url){
        String tmpDir=producerSettings.getDirPath();
        File dir = new File(tmpDir);
        File[] files=dir.listFiles();
        if (files.length != 0) {
            try (InputStream in = new FileInputStream(files[0]);) {
                byte[] data = new byte[in.available()];
                in.read(data);
                String content = new String(data);
                return produce(url, content);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        throw new RuntimeException("There are no files in tmp folder");         
    }
    
    
    
    
    
}
