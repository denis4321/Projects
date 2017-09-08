/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample5;

import java.util.Hashtable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Администратор
 */
public class Test {
    
     public static void main(String[] args) {
        try {
            
            Hashtable h = new Hashtable();
            h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            h.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");            
            InitialContext ctx = new InitialContext(h);
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("myConnectionFactory");
            Destination queue = (Destination) ctx.lookup("myQueue");
            Connection c = cf.createConnection();
            Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TextMessage msg = session.createTextMessage();
            msg.setText("message");
            MessageProducer p = session.createProducer(queue);
            p.send(msg);
            System.out.println("Msg was published to ExceptionCenter");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
