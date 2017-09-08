/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.common;

import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

/**
 *
 * @author DProkopiuk
 */
public class WLRemoteConnectorImpl implements RemoteConnector {

    @Override
    public JMXConnector initJMXConnector(String hostname, String portString, String username, String password) {
        try {
            String protocol = "t3";
            Integer portInteger = Integer.valueOf(portString);
            int port = portInteger.intValue();
            String jndiroot = "/jndi/";
            String mserver = "weblogic.management.mbeanservers.domainruntime";
            JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port, jndiroot + mserver);
            Hashtable h = new Hashtable();
            h.put(Context.SECURITY_PRINCIPAL, username);
            h.put(Context.SECURITY_CREDENTIALS, password);
            h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, "weblogic.management.remote");
            return JMXConnectorFactory.connect(serviceURL, h);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
