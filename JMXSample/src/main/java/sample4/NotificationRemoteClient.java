/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample4;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import remote.common.RemoteConnector;
import remote.common.WLRemoteConnectorImpl;

/**
 *
 * @author Администратор
 */
public class NotificationRemoteClient {
    
     public static void main(String[] args) throws Exception{
        RemoteConnector rc = new WLRemoteConnectorImpl();
        JMXConnector connector=rc.initJMXConnector("127.0.0.1", "7001", "root", "11111111");
        MBeanServerConnection server=connector.getMBeanServerConnection();
        ObjectName key = new ObjectName("notification:key=value");
        Attribute attribute = new Attribute("Limit", 3000);
        server.setAttribute(key, attribute);
        for(int i=0;i<4;i++){
            server.invoke(key, "doOperation", new Object[]{1000}, new String[]{"int"});
            Object value=server.getAttribute(key, "Status");
            System.out.println(String.format("Status=%s", value));
        }
    }
}
