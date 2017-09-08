/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.me;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.Workout;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 *
 * @author DENIS
 */
public class ExerciseList extends List implements CommandListener {

    public ExerciseList() {
        super("Запланированные упражнения", List.IMPLICIT);
        //this.owner = owner;
        showElements(TerminatorMID.getMidlet().getCurrentWorkout());
        setCommandListener(this);
        this.addCommand(TerminatorMID.finishTraining);
        this.addCommand(TerminatorMID.viewHistory);
        this.addCommand(TerminatorMID.EXIT);
        this.setTitle(TerminatorMID.getMidlet().getCurrentWorkout().getName());
    }

    private void showElements(Workout w) {
        for (int i = 0; i < w.getSize(); i++) {
            Exercise e = TerminatorMID.getMidlet().getCurrentWorkout().getExercise(i);
            this.append((i+1) + " " + e.getName(), null);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            try{
                TerminatorMID.getMidlet().showExercise(getSelectedIndex());
            } catch (Exception ex){
                TerminatorMID.showError(ex, this);
            }
        } else {
            TerminatorMID.getMidlet().commandAction(c, d);
        }
    }

    /*public Exercise getExercise() {
        int index = getSelectedIndex();
        Exercise exer = owner.getCurrentWorkout().getExercise(index);
        return exer;
    }*/
}
