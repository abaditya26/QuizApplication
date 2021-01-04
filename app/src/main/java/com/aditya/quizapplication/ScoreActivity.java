package com.aditya.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;

    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();
        scoreTextView = findViewById(R.id.score);
        String score = getIntent().getStringExtra("score");
        String totalQuestions = getIntent().getStringExtra("totalQuestions");
        scoreTextView.setText(score+" / "+totalQuestions);
    }
}