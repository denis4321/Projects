/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.store;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import com.conarhco.terminator.me.TerminatorMID;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author Me
 */
public class ExerciseStore {
private TerminatorMID parent;

    public ExerciseStore(TerminatorMID parent){
        this.parent=parent;
    }

//чистим хранилище от старых записей
    public void cleanStore(RecordStore store) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException{
        RecordEnumeration en1 = store.enumerateRecords(null, null, false);
            while (en1.hasNextElement()) {
                int j = en1.nextRecordId();
                store.deleteRecord(j);
                System.out.println("del from exerciseStore= " + j);
            }
    }
//смотрим что записано в хранилище
    public void showResultOfSave(RecordStore store) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException{
        RecordEnumeration en1 = store.enumerateRecords(null, null, false);
         while (en1.hasNextElement()) {
                String j = new String(en1.nextRecord());
                System.out.println("# RECORD=" + j + "     In this exercise I have seen field: " + j);
            }
    }


    public void save(Exercise exercise) {
        try {
            String s = null;
            RecordStore store = RecordStore.openRecordStore(exercise.getName(), true);
            cleanStore(store);
            for (int i = exercise.getSets().length - 1; i >= 0; i--) {
                s = exercise.getSets()[i].getWeight() + "";
                store.addRecord(s.getBytes(), 0, s.getBytes().length);
                s = exercise.getSets()[i].getRepeats() + "";
                store.addRecord(s.getBytes(), 0, s.getBytes().length);
            }
            s = exercise.getRest() + "";
            store.addRecord(s.getBytes(), 0, s.getBytes().length);
            s = exercise.isWeightCorret() + "";
            store.addRecord(s.getBytes(), 0, s.getBytes().length);
            s = exercise.isTechCorrect() + "";
            store.addRecord(s.getBytes(), 0, s.getBytes().length);
            s = exercise.getType() + "";
            store.addRecord(s.getBytes(), 0, s.getBytes().length);
            showResultOfSave(store);
            store.closeRecordStore();
        } catch (Exception ex) {
           ex.printStackTrace();
            Alert error = new Alert("аШипка!", ex.toString(), null, AlertType.ERROR);
            error.setTimeout(5000);
            Display.getDisplay(parent).setCurrent(error);
        }
    }

    public Exercise load(String exName) {
        Exercise exercise = new Exercise(null, 0, null, 0);
        Vector loadVector = new Vector();
        try {
            RecordStore store = RecordStore.openRecordStore(exName, true);
            for (RecordEnumeration en = store.enumerateRecords(null, null, false); en.hasNextElement();) {
                String str = new String(en.nextRecord());
                loadVector.addElement(str);
            }
            getExerciseValuesFromVector(loadVector, exercise);
            exercise.setName(exName);
            store.closeRecordStore();
        } catch (Exception ex) {
           ex.printStackTrace();
            Alert error = new Alert("аШипка!", ex.toString(), null, AlertType.ERROR);
            error.setTimeout(5000);
            Display.getDisplay(parent).setCurrent(error);
        }
        return exercise;
    }

    private boolean getBooleanValue(String str) {
        if (str.equals("false")) {
            return false;
        }
        return true;
    }

    private void getExerciseValuesFromVector(Vector loadVector, Exercise exercise) {
        Vector vector = new Vector();
        for (int i = 0; i < loadVector.size(); i++) {
            String str = (String) loadVector.elementAt(i);
            switch (i) {
                case 0:
                    exercise.setType(Integer.parseInt(str));
                    break;
                case 1:
                    exercise.setTechCorrect(getBooleanValue(str));
                    break;
                case 2:
                    exercise.setWeightCorret(getBooleanValue(str));
                    break;
                case 3:
                    exercise.setRest(Integer.parseInt(str));
                    break;
                default:
                    if (i % 2 != 0) {
                        String strOld = (String) loadVector.elementAt(i - 1);
                        int repeats = Integer.parseInt(strOld);
                        double weights = Double.parseDouble(str);
                        vector.addElement(new ExerciseSet(repeats, weights));
                    }
            }
        }
        exercise.setExcersieSet(vector);
    }
}

