/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.common;

import javax.management.remote.JMXConnector;

/**
 *
 * @author DProkopiuk
 */
public interface RemoteConnector {
    
    public JMXConnector initJMXConnector(String hostname, String portString, String username, String password);
            
}
