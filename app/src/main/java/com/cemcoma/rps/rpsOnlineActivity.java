package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cemcoma.rps.onlineActivities.challengeOthersScreen;
import com.cemcoma.rps.onlineActivities.challengesScreen;
import com.google.firebase.auth.FirebaseUser;

public class rpsOnlineActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser mUser;
    private String username;
    private int eloP1;
    private Button challengesButton, challengeOthersButton;
    private TextView userEloViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps_online);

        challengesButton = (Button) findViewById(R.id.challengeViewerButton);
        challengesButton.setOnClickListener(this);
        challengeOthersButton = (Button) findViewById(R.id.challengeOthersButton);
        challengeOthersButton.setOnClickListener(this);



        Intent intent = getIntent();
        eloP1 = intent.getIntExtra("eloVsPlayer",1);
        username = intent.getStringExtra("username");
        mUser = intent.getParcelableExtra("userFirebase");
        userEloViewer = (TextView) findViewById(R.id.userEloViewerOnliceActiivity);
        userEloViewer.setText(username + "(" + eloP1 + ")");


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == challengesButton.getId()) {
            Intent intentToChallenges = new Intent(rpsOnlineActivity.this, com.cemcoma.rps.onlineActivities.challengesScreen.class);
            intentToChallenges.putExtra("eloVsPlayer",eloP1);
            intentToChallenges.putExtra("userFirebase", mUser);
            intentToChallenges.putExtra("username",username);
            startActivity(intentToChallenges);
        } else if (view.getId() == challengeOthersButton.getId()) {
            Intent intentToChallengeOthers = new Intent(rpsOnlineActivity.this, challengeOthersScreen.class);
            intentToChallengeOthers.putExtra("eloVsPlayer",eloP1);
            intentToChallengeOthers.putExtra("userFirebase", mUser);
            intentToChallengeOthers.putExtra("username",username);
            startActivity(intentToChallengeOthers);
        }
    }
}