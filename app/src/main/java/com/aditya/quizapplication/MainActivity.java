package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.quizapplication.Models.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    TextView welcomeUserText;
    ProgressDialog progressDialog;
    ModelUser currentUserData;
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
        reference = FirebaseDatabase.getInstance().getReference();

        welcomeUserText=findViewById(R.id.welcomeUserText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging IN");
        progressDialog.setMessage("Processing Your Request");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("uid").exists()){
                    currentUserData = snapshot.getValue(ModelUser.class);
                    setView();
                    progressDialog.cancel();
                }else {
                    currentUserData = new ModelUser(user.getUid(),"","","");
                    //TODO: navigate to edit profile
                    progressDialog.cancel();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error => "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });


    }

    private void setView() {
        welcomeUserText.setText("Welcome "+currentUserData.getName());
    }
}