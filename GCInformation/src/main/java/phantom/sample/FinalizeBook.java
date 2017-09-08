/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phantom.sample;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 *
 * @author Администратор
 */
public class FinalizeBook extends PhantomReference<Book>{

    public FinalizeBook(Book referent, ReferenceQueue<? super Book> q) {
        super(referent, q);
    }
    
    public void cleanup(){
        System.out.println("Finalize!!!");                
    }
}
