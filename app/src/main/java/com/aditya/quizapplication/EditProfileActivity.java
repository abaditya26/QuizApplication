package com.aditya.quizapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aditya.quizapplication.Models.ModelUser;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameInput, phoneInput;
    CircleImageView iconInput;
    Button saveButton;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser user;
    StorageReference mStorageRef;
    ProgressDialog progressDialog;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        nameInput = findViewById(R.id.editProfileName);
        phoneInput = findViewById(R.id.editProfilePhone);
        iconInput = findViewById(R.id.editProfileIcon);
        saveButton = findViewById(R.id.editProfileSaveButton);

        androidx.appcompat.widget.Toolbar tool = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        tool.setNavigationOnClickListener(v -> finish());
        tool.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Processing Request");
        progressDialog.setCancelable(false);
        progressDialog.show();
        reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setData(Objects.requireNonNull(snapshot.getValue(ModelUser.class)));
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(EditProfileActivity.this, "Error => "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        saveButton.setOnClickListener(v -> {
            String saveBtnText = saveButton.getText().toString();
            if (saveBtnText.equals("SAVE")){
                //save data and disable inputs
                saveData();
                disableInputs();
            }else{
                //ready to edit data
                enableInputs();
            }
        });

        iconInput.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);
        });
    }

    private void saveData() {
        HashMap<String, Object> data = new HashMap<>();
        String name, phone;
        name = nameInput.getText().toString();
        phone = phoneInput.getText().toString();
        if (name.equals("")){
            nameInput.setError("Required");
            return;
        }
        if (phone.equals("")){
            phoneInput.setError("Required");
            return;
        }
        data.put("name",name);
        data.put("phone",phone);
        reference.child("Users").child(user.getUid()).updateChildren(data).addOnCompleteListener(task -> Toast.makeText(EditProfileActivity.this, "Profile Updated SuccessFully", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Error => "+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void setData(ModelUser value) {
        nameInput.setText(value.getName());
        phoneInput.setText(value.getPhone());
        if (!value.getProfileImage().equals("default")){
            Glide.with(this).load(value.getProfileImage()).into(iconInput);
        }
        disableInputs();
    }

    @SuppressLint("SetTextI18n")
    private void enableInputs() {
        nameInput.setEnabled(true);
        phoneInput.setEnabled(true);
        saveButton.setText("SAVE");
    }

    @SuppressLint("SetTextI18n")
    private void disableInputs() {
        nameInput.setEnabled(false);
        phoneInput.setEnabled(false);
        saveButton.setText("EDIT");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Image Uploading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            final String randomkey = UUID.randomUUID().toString();
            StorageReference uploadImage = mStorageRef.child("images/"+randomkey);

            uploadImage.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                        task.addOnSuccessListener(uri -> {
                            String imageUrl=uri.toString();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                            reference.child("profileImage").setValue(imageUrl);
                        });

                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload"+exception, Toast.LENGTH_SHORT).show();
                    }).addOnProgressListener(snapshot -> {
                double progresspercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading :- "+(int)progresspercent+" %");
            });
        }else{
            Toast.makeText(this, "Operation Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}