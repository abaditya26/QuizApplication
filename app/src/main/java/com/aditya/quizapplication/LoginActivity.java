package com.aditya.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        emailInput = findViewById(R.id.loginEmail);
        passwordInput = findViewById(R.id.loginPassword);

    }

    public void navigateRegister(View view) {
    }

    public void loginUser(View view) {
        String email, password;
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        if(!validateInput(email, password)){
            return;
        }
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Failed => "+e, Toast.LENGTH_SHORT).show());
    }

    private boolean validateInput(String email, String password) {
        if (email.equals("")){
            emailInput.setError("Required Field");
            return false;
        }
        if (password.equals("")){
            passwordInput.setError("Required Field");
            return false;
        }
        return true;
    }
}