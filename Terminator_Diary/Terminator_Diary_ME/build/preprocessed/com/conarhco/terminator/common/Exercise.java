/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.common;

import com.conarhco.terminator.me.ExerciseList;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/**
 * Упраженение
 * @author Конарх
 * TODO: Сделать екьюалс по имени
 */
public class Exercise {
    private int counter;
    private ExerciseList exList;
    private String name;//Название
    private int type;//Тип (базовое, изолированное)
    private int rest;//Отдых между подходами в секундах
    private ExerciseSet[] sets;//подходы
    private boolean techCorrect = false;//Коррекция техники
    private boolean weightCorret = false;//Коррекция веса
    public static final int BASE = 1;
    public static final int ISOLATE = 2;

    public Exercise(String name, int type, ExerciseSet[] sets, int rest) {
        this.name = name;
        this.type = type;
        this.sets = sets;
        this.rest = rest;
    }

    public boolean isTechCorrect() {
        return techCorrect;
    }

    public void setTechCorrect(boolean techCorrect) {
        this.techCorrect = techCorrect;
    }

    public boolean isWeightCorret() {
        return weightCorret;
    }

    public void setWeightCorret(boolean weightCorret) {
        this.weightCorret = weightCorret;
    }

    public String getName() {
        return name;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getRest() {
        return rest;
    }

    public ExerciseSet[] getSets() {
        return sets;
    }

    public int getType() {
        return type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public ExerciseList getExList() {
        return exList;
    }

    public void setExList(ExerciseList exList) {
        this.exList = exList;
    }

    public void setCounter(int counter){
        this.counter=counter;
    }

    public int getCounter(){
        return counter;
    }

   /* public void save(){
         try {
            RecordStore store = RecordStore.openRecordStore(name, true);
            //RecordEnumeration en1 = store.enumerateRecords(null, null, false);
           /* while (en1.hasNextElement()) {
                int j = en1.nextRecordId();
                store.deleteRecord(j);
            }*/
            //store.addRecord(name.getBytes(), 0, name.getBytes().length);
     /*       String s=type+"";
            store.addRecord(s.getBytes(), 0,s.getBytes().length);
            s=this.techCorrect+"";
            store.addRecord(s.getBytes(), 0,s.getBytes().length);
            s=this.weightCorret+"";
            store.addRecord(s.getBytes(), 0,s.getBytes().length);
            s=rest+"";
            store.addRecord(s.getBytes(), 0,s.getBytes().length);
            for(int i=0;i<this.sets.length;i++){
                s=sets[i].getRepeats()+"";
                store.addRecord(s.getBytes(), 0,s.getBytes().length);
                s=sets[i].getWeight()+"";
                store.addRecord(s.getBytes(), 0,s.getBytes().length);
            }
            RecordEnumeration en1 = store.enumerateRecords(null, null, false);
             while (en1.hasNextElement()) {
                int j = en1.nextRecordId();
                System.out.println("                  In this exercise I have seen field: "+new String(store.getRecord(j)));
            }


            store.closeRecordStore();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void load(String exName){
            name=exName;
            Vector vector = new Vector();
            try {
                RecordStore store = RecordStore.openRecordStore(exName, true);

                if (store.getNumRecords() != 0) {
                   System.out.println("Exercise " + exName+ " load size = "+store.getNumRecords());
                  int repeats=1;
                  double weights=1;
                   for (RecordEnumeration en = store.enumerateRecords(null, null, false); en.hasNextElement();) {
                        int id = en.nextRecordId();
                        String  str=new String(store.getRecord(id));
                        
                        switch (id){
                            case 1:
                               setType(Integer.parseInt(str));
                               break;
                            case 2:
                                this.setTechCorrect(getBooleanValue(str));
                                break;
                            case 3:
                                this.setWeightCorret(getBooleanValue(str));
                                break;
                            case 4:
                                this.setRest(Integer.parseInt(str));
                                break;
                            default:
                            if(id%2==0){
                                String strOld=new String(store.getRecord(id-1));
                                repeats=Integer.parseInt(strOld);
                                weights=Double.parseDouble(str);
                                vector.addElement(new ExerciseSet(repeats,weights));
                            }
                        }
                    }
                    this.setExcersieSet(vector);
                    store.closeRecordStore();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        }

    private boolean getBooleanValue(String str){
        if(str.equals("false"))return false;
        return true;
    }*/

    public void setExcersieSet(Vector v){
        ExerciseSet[] exMas=new ExerciseSet[v.size()];
        int i=0;
        for(Enumeration en=v.elements();en.hasMoreElements();){
            exMas[i]=(ExerciseSet) en.nextElement();
            i++;
        }
        sets=exMas;
    }

    public void setName(String name){
        this.name=name;
    }

}
