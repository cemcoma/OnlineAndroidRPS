package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class menuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button playButton, rankingViewButton;
    private String username;
    private int eloVsComputer, eloVsPlayer;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent loginIntent = getIntent();

        mUser = loginIntent.getParcelableExtra("userFirebase");
        mFirestore = FirebaseFirestore.getInstance();

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        setValues();

        rankingViewButton = (Button) findViewById(R.id.rankingViewButton);
        rankingViewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setValues();
        if (view.getId() == playButton.getId()) {
            Intent intentToPlay = new Intent(menuActivity.this, playTypeActivity.class);
            intentToPlay.putExtra("username",username);
            intentToPlay.putExtra("userFirebase",mUser);
            intentToPlay.putExtra("eloVsComputer",eloVsComputer);
            intentToPlay.putExtra("eloVsPlayer",eloVsPlayer);
            startActivity(intentToPlay);

        } else if (view.getId() == rankingViewButton.getId()) {
            Intent intentToRankings = new Intent(menuActivity.this, rankingsActivity.class );
            startActivity(intentToRankings);
        }
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