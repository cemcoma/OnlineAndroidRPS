package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class playTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private int eloVsPlayer, eloVsComputer;
    private String username;
    private FirebaseUser mUser;
    private Button playButton, playOnlineButton;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_type);

        Intent intent = getIntent();
        mUser = intent.getParcelableExtra("userFirebase");

        mFirestore = FirebaseFirestore.getInstance();

        playButton = (Button) findViewById(R.id.playOfflineButton);
        playButton.setOnClickListener(this);

        playOnlineButton = (Button) findViewById(R.id.playOnlineButton);
        playOnlineButton.setOnClickListener(this);
        setValues();
    }


    @Override
    public void onClick(View view) {
        setValues();
        if (view.getId() == playButton.getId()) {
            Intent intentToPlayComputer = new Intent(playTypeActivity.this, rpsActivity.class);
            intentToPlayComputer.putExtra("eloVsComputer", eloVsComputer);
            intentToPlayComputer.putExtra("userFirebase", mUser);
            startActivity(intentToPlayComputer);
        } else if (view.getId() == playOnlineButton.getId()) {
            Intent intentToPlayOnline = new Intent(playTypeActivity.this, rpsOnlineActivity.class);
            intentToPlayOnline.putExtra("eloVsPlayer", eloVsPlayer);
            intentToPlayOnline.putExtra("userFirebase", mUser);
            intentToPlayOnline.putExtra("username", username);
            startActivity(intentToPlayOnline);
        }
        this.finish();
    }

    private void setValues (){
        mFirestore.collection("Users").document(mUser.getUid()).get().addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    eloVsComputer = Integer.parseInt(documentSnapshot.get("eloVsComputer").toString());
                    eloVsPlayer = Integer.parseInt(documentSnapshot.get("eloVsPlayer").toString());
                    username = documentSnapshot.get("name").toString();
                }
            }
        });

    }
}