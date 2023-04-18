package com.cemcoma.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class rankingsActivity extends AppCompatActivity {
    private TextView winnerText;
    private FirebaseFirestore mFirestore;
    private Button hofButton;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);

        mFirestore = FirebaseFirestore.getInstance();

        winnerText =(TextView) findViewById(R.id.winnerTextView);

        query = mFirestore.collection("Users").orderBy("eloVsComputer", Query.Direction.DESCENDING).limit(10);

        hofButton = (Button) findViewById(R.id.HoFButton);
        hofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rankingsActivity.this ,HofActivity.class);
                startActivity(intent);
            }
        });

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String str = "";
            int placement = 1;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot docSnap : queryDocumentSnapshots) {
                    str += placement + "." + docSnap.get("name").toString() + "(" + docSnap.get("eloVsComputer").toString() +")"+ "\n";
                    placement++;
                }
                winnerText.setText(str);
                winnerText.setTextColor(Color.BLACK);
            }
        });

    }
}