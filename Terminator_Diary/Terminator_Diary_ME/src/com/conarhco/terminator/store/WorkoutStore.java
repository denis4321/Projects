/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conarhco.terminator.store;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import com.conarhco.terminator.common.Workout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author Me
 */
public class WorkoutStore {
    public static final String CURRENT_WORKOUT = "workout";
    public static final String HISTORY_WORKOUT = "history";
    public static final String EXERCISES = "exercises";

    /*public Workout makeWorkoutFromBytes(byte[] bytes) throws IOException{
        ByteArrayInputStream buff=new ByteArrayInputStream(bytes);
        DataInputStream data = new DataInputStream(buff);
        int num=data.readInt();
        String name=data.readUTF();
        data.close();
        return new Workout(num,name);
    }*/

    public void saveWorkout(Workout w, String storeName) throws RecordStoreException, IOException {
        System.out.println("Saving "+storeName);
        RecordStore wStore = RecordStore.openRecordStore(storeName, true);
        try {
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(buff);
            data.writeInt(w.getNum());
            data.writeUTF(w.getName());
            for (int i = 0; i < w.getSize(); i++) {
                Exercise e = w.getExercise(i);
                int id = saveExercise(e);
                data.writeInt(id);
                System.out.println("Saving exercise id="+id);
            }
            data.close();
            byte[] wData = buff.toByteArray();
            //RecordEnumeration en = wStore.enumerateRecords(null, null, false);
            wStore.addRecord(wData, 0, wData.length);
        } finally {
            wStore.closeRecordStore();
        }
    }

    public int saveExercise(Exercise exercise) throws IOException, RecordStoreException {
        RecordStore exStore = RecordStore.openRecordStore(EXERCISES, true);
        try {
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(buff);
            data.writeUTF(exercise.getName());
            data.writeInt(exercise.getType());
            data.writeBoolean(exercise.isTechCorrect());
            data.writeBoolean(exercise.isWeightCorret());
            data.writeInt(exercise.getRest());
            ExerciseSet[] set = exercise.getSets();
            for (int i = 0; i < set.length; i++) {
                data.writeInt(set[i].getRepeats());
                data.writeDouble(set[i].getWeight());
            }
            data.close();
            byte[] eData = buff.toByteArray();
            int id = exercise.getRecordId();
            System.out.println("Exercise ID="+id);
            if (id < 0) {
                id = exStore.addRecord(eData, 0, eData.length);
                exercise.setRecordId(id);
            } else {
                exStore.setRecord(id, eData, 0, eData.length);
            }
            return id;
        } finally {
            exStore.closeRecordStore();
        }
    }

    public void clearWorkout(String name, Workout w, boolean deleteExercises) throws RecordStoreException, IOException{
        RecordStore store=RecordStore.openRecordStore(name, true);
        RecordStore exStore=RecordStore.openRecordStore(EXERCISES, true);
        System.out.println("Clearing workout "+name);
        if (w==null){
            w = load(name);
        }
        RecordEnumeration en = store.enumerateRecords(null, null, false);
        while (en.hasNextElement()) {
            int j = en.nextRecordId();
            store.deleteRecord(j);
        }
        if (w!=null&&deleteExercises){
            for (int i = 0; i < w.getSize(); i++) {
                int id = w.getExercise(i).getRecordId();
                exStore.deleteRecord(id);
                System.out.println("deleted exercise id="+id);
            }
        }
        if (store.getNumRecords()>0) throw new IllegalArgumentException("Workout not entierly deleted: size="+store.getNumRecords());
        store.closeRecordStore();
        exStore.closeRecordStore();
    }

    /**
     * @param storeName
     * @return
     * @throws RecordStoreException
     * @throws IOException
     */
    public Workout load(String storeName) throws RecordStoreException, IOException{
        System.out.println("trying to load "+storeName);
        RecordStore wStore = RecordStore.openRecordStore(storeName, true);
        if(wStore.getNumRecords()>0){
            System.out.println("Record store "+storeName+", numRecords="+wStore.getNumRecords());
           // int size=wStore.getNumRecords();
           // byte[] buf=wStore.getRecord(size);
            byte[] buf=null;
            RecordEnumeration en = wStore.enumerateRecords(null, null, false);
            while(en.hasNextElement()){
                buf=en.nextRecord();
            }
            ByteArrayInputStream buff=new ByteArrayInputStream(buf);
            DataInputStream data = new DataInputStream(buff);
            int num=data.readInt();
            String name=data.readUTF();
            Workout w=new Workout(num, name);
            while(data.available()>0){
                int id=data.readInt();
                System.out.println(id);
                Exercise e=loadExercise(id);
                w.addExercise(e);
            }
            wStore.closeRecordStore();
            //RecordStore.deleteRecordStore(storeName);
            return w;
         }
        return null;
    }

    public Exercise loadExercise(int id) throws RecordStoreException, IOException{
        RecordStore eStore = RecordStore.openRecordStore(EXERCISES, true);
        byte[] buf=eStore.getRecord(id);
        eStore.closeRecordStore();
        Exercise ex = makeExerciseFromBytes(buf);
        ex.setRecordId(id);
        return ex;
    }

    public Exercise makeExerciseFromBytes(byte[] bytes) throws IOException{
        ByteArrayInputStream buff=new ByteArrayInputStream(bytes);
        DataInputStream data = new DataInputStream(buff);
        String name=data.readUTF();
        int type=data.readInt();
        boolean isTechCorrect=data.readBoolean();
        boolean isWeightCorrect=data.readBoolean();
        int rest=data.readInt();
        int size=data.available();
        ExerciseSet[] set = new ExerciseSet[size/12];
        for(int i=0;i<set.length;i++){
            int repeats=data.readInt();
            double weight=data.readDouble();
            set[i] = new ExerciseSet(repeats, weight);
        }
        data.close();
        Exercise exercise = new Exercise(name, type, set, rest);
        exercise.setTechCorrect(isTechCorrect);
        exercise.setWeightCorret(isWeightCorrect);
        return exercise;
    }

}
