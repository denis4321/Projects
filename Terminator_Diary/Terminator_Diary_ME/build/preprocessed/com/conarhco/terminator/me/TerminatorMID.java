/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.me;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import com.conarhco.terminator.common.Workout;
import com.conarhco.terminator.store.WorkoutStore;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 * Голавный класс мидлета.
 * Сдеалать сохранинение текущей тренировки и загрузки ее при включении приложения
 * @author Конарх
 */
public class TerminatorMID extends MIDlet implements CommandListener{
    public final Command BACK = new Command("Назад",Command.BACK,1);
    public final Command EXIT = new Command("Выход", Command.EXIT, 1);
    public final Command finishTraining = new Command("Закончить тренеровку",Command.EXIT,1);

    private Vector workoutsVectorTest = new Vector();//Временно: вектор для тренировок
    private Workout currWorkout = null;//Текущая тренировка
    public WorkoutList workoutList = new WorkoutList(this);
    public ExerciseList exList;
    private WorkoutStore wStore= new WorkoutStore(this);
    private boolean needToSaveWorkout=true;

    public TerminatorMID() {
        Workout w = new Workout(1, "Грудь и бицепс");
        Exercise e = new Exercise("Жим от груди лежа", Exercise.BASE, new ExerciseSet[]{
                    new ExerciseSet(10, 50), new ExerciseSet(10, 55), new ExerciseSet(10, 60)
                }, 60);
        w.addExercise(e);
        e = new Exercise("Подъем ног согнутых в коленях", Exercise.ISOLATE, new ExerciseSet[]{
                    new ExerciseSet(60, 0), new ExerciseSet(60, 0)}, 30);
        w.addExercise(e);
        workoutsVectorTest.addElement(w);
        w = new Workout(3, "Плечи и ноги");
        e = new Exercise("Жим гантелей сидя", Exercise.BASE, new ExerciseSet[]{
                    new ExerciseSet(10, 16), new ExerciseSet(8, 14), new ExerciseSet(8, 16), new ExerciseSet(8, 18)
                }, 60);
        w.addExercise(e);
        e = new Exercise("Подъем ног согнутых в коленях", Exercise.ISOLATE, new ExerciseSet[]{
                    new ExerciseSet(60, 0), new ExerciseSet(60, 0)}, 30);
        w.addExercise(e);
        workoutsVectorTest.addElement(w);
    }

    /**
     * Загружает тернитровки
     */
    private Hashtable loadWorkouts() {
        Enumeration en = workoutsVectorTest.elements();
        Workout w = null;
        Hashtable tb = new Hashtable();
        while (en.hasMoreElements()) {
            w = (Workout) en.nextElement();
            String value = w.getName();
            Integer key = new Integer(w.getNum());
            tb.put(key, value);
        }
        return tb;
    }

    public void setNeedToSaveWorkout(boolean needToSaveWorkout) {
        this.needToSaveWorkout = needToSaveWorkout;
    }

    public void startApp() {
        //Инициализация списка тернировок
        //load();
        currWorkout=wStore.load("workout");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        System.out.println("currWorkout="+currWorkout);
        if (currWorkout == null) {
            //Список тренировок
            showWorkouts();
        } else {
            //Список упражнений текущей тренировки
            showExercises();
            showMyLoadExercises();
        }
    }

    public void showMyLoadExercises(){
        Display.getDisplay(this).setCurrent(exList);
    }

    //Загружает и инициализирует тренировки
    public void showWorkouts() {
        Hashtable workoutsTable = loadWorkouts();
        workoutList.deleteAll();
        workoutList.setWorkouts(workoutsTable);
        Display.getDisplay(this).setCurrent(workoutList);
    }

    /**
     * Устанавливает текущую тренировку
     * @param w
     */
    public void setCurrentWorkout(int workoutNum) {
        for (Enumeration en = workoutsVectorTest.elements(); en.hasMoreElements();) {
            Workout w = (Workout) en.nextElement();
            if (w.getNum() == workoutNum) {
                currWorkout = w;
                //currWorkout.setWorkoutList(workoutList);
                showExercises();
                return;
            }
        }
        throw new IllegalArgumentException("Invalid workout num!");
    }

    public Workout getCurrentWorkout() {
        return currWorkout;
    }

    /**
     * Показывает список упражнений
     */
    public void showExercises() {
        exList=new ExerciseList(this);
    }


    public void showExercise(ExerciseList exercises){
        EditExerciseForm edExer = new EditExerciseForm(this, exercises);
        Display.getDisplay(this).setCurrent(edExer);
    }


    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        if(this.needToSaveWorkout) this.wStore.save(currWorkout,"workout");
    }

    // Возвращет эл-т из вектора по выбору из списка
    public Object getSelElementFromList(List src, Vector source) {
        int index = src.getSelectedIndex();
        return source.elementAt(index);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == this.EXIT) {
            destroyApp(true);
            notifyDestroyed();
        } else if (c==BACK){
            this.showExercise(exList);
        } else if (c==finishTraining){           
            try {
                this.needToSaveWorkout=false;
                System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
                wStore.cleanStore("workout");
                this.wStore.save(currWorkout,"history");
            } catch (Exception ex) {
                ex.printStackTrace();
            Alert error = new Alert("аШипка!", ex.toString(), null, AlertType.ERROR);
            error.setTimeout(5000);
            Display.getDisplay(this).setCurrent(error);
            }
            this.showWorkouts();   
        }
    }

/*    public void save(){
         try {
            RecordStore store = RecordStore.openRecordStore("workout", true);
         System.out.println("---------------------");
         System.out.println("tring to save");
               RecordEnumeration en1 = store.enumerateRecords(null, null, false);
           /* while (en1.hasNextElement()) {
                int j = en1.nextRecordId();
                store.deleteRecord(j);
            }*/
  /*
    int t=0;
            for (Enumeration en = currWorkout.getExVector().elements(); en.hasMoreElements();) {
                Exercise myEx = (Exercise) en.nextElement();
                store.addRecord(myEx.getName().getBytes(), 0, myEx.getName().length());
                System.out.println(" !!!!       Have saved exercise: " +new String(store.getRecord(++t)));
                myEx.save();
            }
            store.closeRecordStore();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void load(){
  //     if (first) {
            try {
                 System.out.println("---------------------");
         System.out.println("tring to load");

                RecordStore store = RecordStore.openRecordStore("workout", true);
                if (store.getNumRecords() != 0) {
                    System.out.println("Record stor !=null");
                    System.out.println(currWorkout==null);
                    currWorkout = new Workout(0,null);
                     System.out.println(currWorkout==null);
                    /////
                    //Exercise myEx = new Exercise(null,0,null,0);
                    //
                    for (RecordEnumeration en = store.enumerateRecords(null, null, false); en.hasNextElement();) {
                        int id = en.nextRecordId();
                        String str = new String(store.getRecord(id));
                        Exercise myEx = new Exercise(null,0,null,0);
                        myEx.load( str);
                        this.currWorkout.addExercise(myEx);
                        System.out.println("    this exercise has next fields: ");
                        System.out.println("        name= "+myEx.getName());
                        System.out.println("        type= "+myEx.getType());
                        System.out.println("        tehCorrect= "+myEx.isTechCorrect());
                        System.out.println("        weightCorrect= "+myEx.isWeightCorret());
                        System.out.println("        rest= "+myEx.getRest());
                        ExerciseSet[]  set=myEx.getSets();
                        for(int i=0;i<set.length;i++){
                            System.out.println("------------------------");
                            System.out.println(set[i].getRepeats());
                            System.out.println(set[i].getWeight());
                        }
                     //   Category c = addCategory(str);
                       // c.load();
                    }
                    System.out.println(this.currWorkout.getExVector().size()+"  ddddddddddddd");
                    store.closeRecordStore();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //finally {
              //  first = false;
            //}
        //}
    }
*/
}
