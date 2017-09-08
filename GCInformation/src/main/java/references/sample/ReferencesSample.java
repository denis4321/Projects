/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package references.sample;

import java.lang.ref.WeakReference;

/**
 *
 * @author Администратор
 */
public class ReferencesSample {

    public static void main(String[] args) throws Exception {
        Person strongPerson = new Person("Denis", 29);
        System.out.println("StrongPerson="+strongPerson);
        WeakReference<Person> weakPerson = new WeakReference<Person>(new Person("Piter", 40));
        System.out.println("WeakPerson="+weakPerson.get());
       
        
        Person strongPerson1 = new Person("P", 1);
        WeakReference<Person> weakPerson1 = new WeakReference<Person>(strongPerson1);
        strongPerson=null;
        System.gc();
        Thread.sleep(5000);
        System.gc();
        System.out.println(weakPerson1.get());
        
        Person strongPerson2 = null;
        WeakReference<Person> weakPerson2 = new WeakReference<Person>(strongPerson2);
        strongPerson2=new Person("P", 2);
        System.gc();
        Thread.sleep(5000);
        System.gc();
        System.out.println(weakPerson2.get());
    }

}
