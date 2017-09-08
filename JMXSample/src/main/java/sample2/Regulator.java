/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample2;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 *
 * @author Администратор
 */
public class Regulator implements NotificationListener {

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println(String.format("Regulator.Notification=%s", notification.getMessage()));
    }
    
}
