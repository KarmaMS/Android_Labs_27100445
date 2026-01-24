package com.example.lonelytwitter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImportantTweet t1 = new ImportantTweet("");
        NormalTweet t2 = new NormalTweet("This is a normal tweet");
        ArrayList<Tweet> tweetList = new ArrayList<>();
        tweetList.add(t1);
        tweetList.add(t2);

        AngryMood m1 = new AngryMood();
        ExcitedMood m2 = new ExcitedMood();
        ArrayList<CurrentMood> moodList = new ArrayList<>();
        moodList.add(m1);
        moodList.add(m2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}