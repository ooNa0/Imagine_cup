package com.example.imagincup.model;

import android.app.Application;
import android.text.BoringLayout;

public class MissionState extends Application {
    private Boolean isDone = false;
    private Boolean isSet = false;

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getIsSet() {
        return isSet;
    }

    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
    }

    private static MissionState instance = null;

    public static synchronized MissionState getInstance(){
        if(null == instance){
            instance = new MissionState();
        }
        return instance;
    }
}
