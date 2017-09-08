/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phantom.sample;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Администратор
 */
public class PhantomSample {
    
    public static void main(String[] args){
        ReferenceQueue<Book> referenceQueue = new ReferenceQueue<Book>();
        List<FinalizeBook> list = new ArrayList<FinalizeBook>();
        List<Book> books = new ArrayList<Book>();
        for(int i=0;i<10;i++){
            Book book=new Book(String.format("Title.%s", i));
            books.add(book);
            FinalizeBook fb = new FinalizeBook(book, referenceQueue);
            list.add(fb);
        }
        books = null;
        System.gc();
        
        for(PhantomReference fr:list){
            System.out.println(fr.isEnqueued());
        }
        
        Reference<? extends Book> inst;
        while((inst=referenceQueue.poll())!=null){
           ((FinalizeBook)(inst)).cleanup();
        }
    }
    
}
