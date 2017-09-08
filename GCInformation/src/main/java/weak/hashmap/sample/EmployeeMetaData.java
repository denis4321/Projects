/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weak.hashmap.sample;

import java.util.Date;

/**
 *
 * @author Администратор
 */
public class EmployeeMetaData {
    private Date date;

    public EmployeeMetaData(Date date) {
        this.date = date;
    }

    
    public EmployeeMetaData() {
        date = new Date();
    }
    
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "EmployeeMetaData{" + "date=" + date + '}';
    }
    
}
