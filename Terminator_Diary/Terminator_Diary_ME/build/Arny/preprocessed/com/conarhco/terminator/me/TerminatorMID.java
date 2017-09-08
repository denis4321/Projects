/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.me;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import com.conarhco.terminator.common.Workout;
import com.conarhco.terminator.store.WorkoutStore;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;

/**
 * Голавный класс мидлета.
 * Сдеалать сохранинение текущей тренировки и загрузки ее при включении приложения
 * @author Конарх
 */
public class TerminatorMID extends MIDlet implements CommandListener{
    public static final Command BACK = new Command("Назад",Command.BACK,1);
    public static final Command EXIT = new Command("Выход", Command.EXIT, 1);
    public static final Command finishTraining = new Command("Закончить тренеровку",Command.SCREEN,3);
    public static final Command viewHistory = new Command("История",Command.SCREEN,2);
    public static final Command exitHistory = new Command("Покинуть историю",Command.BACK,2);
    public static final Command viewAutherization = new Command("Авторизация",Command.SCREEN,4);
    private Vector workoutsVectorTest = new Vector();//Временно: вектор для тренировок
    private Workout currWorkout = null;//Текущая тренировка
    public WorkoutList workoutList = new WorkoutList(this);
    public ExerciseList exList = null;
    private WorkoutStore myStore = new WorkoutStore();
    private boolean isFirst = true;
    private static TerminatorMID midlet;
    private HistoryList list;
    private Displayable historyPrev = null;
    private AuthorizationForm authForm=null;
    private User currUsr = null;
    private static final String SITE = "http://127.0.0.1:8080/arny/mobile/";
    private Hashtable idTable = new Hashtable();



    public TerminatorMID() {

        midlet = this;
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
        authForm = new AuthorizationForm(this);
    }

    /**
     * Загружает трeнировки
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

    public void startApp() {
        this.authForm.load();
        //Для теста
        currUsr = new User("conarh", "111111");
      // currUsr = new User("denis", "1");
        Hashtable table= callWeb("getWorkout", null, workoutList);

     // System.out.println(encode(table));
        /*try{//Инициализация списка тернировок
            if (isFirst){
                isFirst = false;
                setCurrentWorkout(myStore.load("workout"), false);
            }
            if (currWorkout == null) {
                //Список тренировок
                showWorkouts();
            } else {
                //Список упражнений текущей тренировки
                showExercises();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }*/
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
     * TODO: Здесь будет загрузка тренировки по сети
     * @param w
     */
    public void setCurrentWorkout(int workoutNum, boolean save) {
        for (Enumeration en = workoutsVectorTest.elements(); en.hasMoreElements();) {
            Workout w = (Workout) en.nextElement();
            if (w.getNum() == workoutNum) {
                 //System.out.println(w.getName()+"asdasdasdasdsad");
                 setCurrentWorkout(w, save);
                 return;
            }
        }
        throw new IllegalArgumentException("Invalid workout num!");
    }

    public void setCurrentWorkout(Workout w, boolean save) {
        if (currWorkout!=null&&w!=null) throw new IllegalStateException("workout already open!");
        currWorkout = w;
        if (currWorkout!=null){
            if (save){
                try {
                    myStore.saveWorkout(w, WorkoutStore.CURRENT_WORKOUT);
                    exList = null;
                    showExercises();
                } catch (Exception ex) {
                    showError(ex, null);
                }
            }
        }
    }

    public void updateExercise(Exercise e){
        try {
            myStore.saveExercise(e);
        } catch (Exception ex) {
            showError(ex, null);
        }
    }

    public Workout getCurrentWorkout() {
        return currWorkout;
    }

    /**
     * Показывает список упражнений
     */
    public void showExercises() {
         System.out.println("------------------");
        if (exList==null) exList=new ExerciseList();
        Display.getDisplay(this).setCurrent(exList);
    }


    public void showExercise(int index) throws IOException{

        EditExerciseForm edExer = new EditExerciseForm(currWorkout.getExercise(index));
        Display.getDisplay(this).setCurrent(edExer);
    }


    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        // if(this.needToSaveWorkout) this.wStore.save(currWorkout,"workout");

    }

    // Возвращет эл-т из вектора по выбору из списка
    public Object getSelElementFromList(List src, Vector source) {
        int index = src.getSelectedIndex();
        return source.elementAt(index);
    }

    public void commandAction(Command c, Displayable d) {
        if (c ==EXIT) {
            destroyApp(true);
            notifyDestroyed();
        }
        else if(c==TerminatorMID.viewAutherization){

            Display.getDisplay(this).setCurrent(authForm);
        }
        else if(c==TerminatorMID.viewHistory){
            historyPrev = d;
            TerminatorMID.getMidlet().showHistoryList();
        }
        else if(c==TerminatorMID.exitHistory){
            TerminatorMID.getMidlet().backFromHistoryList();
        }
        else if (c==finishTraining){
            try {
                myStore.clearWorkout(WorkoutStore.HISTORY_WORKOUT, null, true);
                myStore.saveWorkout(currWorkout,WorkoutStore.HISTORY_WORKOUT);
                myStore.clearWorkout(WorkoutStore.CURRENT_WORKOUT, currWorkout, false);
                this.currWorkout=null;
            } catch (Exception ex) {
                showError(ex, d);
            }
            this.showWorkouts();
        }
    }

    public static TerminatorMID getMidlet(){
        return midlet;
    }

    public static void showError(Exception ex, Displayable ret) {
        ex.printStackTrace();
        Alert error = new Alert("аШипка!", ex.toString(), null, AlertType.ERROR);
        error.setTimeout(5000);
        if (ret!=null){
            Display.getDisplay(midlet).setCurrent(error, ret);
        } else {
            Display.getDisplay(midlet).setCurrent(error);
        }
    }

    public WorkoutStore getStore(){
        return myStore;
    }

    public void showHistoryList(){
        try {
            Workout w = myStore.load("history");
            list = new HistoryList(w);
            Display.getDisplay(this).setCurrent(list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showHistoryForm(Exercise e){
            Display.getDisplay(this).setCurrent(new HistoryForm(e));
    }

    public void backFromHistoryForm(){
        Display.getDisplay(this).setCurrent(list);
    }

    public void backFromHistoryList(){
        Display.getDisplay(this).setCurrent(historyPrev);
    }

    public void backFromAutherizationForm(){
       Display.getDisplay(this).setCurrent(this.workoutList);
    }

    public void setUser(User u){
        currUsr = u;
    }

    public Hashtable callWeb(String addr, String[][] params, Displayable from){
        try{
            if (currUsr==null) throw new IllegalStateException("Авторизация не настроена!");
            String query = SITE+addr+"?login="+currUsr.getLogin()+"&pass="+currUsr.getPass();
           // String query = SITE+addr+"?login=denis"+"&pass=1";
            HttpConnection c = (HttpConnection) Connector.open(query);
            //c.setRequestMethod("GET");
            /*System.out.println("user login="+currUsr.getLogin()+" pass="+currUsr.getPass());
            PrintStream out = new PrintStream(c.openOutputStream());
            out.print("login="+currUsr.getLogin()+"&pass="+currUsr.getPass());
            if (params!=null){
                for (int i=0; i<params.length; i++){
                    out.print("&"+params[i][0]+"="+params[i][1]);
                }
            }
            out.flush();*/
            int rc = c.getResponseCode();
            System.out.println("Response code = "+rc);

             Vector v = new Vector();
                int r = -1;
                InputStream in = c.openInputStream();
                while ((r = in.read()) >= 0) {
                    v.addElement(new Byte((byte) r));
                }
                byte[] data = new byte[v.size()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = ((Byte) v.elementAt(i)).byteValue();
                }


          //  InputStream in = c.openInputStream();
           // int b = -1;
           // StringBuffer sb = new StringBuffer();
           // while ((b=in.read())>=0){
            // было    sb.append(new String(new byte[]{(byte)b}));
                 //sb.append(Charset.getCharset("cp1251").decode(new byte[]{(byte)b}));
           // }

            //System.out.println(sb.toString());
             
             
             
             Charset enc = Charset.getCharset("cp1251");
             String content = enc.decode(data);
             System.out.println(content);
             



            Hashtable res = decode(content);


            // было Hashtable res = decode(sb.toString());


            //Hashtable workoutsTable = loadWorkouts();
            workoutList.deleteAll();
            workoutList.setWorkouts(res);
            Display.getDisplay(this).setCurrent(workoutList);


            return res;
            //return sb.toString();
        } catch (Exception ex){
            showError(ex, from);
            return null;
        }
    }

    public static String encode(Hashtable values){
        String rezult = "";
        Enumeration en = values.keys();
        while(en.hasMoreElements()){
            String key=(String) en.nextElement();
            rezult+=key+"="+values.get(key)+"\n";
        }
        return rezult;
    }

     public static Hashtable decode(String query){
        Hashtable map = new Hashtable();
        int index=0;
        String key="";
        String value="";
        String nextLine="";
        int count=0;
        int last=query.lastIndexOf('\n');
        while(count<1){
        if(query.indexOf("\n",index)==last)count++;
            int newIndex=query.indexOf("\n",index)+1;
            nextLine=query.substring(index,newIndex);
           // System.out.println(nextLine);
           // System.out.println(query.indexOf("\n",index));

            int separatorIndex=nextLine.indexOf("=");
            key=nextLine.substring(0,separatorIndex);
            value=nextLine.substring(separatorIndex+1, nextLine.length());
            Integer keyValue = new Integer(Integer.parseInt(key));
            map.put(keyValue, value);
            System.out.println(key+" "+value);
            index=newIndex;
        }
        return map;
    }

     public Hashtable getDecodeID(){
        return idTable;
     }

     public void putIntoID_Table(String key, String valueID){
         idTable.put(key, valueID);
     }



}



