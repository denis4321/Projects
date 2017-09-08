/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample2.boundary;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Администратор
 */
public class Employee {
    
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private int age;
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        support.firePropertyChange("age", this.age, age);
        this.age = age;
    }
    
    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
}
