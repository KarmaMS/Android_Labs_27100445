package com.example.lonelytwitter;

import java.util.Date;

public class AngryMood extends CurrentMood {
    public AngryMood() {
        super();
    }

    public AngryMood(Date moodDate) {
        super(moodDate);
    }

    @Override
    public String getMood() {
        return "Angry";
    }
}
