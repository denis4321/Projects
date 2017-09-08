/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.common;

import com.conarhco.terminator.me.ExerciseList;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Упраженение
 * @author Конарх
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
    private int recordId = -1;

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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

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

    public boolean equals(Object obj){
        if(obj==this) return true;
        else if (obj==null) return false;
        else if(obj instanceof Exercise){
                if(name.equals(((Exercise)obj).name)) return true;
            }
        return false;
        }


    public int hashCode() {
        return name.hashCode();
    }




}
