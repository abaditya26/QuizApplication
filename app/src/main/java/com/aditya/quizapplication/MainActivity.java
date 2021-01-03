package com.aditya.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    TextView welcomeUserText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        //do other code
        user = auth.getCurrentUser();
        welcomeUserText=findViewById(R.id.welcomeUserText);


    }
}