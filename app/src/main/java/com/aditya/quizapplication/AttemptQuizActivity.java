package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aditya.quizapplication.Models.ModelQuiz;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttemptQuizActivity extends AppCompatActivity {

    LinearLayout searchQuizResult;
    EditText quizIdInput;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_quiz);

        searchQuizResult = findViewById(R.id.searchQuizResult);
        searchQuizResult.setAlpha(0f);

        quizIdInput = findViewById(R.id.quizIdInput);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

    }

    public void searchQuiz(View view) {
        String quizId = quizIdInput.getText().toString();
        if (quizId.equals("")){
            quizIdInput.setError("Required");
            return;
        }
        reference.child("Quiz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    ModelQuiz quiz =snapshot1.getValue(ModelQuiz.class);
                    if (quiz.getId().equals(quizId)){
                        setData(quiz);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AttemptQuizActivity.this, "Error => "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData(ModelQuiz quiz) {

    }
}