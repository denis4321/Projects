/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.me;

import com.conarhco.terminator.common.Exercise;
import com.conarhco.terminator.common.ExerciseSet;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;

/**
 * @author DENIS
 */
public class EditExerciseForm extends Form implements CommandListener {

    private Exercise curExercise;
    //private TerminatorMID midlet;
    //private ExerciseList owner;
    private Vector textFieldWeightVector = new Vector();
    private Vector textFieldRepeatsVector = new Vector();
    private Command back = new Command("Назад", Command.BACK, 1);
    private Command ok = new Command("Далее", Command.SCREEN, 1);
    private String baseOrIsolate = "sss";
    private String restField = "aaa";
    private ChoiceGroup weightCorrect = new ChoiceGroup("", Choice.MULTIPLE, new String[]{"Коррекция веса"}, null);
    private ChoiceGroup techCorrect = new ChoiceGroup("", Choice.MULTIPLE, new String[]{"Коррекция техники"}, null);
    private Alert proofAlert = new Alert("");
    private Command okAlertCommand = new Command("Продолжить", Command.OK, 1);
    private Command cancelAlertCommand = new Command("Отмена", Command.CANCEL, 1);
    private Image img;
    // определяет были ли изменены значения полей формы
    private boolean isChange;

    /**
     * проверка на изменение+команда выход+команда возврат
     * @param exercises
     */
    public EditExerciseForm(Exercise e) throws IOException {
        super(e.getName());
            curExercise = e;
            definitionOfExerciseSetAmount(curExercise);
            setCommandListener(this);
            addCommand(back);
            addCommand(ok);
            img = Image.createImage("/1.png");
            proofAlert.setCommandListener(this);
            proofAlert.addCommand(okAlertCommand);
            proofAlert.addCommand(cancelAlertCommand);
            addCommand(TerminatorMID.viewHistory);
            proofAlert.setImage(img);
    }

    public void setValueOfFormItems(Exercise exercise) {
        if (exercise.getType() == Exercise.BASE) {
            baseOrIsolate = "Базовое";
        } else {
            baseOrIsolate = "Изолированное";
        }
        restField = exercise.getRest() + "";
        weightCorrect.setSelectedIndex(0, exercise.isWeightCorret());
        techCorrect.setSelectedIndex(0, exercise.isTechCorrect());
    }

    public void definitionOfExerciseSetAmount(Exercise exercise) {
        deleteAll();
        this.setValueOfFormItems(exercise);
        append(baseOrIsolate);
        append(weightCorrect);
        append(techCorrect);
        append("Отдых между подходами " + restField + "\n");
        ExerciseSet[] set = exercise.getSets();
        TextField tf = null;
        for (int i = 0; i < set.length; i++) {
            append("Подход № " + (i + 1));
            tf = new TextField("Количкство раз", set[i].getRepeats() + "", 255, TextField.NUMERIC);
            append(tf);
            textFieldRepeatsVector.addElement(tf);
            if (set[i].getWeight() != 0) {
                tf = new TextField("Вес", "" + set[i].getWeight(), 255, TextField.ANY);
                textFieldWeightVector.addElement(tf);
                append(tf);
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == back) {
            TerminatorMID.getMidlet().showExercises();
        } else if (c == ok) {
            syncExercise(curExercise, false);
            if (isChange) {
                proofAlert.setString("Параметры были изменены!!!\n"
                        + "Хотите продолжить?");
            } else {
                proofAlert.setString("Нет изменений!!!\n"
                        + "Хотите продолжить?");
            }
            Display.getDisplay(TerminatorMID.getMidlet()).setCurrent(proofAlert);
        } else if (c == cancelAlertCommand) {
            Display.getDisplay(TerminatorMID.getMidlet()).setCurrent(this);
            isChange = false;
        } else if (c == okAlertCommand) {
            this.syncExercise(curExercise, true);
            TerminatorMID.getMidlet().showExercises();
            isChange = false;
        } else {
            TerminatorMID.getMidlet().commandAction(c, d);
        }
    }

    /*
     * считывает данные из формы,
     * усли булевский параметр false, тогда идет лишь проверка на изменения
     * значений полей,
     * если булевский параметр тру - производится изменение полей формы
     */
    public void syncExercise(Exercise exercise, boolean toExecuteChanges) {
        TextField tf = null;
        //проверка количетва какаого-либо действия для подхода
        for (int i = 0; i < textFieldRepeatsVector.size(); i++) {
            tf = (TextField) textFieldRepeatsVector.elementAt(i);
            int repeats = Integer.parseInt(tf.getString());
            if (!toExecuteChanges) {
                if (exercise.getSets()[i].getRepeats() != repeats) {
                    isChange = true;
                }
            } else {
                exercise.getSets()[i].setRepeats(repeats);
            }
        }
//проверка количества веса в подходе
        for (int i = 0; i < this.textFieldWeightVector.size(); i++) {
            tf = (TextField) textFieldWeightVector.elementAt(i);
            double weight = Double.parseDouble(tf.getString());
            if (!toExecuteChanges) {
                if (exercise.getSets()[i].getWeight() != weight) {
                    isChange = true;
                }
            } else {
                exercise.getSets()[i].setWeight(weight);
            }
        }
//проверка коррекции веса
        if (!toExecuteChanges) {
            if (exercise.isWeightCorret() != this.weightCorrect.isSelected(0)) {
                this.isChange = true;
            }
        } else {
            exercise.setWeightCorret(this.weightCorrect.isSelected(0));
        }
//проверка коррекции техники
        if (!toExecuteChanges) {
            if (exercise.isTechCorrect() != this.techCorrect.isSelected(0)) {
                isChange = true;
            }
        } else {
            exercise.setTechCorrect(this.techCorrect.isSelected(0));
        }
        if (toExecuteChanges) TerminatorMID.getMidlet().updateExercise(exercise);
    }
}
