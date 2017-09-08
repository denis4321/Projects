/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reference.queue;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 *
 * @author Администратор
 */
public class ReferenceQueueSample {
    
    public static void main(String[] args) throws Exception{
        Dog dog = new Dog("Kesha");
        ReferenceQueue<Dog> referenceQueue = new ReferenceQueue<Dog>();
        DogCleaner dogCleaner = new DogCleaner();
        WeakReference<Dog> weakReference = new DogWeakReference(dog, dogCleaner, referenceQueue);
        dog = null;
        System.gc();
        //Thread.sleep(1000);        
        
        DogWeakReference ref=(DogWeakReference) referenceQueue.remove();
        System.out.println(ref);
        ref.clean();
        Dog d=ref.get();
        System.out.println(d);
    }
    
}
