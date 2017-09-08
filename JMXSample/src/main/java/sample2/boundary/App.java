/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample2.boundary;

/**
 *
 * @author Администратор
 */
public class App {
    
    public static void main(String[] args){
        Employee emp = new Employee();
        EmployeeListener listener = new EmployeeListener();
        emp.addListener(listener);
        emp.setAge(28);
        emp.setAge(29);
        emp.setAge(40);
    }
    
}
