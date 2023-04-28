package com.cemcoma.rps.onlineActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cemcoma.rps.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class challengeOthersScreen extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser mUser;
    private String username;
    private int eloP1;
    private FirebaseFirestore mFirestore;
    private EditText challengeOthersEditText;
    private Button challengeButton;
    private RadioButton rockButton, paperButton, scissorsButton;
    private RadioGroup rpsGroup;
    private challengeMaker challengeMaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_others_screen);

        Intent intent = getIntent();
        eloP1 = intent.getIntExtra("eloVsPlayer",1);
        username = intent.getStringExtra("username");
        mUser = intent.getParcelableExtra("userFirebase");
        mFirestore = FirebaseFirestore.getInstance();

        challengeOthersEditText = (EditText) findViewById(R.id.challengeOthersEditText);
        challengeButton =(Button) findViewById(R.id.challengeButton);
        challengeButton.setOnClickListener(this);

        rockButton = (RadioButton) findViewById(R.id.rockRadioButton);
        paperButton = (RadioButton) findViewById(R.id.paperRadioButton);
        scissorsButton = (RadioButton) findViewById(R.id.scissorsRadioButton);

        rockButton.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        String p2Username = challengeOthersEditText.getText().toString();
        Query p2UsernameUsers = mFirestore.collection("Users").whereEqualTo("name", p2Username);
        p2UsernameUsers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i = 0;
                for(QueryDocumentSnapshot docSnap : queryDocumentSnapshots) {
                    challengeMaker = new challengeMaker(mUser.getUid(), docSnap.get("UID").toString(), getPlayerSelection());
                    i++;
                }
                Toast.makeText(challengeOthersScreen.this, i + " challenges has been created.", Toast.LENGTH_SHORT).show();

            }
        });
        finish();
    }
    private int getPlayerSelection() {
        if(rockButton.isChecked()) {
            return 0;
        }else if(paperButton.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

}