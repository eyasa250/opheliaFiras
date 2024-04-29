package com.example.returnto0.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;  // Optional for error handling

import com.example.returnto0.R;
import com.example.returnto0.buttombar;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button bLogin;

    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        // Observe Login Result and handle success/failure
        mViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String loginResult) {
                if ("Login Successful".equals(loginResult)) {
                    String token = mViewModel.getToken();
                    Log.d("CheckToken", "Retrieved Token: " + token);
                    // Now token is saved and we navigate to buttombar Activity
                    Intent intent = new Intent(LoginActivity.this, buttombar.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + loginResult, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
