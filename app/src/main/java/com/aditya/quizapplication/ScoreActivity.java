package com.aditya.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ScoreActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;

    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        tool.setNavigationOnClickListener(v -> finish());
        tool.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

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