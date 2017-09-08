/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample2;

/**
 *
 * @author Администратор
 */
public interface TraderMBean {

    public String getStatus();

    public void setStatus(String status);

    public int getLimit();

    public void setLimit(int limit);

    public void doOperation(int price);
}
