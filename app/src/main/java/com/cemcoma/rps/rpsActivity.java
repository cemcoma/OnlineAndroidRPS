package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cemcoma.rps.rpsSystems.rockpaperscissors;
import  com.cemcoma.rps.rpsSystems.elo;

public class rpsActivity extends AppCompatActivity implements View.OnClickListener {
    private int eloPlayer = 400;
    private int eloComputer = 200;
    private Button rockButton, paperButton, scissorButton;
    private TextView resultView, computerChoiceTextView, eloView;
    private ImageView computerChoiceImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);
        rockButton = (Button) findViewById(R.id.rockButton);
        paperButton = (Button) findViewById(R.id.paperButton);
        scissorButton = (Button) findViewById(R.id.scissorsButton);

        resultView = (TextView) findViewById(R.id.resultView);
        computerChoiceTextView = (TextView) findViewById(R.id.computerChoiceTextView);
        eloView = (TextView) findViewById(R.id.eloView);

        computerChoiceImageView = (ImageView) findViewById(R.id.computerChoiceImageView);

        rockButton.setOnClickListener(this);
        paperButton.setOnClickListener(this);
        scissorButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
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
        eloView.setText("Your new elo is " + eloPlayer);
        resultView.setText(str);
    }
}