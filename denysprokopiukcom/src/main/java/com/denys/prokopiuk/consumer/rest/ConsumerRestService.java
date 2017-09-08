/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer.rest;

/**
 *
 * @author Denys
 */
import com.denys.prokopiuk.consumer.Storage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/upload")
public class ConsumerRestService {

    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_XML)
    public Response consumeXml(String msg) {
        Storage storage = new Storage();
        storage.storeXmlToTmp(msg);
        //storage.storeToDatabase(msg);
        return Response.status(200).build();
    }

}
