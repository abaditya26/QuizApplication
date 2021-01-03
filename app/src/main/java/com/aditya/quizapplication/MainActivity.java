package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.quizapplication.Adapters.AdapterOptions;
import com.aditya.quizapplication.Models.ModelOptions;
import com.aditya.quizapplication.Models.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    TextView welcomeUserText;
    RecyclerView optionsRecycler;

    ProgressDialog progressDialog;
    ModelUser currentUserData;
    List<ModelOptions> optionsList;
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

        optionsRecycler= findViewById(R.id.optionsRecycler);
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
                }else {
                    currentUserData = new ModelUser(user.getUid(),"","","");
                    //TODO: navigate to edit profile
                }
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error => "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        welcomeUserText.setText("Welcome "+currentUserData.getName());
        optionsList = new ArrayList<>();
        optionsList.add(new ModelOptions("Attempt Quiz","Attempt a new quiz","default","user"));
        optionsList.add(new ModelOptions("Previous Attempted Quiz","View Previous attempted quiz","default","user"));
        optionsList.add(new ModelOptions("New Quiz","Create a new Quiz","default","creator"));
        optionsList.add(new ModelOptions("Your Quiz","View Old Created Quiz","default","creator"));
        optionsList.add(new ModelOptions("Manage Users","Manage the users","default","admin"));
        optionsList.add(new ModelOptions("LogOut","Logout current user","default","user"));

        optionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        optionsRecycler.setAdapter(new AdapterOptions(this, optionsList));
    }
}