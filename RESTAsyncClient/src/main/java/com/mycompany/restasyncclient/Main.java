/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restasyncclient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.internal.util.collection.Values;

/**
 *
 * @author Администратор
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ClientBuilder.newClient();
        WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:8080/RESTAsyncSample-web/webresources/v1/");
        final AsyncInvoker asyncInvoker = webTarget.request().async();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Future<String> response = asyncInvoker.get(String.class);
                    while (response.isDone()) {
                    }
                    System.out.println("Response form Server:"+response.get());
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
        Thread.sleep(5000);
        System.out.println("Main Thread is completed!");
    }

}
