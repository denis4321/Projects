/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String profileUser;
    private String profileType;
    private String assinedPerson;
    private String lastName;
    private String firstName;

    public AppData(String profileUser, String profileType, String assinedPerson, String lastName, String firstName) {
        this.profileUser = profileUser;
        this.profileType = profileType;
        this.assinedPerson = assinedPerson;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public AppData() {
    }

    public String getProfileUser() {
        return profileUser;
    }

    public void setProfileUser(String profileUser) {
        this.profileUser = profileUser;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getAssinedPerson() {
        return assinedPerson;
    }

    public void setAssinedPerson(String assinedPerson) {
        this.assinedPerson = assinedPerson;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Object> translate() {
        List list = new ArrayList();
        list.add(getProfileUser());
        list.add(getProfileType());
        list.add(getAssinedPerson());
        list.add(getLastName());
        list.add(getFirstName());
        return list;
    }

}
