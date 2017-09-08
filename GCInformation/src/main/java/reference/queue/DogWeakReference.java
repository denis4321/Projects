/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reference.queue;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 *
 * @author Администратор
 */
public class DogWeakReference extends WeakReference<Dog> {

    private DogCleaner cleaner;
    private Dog dog;

    public DogWeakReference(Dog referent, DogCleaner cleaner, ReferenceQueue<? super Dog> q) {
        super(referent, q);
        this.cleaner = cleaner;
       // this.dog=referent;
    }

    public void clean() {
        cleaner.clean();
    }
    
    

}
