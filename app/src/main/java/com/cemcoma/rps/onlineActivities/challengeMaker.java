package com.cemcoma.rps.onlineActivities;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class challengeMaker{
    private FirebaseFirestore mFirestore;
    private FirebaseUser playerOne, playerTwo;
    private String playerOneName, playerTwoName, playerOneUID, playerTwoUID;
    private int  eloPlayerOne, eloPlayerTwo;
    private HashMap<String, Object> challengeData;

    public challengeMaker(String playerOneUID, String playerTwoUID, int playerOneSelection) {
        this.playerOneUID = playerOneUID;
        this.playerTwoUID = playerTwoUID;
        challengeData = new HashMap<>();

        initializeVariables(new callback() {
            @Override
            public void callBackString(String calledBackString) {
                if(calledBackString.equals(playerOneName)) {
                    challengeData.put("playerOneName",playerOneName);
                } else if (calledBackString.equals(playerTwoName)) {
                    challengeData.put("playerTwoName", playerTwoName);
                }
            }
            @Override
            public void callBackInt(int callBack) {
                if(challengeData.containsKey("eloPlayerOne")) {
                    challengeData.put("eloPlayerTwo",eloPlayerTwo);
                    setChallenge(playerOneSelection);
                } else {
                    challengeData.put("eloPlayerOne", eloPlayerOne);
                }
            }
        });


    }

    private void initializeVariables(final callback callback) {
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").document(playerOneUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    eloPlayerOne = Integer.parseInt(documentSnapshot.get("eloVsPlayer").toString());
                    playerOneName = documentSnapshot.get("name").toString();
                    callback.callBackString(playerOneName);
                    callback.callBackInt(eloPlayerOne);
                }
            }
        });

        mFirestore.collection("Users").document(playerTwoUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    eloPlayerTwo = Integer.parseInt(documentSnapshot.getData().get("eloVsPlayer").toString());
                    playerTwoName = documentSnapshot.getData().get("name").toString();
                    callback.callBackString(playerTwoName);
                    callback.callBackInt(eloPlayerTwo);
                }
            }
        });

    }
    private void setChallenge(int playerOneSelection) {

        challengeData.put("eloPlayerOne",  eloPlayerOne);
        challengeData.put("eloPlayerTwo",  eloPlayerTwo);
        challengeData.put("playerOneUID",  playerOneUID);
        challengeData.put("playerTwoUID",  playerTwoUID);
        challengeData.put("playerOneSelection", playerOneSelection);

        mFirestore.collection("Challenges").document(playerOneUID + "--" + playerTwoUID).set(challengeData);
    }

}
