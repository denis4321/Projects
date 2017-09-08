/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.concurrent.Future;
import javax.ejb.*;

/**
 *
 * @author Администратор
 */
@Stateless(mappedName = "my")
public class ProcessorImpl implements Processor {

    @Override
    @Asynchronous
    public Future<String> process() {
        long start=System.currentTimeMillis();
        while((System.currentTimeMillis()-start)<5000){}
        return new AsyncResult<String>("status."+(System.currentTimeMillis()-start));
    }

}
