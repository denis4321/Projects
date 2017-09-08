/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample2.boundary;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Администратор
 */
public class EmployeeListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(String.format("Employee.%s.%s-->%s", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()));
    }

}
