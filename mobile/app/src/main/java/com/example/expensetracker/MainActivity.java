package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.expensetracker.model.AuthResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractAPIListener;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private Model model;
    private TextInputEditText ietxtUsername, ietxtPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ietxtUsername = findViewById(R.id.ietxtUsername);
        ietxtPassword = findViewById(R.id.ietxtPassword);

        model = Model.getInstance(this.getApplication());
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
    }

    // Fired when login button clicked
    public void onBtnLoginClicked (View view) {
        // TODO: Form Validation! use the Pattern class
        // TODO: Disable btn
        model.login(ietxtUsername.getText().toString(), ietxtPassword.getText().toString(), new AbstractAPIListener() {
            @Override
            public void onLogin(AuthResponse authResponse) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", authResponse.getUsername());
                editor.putString("token", authResponse.getToken());
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                Toast.makeText(getApplication(), "Logged In Successfully!", Toast.LENGTH_LONG).show();
            }
        });
        // TODO: Enable button here so that it gets enabled in error cases
    }
}