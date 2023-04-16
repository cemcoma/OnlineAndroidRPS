package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cemcoma.rps.rpsSystems.rockpaperscissors;
import  com.cemcoma.rps.rpsSystems.elo;

public class rpsActivity extends AppCompatActivity implements View.OnClickListener {
    private int eloPlayer = 1400;
    private int eloComputer = 1200;
    private Button rockButton, paperButton, scissorButton;
    private TextView resultView, computerChoiceTextView, eloView, computerEloView, waitView;
    private ImageView computerChoiceImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);
        rockButton = (Button) findViewById(R.id.rockButton);
        paperButton = (Button) findViewById(R.id.paperButton);
        scissorButton = (Button) findViewById(R.id.scissorsButton);

        waitView= (TextView) findViewById(R.id.waitView);
        resultView = (TextView) findViewById(R.id.resultView);
        computerChoiceTextView = (TextView) findViewById(R.id.computerChoiceTextView);
        eloView = (TextView) findViewById(R.id.eloView);
        eloView.setText("Your elo is" + eloPlayer);
        computerEloView = (TextView) findViewById(R.id.computerEloView);
        computerEloView.setText("Computer's elo is " + eloComputer);

        computerChoiceImageView = (ImageView) findViewById(R.id.computerChoiceImageView);

        rockButton.setOnClickListener(this);
        paperButton.setOnClickListener(this);
        scissorButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        waitView.setText("Playing the game...");

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
                eloComputer = eloFromMatch.getFinalEloP2();



                eloView.setText("Your new elo is " + eloPlayer);
                computerEloView.setText("Computer's new elo is " + eloComputer);
                resultView.setText(str);
                waitView.setText("");
            }
        }, 1000);

    }
}