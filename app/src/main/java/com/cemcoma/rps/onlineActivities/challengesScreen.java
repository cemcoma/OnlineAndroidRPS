package com.cemcoma.rps.onlineActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cemcoma.rps.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class challengesScreen extends AppCompatActivity implements View.OnClickListener{
    private FirebaseUser mUser;
    private String username;
    private int eloP1;
    private FirebaseFirestore mFirestore;
    private Button searchChallengeButton;
    private TextView challengersTextView;
    private EditText chalengerSearcherEditText;
    private Query challenges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_screen);
        Intent intent = getIntent();
        eloP1 = intent.getIntExtra("eloVsPlayer",1);
        username = intent.getStringExtra("username");
        mUser = intent.getParcelableExtra("userFirebase");

        searchChallengeButton = (Button) findViewById(R.id.challengeSearcherButton);
        searchChallengeButton.setOnClickListener(this);
        chalengerSearcherEditText = (EditText) findViewById(R.id.challengeSearcherEditText);
        challengersTextView = (TextView) findViewById(R.id.challengesTextView);
        mFirestore = FirebaseFirestore.getInstance();


        challenges = mFirestore.collection("Challenges").whereEqualTo("playerTwoUID", mUser.getUid());

        setText(new callback() {
            @Override
            public void callBackString(String calledBackString) {
                challengersTextView.setText(calledBackString);
            }
            @Override
            public void callBackInt(int callBack) {}
        });
    }
    private void setText(final callback callback) {
        challenges.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String str = "";
            int order = 1;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot docSnap : queryDocumentSnapshots) {
                    if(docSnap.exists()) {
                        str += order + "--" + docSnap.get("playerOneName") + "(" + docSnap.get("eloPlayerOne") +")"+ "\n";
                        order++;
                        callback.callBackString(str);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
       String challengeStr = chalengerSearcherEditText.getText().toString();
       Query allUsernames = mFirestore.collection("Users").whereArrayContains("name",challengeStr);
       allUsernames.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               //TODO: check if challenge exists
           }
       });

    }
}