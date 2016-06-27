package com.abs.samih.fcm_ex01.data;

import android.location.Location;

import java.util.Date;

/**
 * Created by school on 08/06/2016.
 */
public class MyTask {

    private String taskKey;
    private  String text;
    private boolean isComplated=false;
    private Date createdAt;
    private int prio;
    private String location;
    private String phone;

    public MyTask(String text, boolean isComplated, Date createdAt, int prio, String location, String phone) {
        this.text = text;
        this.isComplated = isComplated;
        this.createdAt = createdAt;
        this.prio = prio;
        this.location = location;
        this.phone = phone;
    }

    public MyTask() {
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isComplated() {
        return isComplated;
    }

    public void setComplated(boolean complated) {
        isComplated = complated;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "text='" + text + '\'' +
                ", isComplated=" + isComplated +
                ", prio=" + prio +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
