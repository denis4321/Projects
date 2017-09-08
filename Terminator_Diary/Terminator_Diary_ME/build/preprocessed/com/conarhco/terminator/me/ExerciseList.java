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

    private TerminatorMID owner;

    public ExerciseList(TerminatorMID owner) {
        super("Запланированные упражнения", List.IMPLICIT);
        this.owner = owner;
        showElements(owner.getCurrentWorkout());
        setCommandListener(this);
        this.addCommand(owner.finishTraining);
        this.addCommand(owner.EXIT);
        this.setTitle(owner.getCurrentWorkout().getName());
        owner.setNeedToSaveWorkout(true);
    }

    private void showElements(Workout w) {
        int j = 0;
        for (int i = 0; i < w.getSize(); i++) {
            Exercise e = owner.getCurrentWorkout().getExercise(i);
            this.append(++j + " " + e.getName(), null);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            owner.showExercise(this);
        } else {
            owner.commandAction(c, d);
        }
    }

    public Exercise getExercise() {
        int index = getSelectedIndex();
        Exercise exer = owner.getCurrentWorkout().getExercise(index);
        return exer;
    }
}
