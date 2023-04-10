package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {
    EditText etEmailSignUp, etUsernameSignUp, etPassSignUp, etPassConfirmSignUp;
    EditText etMobileNumber;
    Button signUp;
    String strEmail, strUsername, strPass, strMobileNo, strConfirmPass;
    TextView btnGoToLogin;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPassSignUp = findViewById(R.id.etPassSignUp);
        etUsernameSignUp = findViewById(R.id.etUsernameSignUp);
        etPassConfirmSignUp = findViewById(R.id.etPassSignUpConfirm);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        signUp = findViewById(R.id.signup);

        btnGoToLogin = findViewById(R.id.btn_GoToLogin);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignActivity.this, Login.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = etEmailSignUp.getText().toString().trim();
                strUsername = etUsernameSignUp.getText().toString().trim();
                strMobileNo = etMobileNumber.getText().toString().trim();
                strPass = etPassSignUp.getText().toString().trim();
                strConfirmPass = etPassConfirmSignUp.getText().toString().trim();
                if (validateConfirmPassword()) {
                    registerUser(strEmail, strUsername, strPass, strMobileNo);
                }
            }
        });

        backBtn = findViewById(R.id.imageView3);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void registerUser(String strEmail, String strUsername, String strPass, String strMobileNo) {
        if (!validateUsername() | !validateEmail() | !validatePassword() | !validateMobileNo()) {
            return;
        }
        //ith changes krache ahet
        UserHelperClass user = new UserHelperClass(strEmail, strUsername, strPass, strMobileNo);
        DatabaseHelperClass db = new DatabaseHelperClass();
        db.addUser(user);
    }

    private boolean validateUsername() {
        String val = etUsernameSignUp.getText().toString().trim();
        final String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            etUsernameSignUp.setError("Field cannot be empty");
            return false;
        } else if (val.length() <= 6) {
            etUsernameSignUp.setError("Username too short");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            etUsernameSignUp.setError("Username should not contain whitespaces");
            return false;
        } else {
            etUsernameSignUp.setError(null);
            return true;
        }
    }

    private boolean validateMobileNo() {
        String val = etMobileNumber.getText().toString().trim();
        final String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            etMobileNumber.setError("Field cannot be empty");
            return false;
        } else if (val.length() != 10) {
            etMobileNumber.setError("Enter a 10 digit mobile number");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            etMobileNumber.setError("Phones numbers should not contain whitespaces");
            return false;
        } else {
            etMobileNumber.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = etEmailSignUp.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            etEmailSignUp.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            etEmailSignUp.setError("Invalid email address");
            return false;
        } else {
            etEmailSignUp.setError(null);
            return true;
        }
    }


    private boolean validatePassword() {
        String val = etPassSignUp.getText().toString().trim();
        if (val.isEmpty()) {
            etPassSignUp.setError("Field cannot be empty");
            return false;
        } else if (val.length() <= 6) {
            etPassSignUp.setError("Password too short");
            return false;
        } else {
            etPassSignUp.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String val = etPassSignUp.getText().toString().trim();
        String val1 = etPassConfirmSignUp.getText().toString().trim();
        if (!val.equals(val1)) {
            etPassConfirmSignUp.setError("Passwords do no match");
            return false;
        } else {
            etPassConfirmSignUp.setError(null);
            return true;
        }
    }

}
