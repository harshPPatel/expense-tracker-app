package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.expensetracker.model.AuthResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Model model;
    private TextInputEditText ietxtUsername, ietxtPassword;
    private Button btnLogin, btnSignUp;
    private SharedPreferences sharedPreferences;
    private boolean isValidUsername = false, isValidPassword = false;

    /**
     * Fired when MainActivity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ietxtUsername = findViewById(R.id.ietxtUsername);
        ietxtPassword = findViewById(R.id.ietxtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        model = Model.getInstance(this.getApplication());
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        // Adding validators to the text fields
        ietxtUsername.addTextChangedListener(new UserNameValidator());
        ietxtPassword.addTextChangedListener(new PasswordValidator());

        // TODO: Implement Dark Mode!

        // fetching token from sharedPreference
        String token = sharedPreferences.getString("token", "");
        // Validating the token if it already exists in the shared preference and navigating
        // user to dashboard if it is valid one
        if (!token.isEmpty()) {
            btnLogin.setEnabled(false);
            btnSignUp.setEnabled(false);
            btnLogin.setText(R.string.btnLogin_loading_text);
            model.validateToken(token, new AbstractListener() {
                @Override
                public void onRequestFailed(JSONObject jsonError) {
                    Toast.makeText(getApplication(), "Invalid Token. Please Login again!", Toast.LENGTH_LONG).show();
                    resetSharedPreference();
                    btnLogin.setEnabled(true);
                    btnSignUp.setEnabled(true);
                    btnLogin.setText(R.string.btnLogin_text);
                }

                @Override
                public void onValidToken(boolean result) {
                    navigateToDashboard();
                }
            });
        }

        // Adding username to the field if user comes from sign up
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username != null) {
            ietxtUsername.setText(username);
        }
    }

    /**
     * Fired when btnLogin is clicked in the view
     * @param view btnLogin
     */
    public void onBtnLoginClicked(View view) {
        btnLogin.setEnabled(false);
        btnLogin.setText(R.string.btnLogin_loading_text);

        model.login(ietxtUsername.getText().toString(), ietxtPassword.getText().toString(), new AbstractListener() {
            @Override
            public void onLogin(AuthResponse authResponse) {
                saveToSharedPreference(authResponse);
                navigateToDashboard();
                btnLogin.setEnabled(true);
                btnLogin.setText(R.string.btnLogin_text);
            }

            @Override
            public void onRequestFailed(JSONObject jsonError) {
                try {
                    // EXTRA TODO: Custom Toast with error theme
                    Toast.makeText(getApplication(), "ERROR: " + jsonError.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "JSON Parse Error", Toast.LENGTH_LONG).show();
                }
                resetSharedPreference();
                btnLogin.setEnabled(true);
                btnLogin.setText(R.string.btnLogin_text);
            }
        });
    }

    /**
     * used when user clicks btnLogin in the view
     * @param view
     */
    public void onBtnSignUpClicked(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates the user to Dashboard activity
     */
    public void navigateToDashboard() {
        Intent intent = new Intent(MainActivity.this, UserDashboardActivity.class);
        startActivity(intent);
        Toast.makeText(getApplication(), "Logged In Successfully!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Saves data got from AuthResponse to shared preference
     * @param authResponse Auth Response from Login API call
     */
    public void saveToSharedPreference(AuthResponse authResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", authResponse.getUsername());
        editor.putString("token", authResponse.getToken());
        editor.commit();
    }

    /**
     * Resets the sharedPreference and removes the user and token value from it
     */
    public void resetSharedPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("token");
        editor.commit();
    }

    /**
     * Validator which runs whenever text changes in UserName input field
     */
    public class UserNameValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String username = charSequence.toString();
            isValidUsername = false;
            btnLogin.setEnabled(false);
            if (username.isEmpty()) {
                ietxtUsername.setError("Username is required!");
            } else if (username.length() < 3) {
                ietxtUsername.setError("Minimum 3 characters are required");
            } else if (username.length() > 15) {
                ietxtUsername.setError("Maximum 15 characters are allowed");
            } else if (!Pattern.matches("(^[a-zA-Z0-9_]+$)", charSequence)) {
                ietxtUsername.setError("Only A-Z, a-z, 0-9 and _ are allowed!");
            } else {
                isValidUsername = true;
                ietxtUsername.setError(null);
                if (isValidPassword) {
                    btnLogin.setEnabled(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }

    /**
     * Validator which runs whenever text changes in Password input field
     */
    public class PasswordValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String password = charSequence.toString();
            isValidPassword = false;
            btnLogin.setEnabled(false);
            if (password.isEmpty()) {
                ietxtPassword.setError("Password is required!");
            } else if (password.length() < 6) {
                ietxtPassword.setError("Minimum 6 characters are required");
            } else if (password.length() > 30) {
                ietxtPassword.setError("Maximum 30 characters are allowed");
            } else if (!Pattern.matches("(^[a-zA-Z0-9_@]+$)", charSequence)) {
                ietxtPassword.setError("Only A-Z, a-z, 0-9, _ and @ are allowed!");
            } else {
                isValidPassword = true;
                ietxtPassword.setError(null);
                if (isValidUsername) {
                    btnLogin.setEnabled(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }
}