package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aditya.quizapplication.Adapters.AdapterAttemptedQuiz;
import com.aditya.quizapplication.Models.ModelAttemptedQuiz;
import com.aditya.quizapplication.Models.ModelQuiz;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PreviousAttemptedQuizActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseAuth auth;
    DatabaseReference reference;

    List<ModelAttemptedQuiz> quizList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_attempted_quiz);

        recyclerView = findViewById(R.id.attemptedQuizRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        quizList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }


        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        tool.setNavigationOnClickListener(v -> finish());
        tool.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("AttemptedQuiz").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quizList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    quizList.add(snapshot1.getValue(ModelAttemptedQuiz.class));
                }
                recyclerView.setAdapter(new AdapterAttemptedQuiz(quizList,getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}