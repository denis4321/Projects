/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample1;

/**
 *
 * @author Администратор
 */
public class Trader implements TraderMBean {

    private String status;
    private int limit;
    private int sumOfPrice;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
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

}
