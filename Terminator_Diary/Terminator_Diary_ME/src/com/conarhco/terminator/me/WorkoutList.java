/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.me;

import com.conarhco.terminator.common.Workout;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStoreException;

/**
 * Список тренировок
 * @author Конарх
 */
public class WorkoutList extends List implements CommandListener {

    //private TerminatorMID parent;
    private Hashtable workouts = null;//Датамодель тренировок
    private int[] nums = null;

    public WorkoutList(TerminatorMID parent) {
        super("Выбор тренировки", List.IMPLICIT);
        this.addCommand(TerminatorMID.EXIT);
        this.addCommand(TerminatorMID.viewAutherization);
        this.addCommand(TerminatorMID.viewHistory);
        this.setCommandListener(this);
    }

    public void setWorkouts(Hashtable workouts) {
        this.workouts = workouts;
        nums = new int[workouts.size()];
        Enumeration en = workouts.keys();
        int index = 0;
        while (en.hasMoreElements()) {
            nums[index++] = ((Integer) en.nextElement()).intValue();
        }
        //Сортировка
        sort(nums);
        for (int i = 0; i < workouts.size(); i++) {
            this.append(nums[i] + " " + workouts.get(new Integer(nums[i])), null);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            int num = nums[getSelectedIndex()];
            TerminatorMID.getMidlet().setCurrentWorkout(num, true);
        } else {
            TerminatorMID.getMidlet().commandAction(c, d);
        }
    }

    public void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = a[i];
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < min) {
                    min = a[j];
                    a[j] = a[i];
                    a[i] = min;
                }
            }
        }
    }
}
