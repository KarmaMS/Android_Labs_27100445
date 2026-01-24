package com.example.lonelytwitter;

import java.util.Date;

public abstract class CurrentMood {
    private Date moodDate;

    public CurrentMood() {
        this.moodDate = new Date();
    }

    public CurrentMood(Date date) {
        this.moodDate = date;
    }

    public Date getDate() {
        return moodDate;
    }

    public void setDate(Date date) {
        this.moodDate = date;
    }

    public abstract String getMood();
}
