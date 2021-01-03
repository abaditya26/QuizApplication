package com.aditya.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser user;

    EditText inputName, inputEmail, inputPhone, inputPassword, inputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference();

        inputName = findViewById(R.id.registerName);
        inputEmail = findViewById(R.id.registerEmail);
        inputPhone = findViewById(R.id.registerPhone);
        inputPassword = findViewById(R.id.registerPassword1);
        inputPasswordConfirm = findViewById(R.id.registerPassword2);

    }

    public void registerUser(View view) {
        String name, email, phone, password, passwordConfirm;
        name = inputName.getText().toString();
        email = inputEmail.getText().toString();
        phone = inputPhone.getText().toString();
        password = inputPassword.getText().toString();
        passwordConfirm = inputPasswordConfirm.getText().toString();

        if(!validateData(name, email, phone, password, passwordConfirm)){
            return;
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //do code to add in model and save it
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(RegisterUserActivity.this, "Error => "+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private boolean validateData(String name, String email, String phone, String password, String passwordConfirm) {
        if (name.equals("")){
            inputName.setError("Required Field");
            return false;
        }
        if (email.equals("")){
            inputEmail.setError("Required Field");
            return false;
        }
        if (phone.equals("")){
            inputPhone.setError("Required Field");
            return false;
        }
        if (password.equals("")){
            inputPassword.setError("Required Field");
            return false;
        }
        if (passwordConfirm.equals("")){
            inputPasswordConfirm.setError("Required Field");
            return false;
        }
        if (!password.equals(passwordConfirm)){
            inputPassword.setError("Password does not match");
            return false;
        }
        return true;
    }

    public void navigateLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}