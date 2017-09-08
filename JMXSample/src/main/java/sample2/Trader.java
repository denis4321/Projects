/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample2;

import javax.management.AttributeChangeNotification;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

/**
 *
 * @author Администратор
 */
public class Trader implements TraderMBean, NotificationBroadcaster {

    private NotificationBroadcasterSupport support = new NotificationBroadcasterSupport();
    private long seqNumber;

    private String status;
    private int limit;
    private int sumOfPrice;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        support.sendNotification(new AttributeChangeNotification(this, seqNumber++, System.currentTimeMillis(), String.format("%s.%s-->%s", "Status", this.status, status), "Status", String.class.getName(), this.status, status));
        this.status = status;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void doOperation(int price) {
        if ((sumOfPrice + price) <= limit) {
            setStatus("PERMITTED");
            sumOfPrice += price;
        } else {
            setStatus("NOT_PERMITTED");
        }
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
        support.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        support.removeNotificationListener(listener);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        MBeanNotificationInfo info = new MBeanNotificationInfo(new String[]{Notification.class.getName()}, Notification.class.getName(), "Status change notification");
        return new MBeanNotificationInfo[]{info};
    }

}
