package com.example.login;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tushar.dashboard.Dashboard;

public class Login extends AppCompatActivity {
    TextView signUpBtn;
    Button loginBtn;
    ImageView backBtn;
    EditText userNameData, passwordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        userNameData = findViewById(R.id.etUsernameLogin);
        passwordData = findViewById(R.id.etPassLogin);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this::loginUser);

        signUpBtn.setOnClickListener(view -> startActivity(new Intent(Login.this, SignActivity.class)));

        backBtn = findViewById(R.id.imageView2);
        backBtn.setOnClickListener(view -> finish());
    }

    private boolean validateUserName() {
        String val = userNameData.getText().toString();
        if (val.isEmpty()) {
            userNameData.setError("Field cannot be empty");
            return false;
        } else {
            userNameData.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordData.getText().toString();
        if (val.isEmpty()) {
            passwordData.setError("Field cannot be empty");
            return false;
        } else {
            passwordData.setError(null);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateUserName() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        String userEnteredUsername = userNameData.getText().toString().trim();
        String userEnteredPassword = passwordData.getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("userName").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userNameData.setError(null);
                    String passwordFromDB = snapshot.child(userEnteredUsername).child("pass").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        passwordData.setError(null);

                        String userNameFromDB = snapshot.child(userEnteredUsername).child("userName").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String mobileNoFromDB = snapshot.child(userEnteredUsername).child("mobileNo").getValue(String.class);


                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("username", userNameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("mobileNo", mobileNoFromDB);
                        intent.putExtra("pass", passwordFromDB);

                        startActivity(intent);


                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        passwordData.setError("Wrong password");
                        passwordData.requestFocus();
                    }
                } else {
                    userNameData.setError("Username does not exist");
                    userNameData.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}