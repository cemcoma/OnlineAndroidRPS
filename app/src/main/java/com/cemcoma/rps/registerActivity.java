package com.cemcoma.rps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton;
    private EditText usernameEditText ,emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        emailEditText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = (EditText) findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    private void register() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if( username.length() > 0 && email.length() > 0 && password.length() > 0) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(registerActivity.this, "User has been created", Toast.LENGTH_SHORT).show();
                        Intent intentToMenu = new Intent(registerActivity.this, menuActivity.class);
                        startActivity(intentToMenu);
                    } else {
                        Toast.makeText(registerActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, "Fields can not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        register();
    }
}