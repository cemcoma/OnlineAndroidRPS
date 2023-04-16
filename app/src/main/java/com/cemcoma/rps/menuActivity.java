package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class menuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button playButton, playOnlineButton;
    private String username;
    private int eloVsComputer, eloVsPlayer;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private DocumentReference mDocReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent loginIntent = getIntent();

        mUser = loginIntent.getParcelableExtra("userFirebase");
        // mReference = FirebaseDatabase.getInstance("https://rps-cemtunay-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(mUser.getUid());
        mFirestore = FirebaseFirestore.getInstance();

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        playOnlineButton = (Button) findViewById(R.id.playOnlineButton);
        playOnlineButton.setOnClickListener(this);

        setValues();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == playButton.getId()) {
            Intent intentToPlayComputer = new Intent(menuActivity.this, rpsActivity.class);
            intentToPlayComputer.putExtra("eloVsComputer", eloVsComputer);
            intentToPlayComputer.putExtra("userFirebase", mUser);
            startActivity(intentToPlayComputer);
        } else if (view.getId() == playOnlineButton.getId()) {
            Intent intentToPlayOnline = new Intent(menuActivity.this, rpsOnlineActivity.class);
            intentToPlayOnline.putExtra("eloVsPlayer", eloVsPlayer);
            intentToPlayOnline.putExtra("userFirebase", mUser);
            intentToPlayOnline.putExtra("username", username);
            startActivity(intentToPlayOnline);
        }
    }

    private void setValues (){
        mDocReference = mFirestore.collection("Users").document(mUser.getUid());
        mDocReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    username = (String) documentSnapshot.getData().get("name");
                    eloVsComputer = (int) Math.round((long)documentSnapshot.getData().get("eloVsComputer"));
                    eloVsPlayer = (int) Math.round((long)documentSnapshot.getData().get("eloVsPlayer"));
            }
        });
    }
}