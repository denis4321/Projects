package com.conarhco.terminator.store;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import com.conarhco.terminator.common.Workout;
import com.conarhco.terminator.me.TerminatorMID;
import java.util.Enumeration;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Me
 */
public class WorkoutStore {
    private ExerciseStore eStore;
    private TerminatorMID parent;
    public WorkoutStore(TerminatorMID parent){
        this.parent=parent;
        eStore=new ExerciseStore(parent);
    }

    public void save(Workout currWorkout,String storeName) {
        try {
            RecordStore store = RecordStore.openRecordStore(storeName, true);
            System.out.println("---------------------");
            System.out.println("                    "+storeName);
            System.out.println("trying to save");
            for (int i = currWorkout.getExVector().size()-1; i>=0; i--) {
                Exercise myEx = (Exercise) currWorkout.getExVector().elementAt(i);
                store.addRecord(myEx.getName().getBytes(), 0, myEx.getName().length());
                System.out.println(" !!!!       Have saved exercise: " + store.getNumRecords() + " " + myEx.getName());
                eStore.save(myEx);
                System.err.println("I am in workoutSave in the loop");
            }
            store.addRecord(currWorkout.getName().getBytes(), 0, currWorkout.getName().getBytes().length);
            store.closeRecordStore();
        } catch (Exception ex) {
           ex.printStackTrace();
            Alert error = new Alert("аШипка!", ex.toString(), null, AlertType.ERROR);
            error.setTimeout(5000);
            Display.getDisplay(parent).setCurrent(error);
        }
    }

    public Workout load(String storeName) {
        Workout currWorkout = null;
        try {
            System.out.println("---------------------");
            System.out.println("trying to load");
            RecordStore store = RecordStore.openRecordStore(storeName, true);
            if (store.getNumRecords() != 0) {
                int t=0;
                for (RecordEnumeration en = store.enumerateRecords(null, null, false); en.hasNextElement();) {
                    String str = new String(en.nextRecord());
                    System.out.println("t= "+t);
                   if (t==0){
                        currWorkout = new Workout(0, str);
                        System.out.println(str);
                   } else {
                    Exercise myEx = eStore.load(str);
                    currWorkout.addExercise(myEx);
                    System.out.println();
                    System.out.println("    this exercise has next fields: ");
                    System.out.println("        name= " + myEx.getName());
                    System.out.println("        type= " + myEx.getType());
                    System.out.println("        tehCorrect= " + myEx.isTechCorrect());
                    System.out.println("        weightCorrect= " + myEx.isWeightCorret());
                    System.out.println("        rest= " + myEx.getRest());
                    ExerciseSet[] set = myEx.getSets();
                   System.out.println("IS PODHODI NULL");
                   System.out.println(set==null);
                    for (int i = 0; i < set.length; i++) {
                        System.out.println("------------------------");
                        System.out.println(set[i].getRepeats());
                        System.out.println(set[i].getWeight());
                    }
                }
                    t++;
                }
                      System.out.println(currWorkout.getExVector().size()+"  =size");
                    RecordEnumeration en1 = store.enumerateRecords(null, null, false);
                 while (en1.hasNextElement()) {
                   int j = en1.nextRecordId();
                  store.deleteRecord(j);
                 System.out.println("del= "+j);
                }
                store.closeRecordStore();
            }
        } catch (Exception ex) {
           ex.printStackTrace();
            Alert error = new Alert("аШипка!", ex.toString(), null, AlertType.ERROR);
            error.setTimeout(5000);
            Display.getDisplay(parent).setCurrent(error);
        }
        return currWorkout;
    }

    public void cleanStore(String recordStoreName) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException{
        RecordStore store = RecordStore.openRecordStore(recordStoreName, true);
        RecordEnumeration en1 = store.enumerateRecords(null, null, false);
            while (en1.hasNextElement()) {
                int j = en1.nextRecordId();
                store.deleteRecord(j);
                System.out.println("УДАЛЯЮ ПОСЛЕ ОТПРАКИ В ИСТОРИЮ= " + j);
            }
            store.closeRecordStore();
    }
}
