package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.quizapplication.Adapters.AdapterOptions;
import com.aditya.quizapplication.Models.ModelOptions;
import com.aditya.quizapplication.Models.ModelUser;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    TextView welcomeUserText;
    RecyclerView optionsRecycler;
    CircleImageView userIcon;

    ProgressDialog progressDialog;
    ModelUser currentUserData;
    List<ModelOptions> optionsList;

    public static String role="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        //do other code
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        optionsList = new ArrayList<>();

        optionsRecycler= findViewById(R.id.optionsRecycler);
        welcomeUserText=findViewById(R.id.welcomeUserText);
        userIcon = findViewById(R.id.userIcon);

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
                    if (currentUserData != null) {
                        role = currentUserData.getRole();
                    }
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
        optionsList.clear();
        welcomeUserText.setText("Welcome "+currentUserData.getName());
        if (!currentUserData.getProfileImage().equals("default")){
            Glide.with(this).load(currentUserData.getProfileImage()).into(userIcon);
        }
        switch (role){
            case "admin":
                optionsList.add(new ModelOptions("Manage Users","Manage the users","default","admin"));
            case "creator":
                optionsList.add(new ModelOptions("New Quiz","Create a new Quiz","default","creator"));
                optionsList.add(new ModelOptions("Your Quiz","View Old Created Quiz","default","creator"));
        }
        optionsList.add(new ModelOptions("Attempt Quiz","Attempt a new quiz","default","user"));
        optionsList.add(new ModelOptions("Previous Attempted Quiz","View Previous attempted quiz","default","user"));

        optionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        optionsRecycler.setAdapter(new AdapterOptions(this, optionsList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutButton) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else if(item.getItemId() == R.id.profileSettingsButton){
            //navigate to settings
        }else{
            Toast.makeText(this, "No listener defined", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}