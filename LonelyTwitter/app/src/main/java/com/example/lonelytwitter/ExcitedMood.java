package com.example.lonelytwitter;

import java.util.Date;

public class ExcitedMood extends CurrentMood {
    public ExcitedMood() {
        super();
    }

    public ExcitedMood(Date moodDate) {
        super(moodDate);
    }

    @Override
    public String getMood() {
        return "Excited";
    }
}
