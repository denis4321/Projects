/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.concurrent.Future;
import javax.ejb.Remote;

/**
 *
 * @author Администратор
 */
@Remote
public interface Processor {

    public Future<String> process();

}
