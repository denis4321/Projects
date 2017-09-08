/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author DProkopiuk
 */
@ApplicationScoped
@ServerEndpoint("/export")
public class SingleEndpoint {
    
    private SingleSessionHandler singleSessionHandler = SingleSessionHandler.getInstance();
    
    @OnOpen
    public void init(Session session) {    
        System.out.println("Init");
        singleSessionHandler.addSession(session);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("Message="+message);
        singleSessionHandler.makeExport(message, session);                    
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnClose
    public void destroy(Session session) {
        singleSessionHandler.removeSession(session);
    }

    
}
