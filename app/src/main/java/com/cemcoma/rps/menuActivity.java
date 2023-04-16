package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

public class menuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button playButton;
    private String username;
    private int eloVsComputer, eloVsPlayers;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent loginIntent = getIntent();
        mUser = loginIntent.getParcelableExtra("user");


        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == playButton.getId()) {
            Intent intentToPlayComputer = new Intent(menuActivity.this, rpsActivity.class);
            intentToPlayComputer.putExtra("username", username);
            startActivity(intentToPlayComputer);
        }
    }
}