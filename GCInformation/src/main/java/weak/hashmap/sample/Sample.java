/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weak.hashmap.sample;

import java.util.WeakHashMap;

/**
 *
 * @author Администратор
 */
public class Sample {
    
    public static void main(String[] args) throws InterruptedException{
        WeakHashMap<Employee, EmployeeMetaData> map = new WeakHashMap<Employee, EmployeeMetaData>();
        Employee emp = new Employee();
        map.put(emp, new EmployeeMetaData());
        
        EmployeeMetaData meta = map.get(emp);
        System.out.println(meta);
        emp=null;
        System.gc();
        Thread.sleep(1000);
        if(map.containsValue(meta)){
            System.out.println("Still contailns key Employee");
        }else{
            System.out.println("Key is gone");
        }
        
        
        
        //System.out.println(map.get(emp));
    }
    
}
