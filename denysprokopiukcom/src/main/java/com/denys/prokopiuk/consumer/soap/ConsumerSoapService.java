/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.denys.prokopiuk.consumer.soap;

import com.denys.prokopiuk.consumer.Storage;
import javax.jws.WebService;
import javax.jws.WebMethod;

/**
 *
 * @author Denys
 */
@WebService(serviceName = "ConsumerSoapService")
public class ConsumerSoapService {

    @WebMethod
    public void uploadXml(String xml) {
        Storage storage = new Storage();
        storage.storeXmlToTmp(xml);
        storage.storeToDatabase(xml);
    }
}
