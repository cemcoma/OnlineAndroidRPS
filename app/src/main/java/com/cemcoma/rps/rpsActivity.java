package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cemcoma.rps.rpsSystems.rockpaperscissors;
import  com.cemcoma.rps.rpsSystems.elo;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class rpsActivity extends AppCompatActivity implements View.OnClickListener {
    private int eloPlayer;
    private int eloComputer = 1000;
    private Button rockButton, paperButton, scissorButton;
    private TextView resultView, computerChoiceTextView, eloView, computerEloView, waitView;
    private ImageView computerChoiceImageView;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private DocumentReference mDocReference;
    private HashMap<String, Object> eloHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);

        Intent intent = getIntent();
        eloPlayer = intent.getIntExtra("eloVsComputer",0);
        mUser = intent.getParcelableExtra("userFirebase");

        rockButton = (Button) findViewById(R.id.rockButton);
        paperButton = (Button) findViewById(R.id.paperButton);
        scissorButton = (Button) findViewById(R.id.scissorsButton);

        waitView= (TextView) findViewById(R.id.waitView);
        resultView = (TextView) findViewById(R.id.resultView);
        computerChoiceTextView = (TextView) findViewById(R.id.computerChoiceTextView);
        eloView = (TextView) findViewById(R.id.eloView);
        eloView.setText("Your elo is " + eloPlayer);
        computerEloView = (TextView) findViewById(R.id.computerEloView);
        computerEloView.setText("Computer's elo is " + eloComputer);

        computerChoiceImageView = (ImageView) findViewById(R.id.computerChoiceImageView);

        rockButton.setOnClickListener(this);
        paperButton.setOnClickListener(this);
        scissorButton.setOnClickListener(this);

        mFirestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void onClick(View view) {
        //To make player wait
        waitView.setText("Playing the game...");
        rockButton.setEnabled(false);
        paperButton.setEnabled(false);
        scissorButton.setEnabled(false);


        new Handler().postDelayed(new Runnable() { //For delay
            @Override
            public void run() {
                String str = "";
                int userSelection = -1; //will always change

                if(view.getId() == rockButton.getId()) {
                    userSelection = 0;
                } else if (view.getId() == paperButton.getId()) {
                    userSelection = 1;
                } else if (view.getId() == scissorButton.getId()) {
                    userSelection = 2;
                }


                rockpaperscissors match = new rockpaperscissors(userSelection);
                switch (match.getComputerChoice()) {
                    case (0):
                        computerChoiceTextView.setText("Computer chose rock!");
                        computerChoiceImageView.setBackgroundResource(R.drawable.computer_rock);
                        break;
                    case (1):
                        computerChoiceTextView.setText("Computer chose paper!");
                        computerChoiceImageView.setBackgroundResource(R.drawable.computer_paper);
                        break;
                    case (2):
                        computerChoiceTextView.setText("Computer chose scissors!");
                        computerChoiceImageView.setBackgroundResource(R.drawable.computer_scissors);
                        break;
                }

                elo eloFromMatch = new elo(eloPlayer,eloComputer,match.didPlayerWin());
                switch (match.didPlayerWin()) {
                    case (1):  str = "Player Won!";  break;
                    case (0):  str = "Draw..."; break;
                    case (-1): str = "Computer Won!"; break;
                }

                eloPlayer = eloFromMatch.getFinalEloP1();
                //eloComputer = eloFromMatch.getFinalEloP2();



                eloView.setText("Your new elo is " + eloPlayer);
                computerEloView.setText("Computer's new elo is " + eloComputer);
                resultView.setText(str);

                //To set everything to starting position
                waitView.setText("");
                rockButton.setEnabled(true);
                paperButton.setEnabled(true);
                scissorButton.setEnabled(true);
                changeEloInDatabase();
            }
        }, 1000);

    }

    private void changeEloInDatabase() {
        if(eloPlayer > 0) {
            eloHashMap = new HashMap<>();
            eloHashMap.put("eloVsComputer", eloPlayer);
            mDocReference = mFirestore.collection("Users").document(mUser.getUid());
            mDocReference.update(eloHashMap);
        }
    }
}