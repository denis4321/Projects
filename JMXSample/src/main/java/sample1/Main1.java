/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample1;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 *
 * @author Администратор
 */
public class Main1 {
    
    public static void main(String[] args) throws Exception {
        MBeanServer server = MBeanServerFactory.createMBeanServer();
        ObjectName key = new ObjectName("mdomain:key=value");
        Trader trador = new Trader();
        server.registerMBean(trador, key);
        
        //Remote reflection
        Object limitValue=server.getAttribute(key, "Limit");
        Object statusValue=server.getAttribute(key, "Status");
        System.out.println(String.format("Limit=%s", limitValue));
        System.out.println(String.format("Status=%s", statusValue));
        Object returnValue = server.invoke(key, "doOperation", new Object[]{1000}, new String[]{"int"});
        System.out.println(String.format("ReturnValue=%s", returnValue));
        
        Attribute attr = new Attribute("Limit", 3000);
        server.setAttribute(key, attr);
       // trador.setLimit("300");
        limitValue=server.getAttribute(key, "Limit");
        System.out.println(String.format("Limit.MBeanServer=%s", limitValue));
        limitValue=trador.getLimit();
        System.out.println(String.format("Limit.Object=%s", limitValue));
        
       
        for(int i=0;i<4;i++){
            server.invoke(key, "doOperation", new Object[]{1000}, new String[]{"int"});
            statusValue=server.getAttribute(key, "Status");
            System.out.println(String.format("Status=%s", statusValue));
        }
    }
    
}
