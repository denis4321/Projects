/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.startup.timer.dao;

import java.io.Serializable;

public class FeedConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dayOfWeek;
    private String dayOfMonth;
    private String month;
    private String hour;
    private String minute;
    private String second;
    private boolean enabled;
    private String targetNodeName;

    public FeedConfig() {
    }

    public FeedConfig(String dayOfWeek, String dayOfMonth, String month, String hour, String minute, String second, boolean enabled, String targetNodeName) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.enabled = enabled;
        this.targetNodeName = targetNodeName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTargetNodeName() {
        return targetNodeName;
    }

    public void setTargetNodeName(String targetNodeName) {
        this.targetNodeName = targetNodeName;
    }

    public boolean isNotUpdatedFromDatabase() {
        return dayOfMonth == null && dayOfWeek == null && month == null && hour == null && minute == null && second == null && !enabled && targetNodeName == null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.dayOfWeek != null ? this.dayOfWeek.hashCode() : 0);
        hash = 29 * hash + (this.dayOfMonth != null ? this.dayOfMonth.hashCode() : 0);
        hash = 29 * hash + (this.month != null ? this.month.hashCode() : 0);
        hash = 29 * hash + (this.hour != null ? this.hour.hashCode() : 0);
        hash = 29 * hash + (this.minute != null ? this.minute.hashCode() : 0);
        hash = 29 * hash + (this.second != null ? this.second.hashCode() : 0);
        hash = 29 * hash + (this.enabled ? 1 : 0);
        hash = 29 * hash + (this.targetNodeName != null ? this.targetNodeName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FeedConfig other = (FeedConfig) obj;
        if ((this.dayOfWeek == null) ? (other.dayOfWeek != null) : !this.dayOfWeek.equals(other.dayOfWeek)) {
            return false;
        }
        if ((this.dayOfMonth == null) ? (other.dayOfMonth != null) : !this.dayOfMonth.equals(other.dayOfMonth)) {
            return false;
        }
        if ((this.month == null) ? (other.month != null) : !this.month.equals(other.month)) {
            return false;
        }
        if ((this.hour == null) ? (other.hour != null) : !this.hour.equals(other.hour)) {
            return false;
        }
        if ((this.minute == null) ? (other.minute != null) : !this.minute.equals(other.minute)) {
            return false;
        }
        if ((this.second == null) ? (other.second != null) : !this.second.equals(other.second)) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if ((this.targetNodeName == null) ? (other.targetNodeName != null) : !this.targetNodeName.equals(other.targetNodeName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FeedConfig{" + "dayOfWeek=" + dayOfWeek + ", dayOfMonth=" + dayOfMonth + ", month=" + month + ", hour=" + hour + ", minute=" + minute + ", second=" + second + ", enabled=" + enabled + ", targetNodeName=" + targetNodeName + '}';
    }

}
