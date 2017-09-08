/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conarhco.terminator.me;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Worker
 */
public class HistoryForm extends Form implements CommandListener{
    private Exercise curExercise;
    private Command back = new Command("Назад", Command.BACK, 1);
    private String baseOrIsolate = "sss";
    private String restField = "aaa";
    private StringItem weightCorrect = new StringItem("", "Коррекция веса");
    private StringItem techCorrect = new StringItem("", "Коррекция техники");

    /**
     * проверка на изменение+команда выход+команда возврат
     * @param exercises
     */
    public HistoryForm(Exercise e) {
        super("История/Упражнения/" + e.getName());
        curExercise = e;
        definitionOfExerciseSetAmount(curExercise);
        setCommandListener(this);
        addCommand(back);
        this.addCommand(TerminatorMID.exitHistory);
    }

    public void setValueOfFormItems(Exercise exercise) {
        if (exercise.getType() == Exercise.BASE) {
            baseOrIsolate = "Базовое";
        } else {
            baseOrIsolate = "Изолированное";
        }
        restField = exercise.getRest() + "";
    }

    public void definitionOfExerciseSetAmount(Exercise exercise) {
        deleteAll();
        this.setValueOfFormItems(exercise);
        append(baseOrIsolate);
        if (exercise.isWeightCorret()){
            append(weightCorrect);
        }
        if (exercise.isTechCorrect()){
            append(techCorrect);
        }
        append("Отдых между подходами " + restField + "\n");
        ExerciseSet[] set = exercise.getSets();
        String tf = null;
        for (int i = 0; i < set.length; i++) {
            append("\tПодход № " + (i + 1)+"\nКоличкство раз ");
            tf = set[i].getRepeats() + "\n";
            append(tf);
            if (set[i].getWeight() != 0) {
                append("Вес");
                tf = set[i].getWeight()+"\n";
                append(tf);
                append("\n");
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if(c==this.back){
            TerminatorMID.getMidlet().backFromHistoryForm();
        } else {
            TerminatorMID.getMidlet().commandAction(c, d);
        }
    }

}