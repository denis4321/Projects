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
 * @author Worker
 */
public class HistoryList extends List implements CommandListener {
    //private Command exitHistory = new Command("Назад",Command.BACK,1);
    Workout w;

    public HistoryList(Workout w){
        super("История тренировки \""+w.getName()+"\"",List.IMPLICIT);
        this.w=w;
        addCommand(TerminatorMID.exitHistory);
        setCommandListener(this);
        showElements(w);
    }

     private void showElements(Workout w) {
        for (int i = 0; i < w.getSize(); i++) {
            Exercise e = w.getExercise(i);
            this.append((i+1) + " " + e.getName(), null);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if(c==List.SELECT_COMMAND){
            int i=getSelectedIndex();
            Exercise e=w.getExercise(i);
            TerminatorMID.getMidlet().showHistoryForm(e);
        } else {
            TerminatorMID.getMidlet().commandAction(c, d);
        }
    }
}
