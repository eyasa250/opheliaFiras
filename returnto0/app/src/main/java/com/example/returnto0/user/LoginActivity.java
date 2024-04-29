package com.example.returnto0.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.returnto0.R;

public class LoginActivity extends AppCompatActivity {
    TextView tvDrinkName, tvLoginResult;
    EditText etEmail, etPassword;
    Button bLogin, bGetDrink;

    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvLoginResult = findViewById(R.id.tvLoginResult);
        bLogin = findViewById(R.id.bLogin);
      //  bGetDrink = findViewById(R.id.bGetDrink);  // Find the button here

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvLoginResult.setText(s);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

    }

}
