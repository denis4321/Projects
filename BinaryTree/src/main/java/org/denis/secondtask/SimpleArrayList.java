package org.denis.secondtask;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 * 
 * <p>Company: </p>
 *
 * @author Denis Prokopiuk
 * @version 1.0
 */


import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SimpleList implements some methods of List interface
 *
 * from implementer: the implementation is not synchronized,
 * beacuse it was'n set as nesessary task
 *
 */
public class SimpleArrayList
{
    //array to store elements of the list
    Object[] data;
    private int size =0;
    public static int STEP = 10;
    private long version = 0;


    public SimpleArrayList(){
        this.data = new Object[STEP];
    }


    /**
     * Specified by: the same method of java.util.List
     * @param index
     * @return
     */
    public Object get(int index){
        indexCheck(index, false);
        return data[index];
    }

    /**
     * Specified by: the same method of java.util.List
     * @param index
     * @param obj
     */
    public Object set(int index, Object obj){
       indexCheck(index, false);
       Object old=get(index);
       incVersion();
       data[index]=obj;
       return old;
    }

    /**
     * Specified by: the same method of java.util.List
     * @param index
     * @param element
     */
    public void add(int index, Object element){
        indexCheck(index, true);
        incVersion();
        for (int i=size-1; i>=index; i--){
            data[i+1]=data[i];
        }
        data[index] = element;
        size++;
        if (size>=data.length){
             Object[] old=data;
             data =new Object[data.length+STEP];
             System.arraycopy(old,0,data,0,old.length);
        }
     }

    /**
     * Specified by: the same method of java.util.List
     * @return
     */
    public Iterator iterator(){
       return new Iterator(){
           private int index=0;
           private boolean removable = false;
           private long version;

           {
                this.version = getVersion();
           }

           public boolean hasNext() {
               if (version!=getVersion()) throw new ConcurrentModificationException();
               return index<size;
           }

           public Object next() {
               if (!hasNext()) throw new NoSuchElementException();
               if (version!=getVersion()) throw new ConcurrentModificationException();
               removable = true;
               return data[index++];
           }

           public void remove() {
               if (!removable) throw new IllegalStateException("next() hasn't been called yet or remove was already called fo current element!");
               if (version!=getVersion()) throw new ConcurrentModificationException();
               incVersion();
               this.version = SimpleArrayList.this.getVersion();
               System.arraycopy(data, index, data, index-1, size-index);
               size--;
               index--;
               removable = false;
           }
       };
    }

    /**
     * Specified by: the same method of java.util.List
     * @return
     */
    @Override
    public int hashCode(){
        return Arrays.hashCode(data);
    }

    /**
     * Specified by: the same method of java.util.List
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj==null)return false;
        if (obj==this)return true;
        if (obj instanceof SimpleArrayList){
            return Arrays.equals(data, ((SimpleArrayList)obj).data);
        }
        else return false;
    }

    /**
     * Specified by: the same method of java.util.List
     * @return
     */
    public int indexOf(Object o){
       for (int i = 0; i < size; i++)
           if (data[i]==null){
               if (o==null) return i;
           } else
           if (data[i].equals(o)) return i;
        return -1;
    }

    private void indexCheck(int index, boolean allowsAdd){
        if ((allowsAdd ? index > size : index>=size)||index<0) throw new ArrayIndexOutOfBoundsException(index);
    }

    private void incVersion(){
        version++;
    }

    private long getVersion(){
        return version;
    }
}



