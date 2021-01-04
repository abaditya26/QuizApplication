package com.aditya.quizapplication.Creator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aditya.quizapplication.Adapters.AdapterQuiz;
import com.aditya.quizapplication.Models.ModelQuiz;
import com.aditya.quizapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreatedQuizsActivity extends AppCompatActivity {

    RecyclerView createdQuizRecycler;

    FirebaseAuth auth;
    DatabaseReference reference;

    List<ModelQuiz> quizs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_quizs);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();

        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        tool.setNavigationOnClickListener(v -> finish());
        tool.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        createdQuizRecycler = findViewById(R.id.createdQuizRecycler);
        createdQuizRecycler.setLayoutManager(new LinearLayoutManager(this));
        quizs = new ArrayList<>();
        reference.child("Quiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quizs.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ModelQuiz quiz = snapshot1.getValue(ModelQuiz.class);
                    if(quiz!=null){
                        if (quiz.getOwnerId().equals(auth.getCurrentUser().getUid())){
                            quizs.add(quiz);
                        }
                    }
                }
                createdQuizRecycler.setAdapter(new AdapterQuiz(quizs,getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}