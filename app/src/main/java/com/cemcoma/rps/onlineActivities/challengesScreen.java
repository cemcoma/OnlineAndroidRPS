package com.cemcoma.rps.onlineActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cemcoma.rps.rpsSystems.*;
import com.cemcoma.rps.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;


public class challengesScreen extends AppCompatActivity implements View.OnClickListener{
    private FirebaseUser mUser;
    private String username, UID2;
    private int eloP1, eloP2;
    private FirebaseFirestore mFirestore;
    private Button searchChallengeButton;
    private TextView challengersTextView;
    private EditText chalengerSearcherEditText;
    private RadioButton rockButton, paperButton, scissorsButton;
    private Query challenges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_screen);
        Intent intent = getIntent();
        eloP1 = intent.getIntExtra("eloVsPlayer",1); // the one opening the page
        username = intent.getStringExtra("username");
        mUser = intent.getParcelableExtra("userFirebase");

        searchChallengeButton = (Button) findViewById(R.id.challengeSearcherButton);
        searchChallengeButton.setOnClickListener(this);
        chalengerSearcherEditText = (EditText) findViewById(R.id.challengeSearcherEditText);
        challengersTextView = (TextView) findViewById(R.id.challengesTextView);
        mFirestore = FirebaseFirestore.getInstance();

        rockButton     = (RadioButton) findViewById(R.id.rockRadioButton);
        paperButton    = (RadioButton) findViewById(R.id.paperRadioButton);
        scissorsButton = (RadioButton) findViewById(R.id.scissorsRadioButton);

        rockButton.setChecked(true);

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
        Query allUsernames = mFirestore.collection("Users").whereEqualTo("name",challengeStr);

        getPlayerElo(allUsernames, challengeStr, new callback() {
            @Override
            public void callBackString(String calledBackString) {
                UID2 = calledBackString;
            }

            @Override
            public void callBackInt(int callBack) {
                eloP2 = callBack;
            }
        });

        challenges.whereEqualTo("playerOneName", challengeStr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot docSnap : queryDocumentSnapshots) {
                    if(docSnap.exists()) {
                        rockpaperscissors2players rps = new rockpaperscissors2players(getPlayerSelection(), Integer.parseInt(docSnap.get("playerOneSelection").toString()));
                        elo elo = new elo(eloP1,eloP2, rps.didPlayerWin());
                        setPlayerElos(elo.getFinalEloP1(), elo.getFinalEloP2());
                        deleteChallenge(challengeStr);
                    }
                }
            }
        });

    }
    private void deleteChallenge(String challengerStr) {
        mFirestore.collection("Challenges").document(UID2 +"--" + mUser.getUid()).delete();
    }
    private int getPlayerSelection() {
        if(rockButton.isChecked()) {
            return 0;
        } else if(paperButton.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }
    private void setPlayerElos(int finalEloP1, int finalEloP2) {
        mFirestore.collection("Users").document(mUser.getUid()).update("eloVsPlayer", finalEloP1);
        mFirestore.collection("Users").document(UID2).update("eloVsPlayer", finalEloP2);
    }

    public void getPlayerElo(Query allchallengers, String challengerName , final callback callback) {
        allchallengers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot docSnap : queryDocumentSnapshots) {
                    if(docSnap.exists()) {
                        callback.callBackInt(Integer.parseInt(docSnap.get("eloVsPlayer").toString()));
                        callback.callBackString(docSnap.get("UID").toString());
                    }
                }
            }
        });
    }
}