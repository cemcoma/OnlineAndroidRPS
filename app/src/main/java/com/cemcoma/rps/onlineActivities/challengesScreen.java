package com.cemcoma.rps.onlineActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cemcoma.rps.R;
import com.google.firebase.auth.FirebaseUser;


public class challengesScreen extends AppCompatActivity {
    private FirebaseUser mUser;
    private String username;
    private int eloP1, eloP2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_screen);
        Intent intent = getIntent();
        eloP1 = intent.getIntExtra("eloVsPlayer",1);
        username = intent.getStringExtra("username");
        mUser = intent.getParcelableExtra("userFirebase");

    }
}