package com.cemcoma.rps.onlineActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cemcoma.rps.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class challengesScreen extends AppCompatActivity {
    private FirebaseUser mUser;
    private String username;
    private int eloP1;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_screen);
        Intent intent = getIntent();
        eloP1 = intent.getIntExtra("eloVsPlayer",1);
        username = intent.getStringExtra("username");
        mUser = intent.getParcelableExtra("userFirebase");

        /* Not yet implemented...

        Query challenges = mFirestore.collection("Challenges").whereArrayContains("playerTwoUID", mUser.getUid());

        challenges.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String str = "";
            int order = 1;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot docSnap : queryDocumentSnapshots) {
                    str += order + "--" + docSnap.get("name").toString() + "(" + docSnap.get("eloVsComputer").toString() +")"+ "\n";
                    order++;
                }

            }
        });
        */

    }
}