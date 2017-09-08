/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.common;

import com.conarhco.terminator.me.WorkoutList;
import java.util.Vector;

/**
 * Тренировка
 * @author Конарх
 */
public class Workout {

    private Vector exercises = new Vector();
    private int num;
    private String name;
    //private WorkoutList wList;

    public Workout(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public void addExercise(Exercise e) {
        //System.out.println(e.toString());
        if (!exercises.contains(e)) {
            exercises.addElement(e);
        } else {
           throw new IllegalArgumentException("Упражнение уже существует");
        }
    }

    public Exercise getExercise(int num) {
        return (Exercise) exercises.elementAt(num);
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public int getSize() {
        return exercises.size();
    }

    public Vector getExVector(){
        return exercises;
    }

}
