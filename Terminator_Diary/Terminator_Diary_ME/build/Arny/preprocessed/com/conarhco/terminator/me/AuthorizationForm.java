/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conarhco.terminator.me;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/**
 *
 * @author RSDENIS
 */
public class AuthorizationForm extends Form implements CommandListener {
    private TextField user = new TextField("Логин", "", 255, TextField.ANY);
    private TextField password = new TextField("Пароль", "", 255, TextField.PASSWORD);
    private TextField retypePassword = new TextField("Повторите пароль", "", 255, TextField.PASSWORD);
    private Command login = new Command("Вход", Command.OK, 1);
    private Command back = new Command("Назад", Command.BACK, 2);
    private Alert passwordAlert = new Alert("");
    private TerminatorMID parent;

    public AuthorizationForm(TerminatorMID parent) {
        super("Авторизация");
        this.parent = parent;
        append(user);
        append(password);
        append(retypePassword);
        this.addCommand(back);
        this.addCommand(login);
        setCommandListener(this);
        //load();
    }

    private boolean checkPassword() {
        return password.getString().equals(retypePassword.getString());
    }

    public void save() {
        try {
            RecordStore store = RecordStore.openRecordStore("auth", true);
            RecordEnumeration en = store.enumerateRecords(null, null, false);
            while (en.hasNextElement()) {
                int j = en.nextRecordId();
                store.deleteRecord(j);
            }
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(buf);
            User u = new User(user.getString(),password.getString());
            data.writeUTF(u.getLogin());
            data.writeUTF(u.getPass());
            data.close();
            store.addRecord(buf.toByteArray(), 0, buf.toByteArray().length);
            parent.setUser(u);
        } catch (Exception ex) {
            TerminatorMID.showError(ex, this);
        }
    }

    public void load() {
        try {
            RecordStore store = RecordStore.openRecordStore("auth", true);
            if (store.getNumRecords() > 0) {
                byte[] buf = null;
                RecordEnumeration en = store.enumerateRecords(null, null, false);
                while (en.hasNextElement()) {
                    buf = en.nextRecord();
                }
                ByteArrayInputStream buff = new ByteArrayInputStream(buf);
                DataInputStream data = new DataInputStream(buff);
                User u = new User(data.readUTF(), data.readUTF());
                user.setString(u.getLogin());
                password.setString(u.getLogin());
                parent.setUser(u);
            }
        } catch (Exception ex) {
            TerminatorMID.showError(ex, this);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == this.back) {
            TerminatorMID.getMidlet().backFromAutherizationForm();
        } else if (c == this.login) {
            if (!checkPassword()) {
                passwordAlert.setString("Пароли не совпадают!!!");
                passwordAlert.setTimeout(1000);
                Display.getDisplay(TerminatorMID.getMidlet()).setCurrent(passwordAlert);
            } else {
               save();
               TerminatorMID.getMidlet().backFromAutherizationForm();
            }
        } else {
            TerminatorMID.getMidlet().commandAction(c, d);
        }
    }

    /*public String getUserLogin(){
        return user.getString();
    }

    public String getPassword(){
        return password.getString();
    }*/
}
