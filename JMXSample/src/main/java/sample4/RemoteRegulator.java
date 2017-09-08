/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample4;

import sample3.*;
import java.io.IOException;
import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import remote.common.RemoteConnector;
import remote.common.WLRemoteConnectorImpl;
import sample2.Regulator;

/**
 *
 * @author Администратор
 */
public class RemoteRegulator {

    public static void main(String[] args) throws Exception {
        RemoteConnector rc = new WLRemoteConnectorImpl();
        JMXConnector connector = rc.initJMXConnector("127.0.0.1", "7001", "root", "11111111");
        MBeanServerConnection server = connector.getMBeanServerConnection();
        ObjectName key = new ObjectName("notification:key=value");
        Regulator regulator = new Regulator();
        server.addNotificationListener(key, regulator, null, null);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
