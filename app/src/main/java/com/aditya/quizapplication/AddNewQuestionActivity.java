package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aditya.quizapplication.Models.ModelQuestions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewQuestionActivity extends AppCompatActivity {

    String quizId;

    FirebaseAuth auth;
    DatabaseReference reference;

    ProgressDialog progressDialog;

    int questionCount = 0;

    EditText questionInput, option1Input, option2Input, option3Input, option4Input;
    RadioButton radioOption1, radioOption2, radioOption3, radioOption4;
    RadioGroup radioOptionGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_question);

        quizId = getIntent().getStringExtra("id");
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching data");
        progressDialog.setMessage("Processing request");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.child("Quiz").child(quizId).child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionCount=0;
                for (DataSnapshot ignored : snapshot.getChildren()){
                    questionCount++;
                }
                try {
                    progressDialog.cancel();
                }catch (Exception ignored){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //initiate views
        questionInput = findViewById(R.id.questionInput);
        option1Input = findViewById(R.id.option_1_input);
        option2Input = findViewById(R.id.option_2_input);
        option3Input = findViewById(R.id.option_3_input);
        option4Input = findViewById(R.id.option_4_input);
        radioOption1 = findViewById(R.id.optionRadio1);
        radioOption2 = findViewById(R.id.optionRadio2);
        radioOption3 = findViewById(R.id.optionRadio3);
        radioOption4 = findViewById(R.id.optionRadio4);
        radioOptionGroup = findViewById(R.id.radioOptionGroup);

        option1Input.addTextChangedListener(new TextWatcherAdapter(radioOption1));
        option2Input.addTextChangedListener(new TextWatcherAdapter(radioOption2));
        option3Input.addTextChangedListener(new TextWatcherAdapter(radioOption3));
        option4Input.addTextChangedListener(new TextWatcherAdapter(radioOption4));
    }

    public void addQuestion(View view) {
        int selectedId = radioOptionGroup.getCheckedRadioButtonId();
        RadioButton selected = (RadioButton) findViewById(selectedId);
        String option1, option2, option3, option4, question;
        option1=option1Input.getText().toString();
        option2=option2Input.getText().toString();
        option3=option3Input.getText().toString();
        option4=option4Input.getText().toString();
        question=questionInput.getText().toString();
        if(!validateData(question, option1, option2, option3, option4, selectedId)){
            return;
        }
        String answer = selected.getText().toString();
        ModelQuestions questionModel = new ModelQuestions(question,option1,option2,option3,option4,answer);
        reference.child("Quiz").child(quizId).child("Questions").child(questionCount+"").setValue(questionModel).addOnCompleteListener(task -> {
            Toast.makeText(AddNewQuestionActivity.this, "Question Added", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(AddNewQuestionActivity.this, "Error => "+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private boolean validateData(String question, String option1, String option2, String option3, String option4, int selectedId) {
        if (selectedId==-1){
            radioOption1.setError("Should be selected");
            radioOption2.setError("Should be selected");
            radioOption3.setError("Should be selected");
            radioOption4.setError("Should be selected");
            return false;
        }
        if (question.equals("")){
            questionInput.setError("Required");
            return false;
        }
        if (option1.equals("")){
            option1Input.setError("Required");
            return false;
        }
        if (option2.equals("")){
            option2Input.setError("Required");
            return false;
        }
        if (option3.equals("")){
            option3Input.setError("Required");
            return false;
        }
        if (option4.equals("")){
            option4Input.setError("Required");
            return false;
        }
        return true;
    }


    private class TextWatcherAdapter implements TextWatcher {
        RadioButton radioOption;
        TextWatcherAdapter(RadioButton radioOption){
            this.radioOption=radioOption;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            radioOption.setText(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}