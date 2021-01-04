package com.aditya.quizapplication.Creator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aditya.quizapplication.Adapters.AdapterQuestions;
import com.aditya.quizapplication.Models.ModelQuestions;
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

public class OldQuestionsActivity extends AppCompatActivity {

    RecyclerView recyclerQuestions;
    FirebaseAuth auth;
    DatabaseReference reference;

    List<ModelQuestions> questions;

    String quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_questions);

        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        tool.setNavigationOnClickListener(v -> finish());
        tool.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        quizId = getIntent().getStringExtra("quizId");
        if (quizId == null){
            Toast.makeText(this, "Error Generated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();

        recyclerQuestions = findViewById(R.id.questionsRecycler);
        recyclerQuestions.setLayoutManager(new LinearLayoutManager(this));
        questions = new ArrayList<>();

        reference.child("Quiz").child(quizId).child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    questions.add(snapshot1.getValue(ModelQuestions.class));
                }
                recyclerQuestions.setAdapter(new AdapterQuestions(getApplicationContext(), questions));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.addQuestionButton){
            Intent intent = new Intent(this, AddNewQuestionActivity.class);
            intent.putExtra("id",quizId);
            startActivity(intent);
        }
        return true;
    }
}