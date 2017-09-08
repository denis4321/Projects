/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 *
 * @author Администратор
 */
@ManagedBean
@Path("v1")
public class MyEndpoint {

    //@EJB
  //  private Processor processor;

    @GET
    public void getText(@Suspended final AsyncResponse asyncResponse) {
        System.out.println("#####Start#####");
        try {
            //Future<String> f=processor.process();
            long start = System.currentTimeMillis();
            while((System.currentTimeMillis()-start)<15000){}            
            asyncResponse.resume("Result."+(System.currentTimeMillis()-start));
        } catch (Exception ex) {
            asyncResponse.resume(ex);
        }
        System.out.println("#####Finish#####");
    }

}
