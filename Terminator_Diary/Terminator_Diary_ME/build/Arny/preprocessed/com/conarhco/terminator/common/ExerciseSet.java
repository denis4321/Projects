/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conarhco.terminator.common;

/**
 * Подход
 * @author Конарх
 */
public class ExerciseSet {
    private int repeats;
    private double weight;

    public ExerciseSet(int repeats, double weight){
        setRepeats(repeats);
        setWeight(weight);
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        if (repeats<=0) throw new IllegalArgumentException("Invalid repeats");
        this.repeats = repeats;
    }

    public double getWeight() {
        if (weight<0) throw new IllegalArgumentException("Invalid weight");
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
