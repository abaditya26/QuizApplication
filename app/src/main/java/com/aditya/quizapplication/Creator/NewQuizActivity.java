package com.aditya.quizapplication.Creator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aditya.quizapplication.MainActivity;
import com.aditya.quizapplication.Models.ModelQuiz;
import com.aditya.quizapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NewQuizActivity extends AppCompatActivity {

    EditText inputQuizId, inputQuizName;
    FirebaseAuth auth;
    DatabaseReference reference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();

        inputQuizId = findViewById(R.id.newQuizId);
        inputQuizName = findViewById(R.id.newQuizName);

        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        tool.setNavigationOnClickListener(v -> finish());
        tool.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Processing Request");
        progressDialog.setCancelable(false);
    }

    public void validateAndAdd(View view) {
        String quizId, quizName, ownerId, ownerName;
        quizId = inputQuizId.getText().toString();
        quizName = inputQuizName.getText().toString();
        ownerId = auth.getCurrentUser().getUid();
        ownerName = MainActivity.userName;

        progressDialog.show();
        reference.child("Quiz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    ModelQuiz quiz =snapshot1.getValue(ModelQuiz.class);
                    if (quiz.getId().equals(quizId)){
                        inputQuizId.setError("Select another id");
                        progressDialog.cancel();
                        return;
                    }
                }
                saveData(quizId,quizName,ownerId,ownerName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Error => "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(String quizId, String quizName, String ownerId, String ownerName) {
        reference.child("Quiz").child(quizId).setValue(new ModelQuiz(quizId,quizName,ownerId,ownerName))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(NewQuizActivity.this, "Quiz Added", Toast.LENGTH_SHORT).show();
                        Intent addQuestion = new Intent(this, AddNewQuestionActivity.class);
                        addQuestion.putExtra("id",quizId);
                        startActivity(addQuestion);
                        finish();
                    }
                    progressDialog.cancel();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(NewQuizActivity.this, "Error => " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                });
    }
}