package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.aditya.quizapplication.Models.ModelQuestions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    Button option1Button, option2Button, option3Button, option4Button;
    TextView questionTextView, index;

    List<ModelQuestions> questionsList;
    List<String> selected;
    String quizId;

    FirebaseAuth auth;
    DatabaseReference reference;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        option1Button = findViewById(R.id.option1);
        option2Button = findViewById(R.id.option2);
        option3Button = findViewById(R.id.option3);
        option4Button = findViewById(R.id.option4);
        questionTextView = findViewById(R.id.question);
        index = findViewById(R.id.questionIndicator);

        quizId = getIntent().getStringExtra("quizId");

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();
        questionsList = new ArrayList<>();

        reference.child("Quiz").child(quizId).child("Questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    questionsList.add(snapshot1.getValue(ModelQuestions.class));
                }
                setData(position);
                //perform
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setData(int position) {
        questionTextView.setText(questionsList.get(position).getQuestion());
        option1Button.setText(questionsList.get(position).getOption_1());
        option2Button.setText(questionsList.get(position).getOption_2());
        option3Button.setText(questionsList.get(position).getOption_3());
        option4Button.setText(questionsList.get(position).getOption_4());
    }

}