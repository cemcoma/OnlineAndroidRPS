package com.cemcoma.rps.onlineActivities;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class challengeMaker {
    private FirebaseFirestore mFirestore;
    private FirebaseUser playerOne, playerTwo;
    private String playerOneName, playerTwoName, playerOneUID, playerTwoUID;
    private int  eloPlayerOne, eloPlayerTwo;

    public challengeMaker(String playerOneUID, String playerTwoUID, int playerOneSelection) {
        this.playerOneUID = playerOneUID;
        this.playerTwoUID = playerTwoUID;
        initializeVariables();
        setChallenge(playerOneSelection);
    }

    private void initializeVariables() {
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").document(playerOneUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                eloPlayerOne = Integer.parseInt(documentSnapshot.get("eloVsPlayer").toString());
                playerOneName = documentSnapshot.get("name").toString();
            }
        });

        mFirestore.collection("Users").document(playerTwoUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                eloPlayerTwo = Integer.parseInt(documentSnapshot.get("eloVsPlayer").toString());
                playerTwoName = documentSnapshot.get("name").toString();
            }
        });

    }
    private void setChallenge(int playerOneSelection) {
        HashMap<String, Object> challengeData = new HashMap<>();
        challengeData.put("eloPlayerOne",  eloPlayerOne);
        challengeData.put("eloPlayerTwo",  eloPlayerTwo);
        challengeData.put("playerOneName", playerOneName);
        challengeData.put("playerTwoName", playerTwoName);
        challengeData.put("playerOneUID",  playerOneUID);
        challengeData.put("playerTwoUID",  playerTwoUID);
        challengeData.put("playerOneSelection", playerOneSelection);

        mFirestore.collection("Challenges").document(playerOneUID + "--" + playerTwoUID).set(challengeData);
    }

}
