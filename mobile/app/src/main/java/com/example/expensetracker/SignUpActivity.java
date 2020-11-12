package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private Model model;
    private TextInputEditText ietxtUsername, ietxtPassword, ietxtConfirmPassword;
    private Button btnSignUp, btnLogin;
    private SharedPreferences sharedPreferences;
    private boolean isValidUsername = false, isValidPassword = false, isValidConfirmedPassword;

    /**
     * Fired when MainActivity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        ietxtUsername = findViewById(R.id.ietxtSignupUsername);
        ietxtPassword = findViewById(R.id.ietxtSignupPassword);
        ietxtConfirmPassword = findViewById(R.id.ietxtSignupConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        model = Model.getInstance(this.getApplication());
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        // Adding validators to the text fields
        ietxtUsername.addTextChangedListener(new UserNameValidator());
        ietxtPassword.addTextChangedListener(new PasswordValidator());
        ietxtConfirmPassword.addTextChangedListener(new ConfirmPasswordValidator());
    }

    /**
     * Fired when btnSignUp is clicked in the view
     * @param view btnSignUp
     */
    public void onBtnSignUpClicked(View view) {
        btnSignUp.setEnabled(false);
        btnLogin.setEnabled(false);
        btnSignUp.setText(R.string.btnSignUp_loading_text);

        model.signup(ietxtUsername.getText().toString(), ietxtPassword.getText().toString(), ietxtConfirmPassword.getText().toString(), new AbstractListener() {
            @Override
            public void onSignUp(String username) {
                navigateToLogin(username);
                btnSignUp.setEnabled(true);
                btnLogin.setEnabled(true);
                btnSignUp.setText(R.string.btnSignUp_text);
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
                btnSignUp.setEnabled(true);
                btnLogin.setEnabled(true);
                btnSignUp.setText(R.string.btnSignUp_text);
            }
        });
    }

    /**
     * used when user clicks btnLogin in the view
     * @param view
     */
    public void onBtnLoginClicked(View view) {
        navigateToLogin(null);
    }

    /**
     * Navigates the user to Dashboard activity
     */
    public void navigateToLogin(@Nullable String username) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        if (username != null) {
            intent.putExtra("username", username);
        }
        startActivity(intent);
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
            btnSignUp.setEnabled(false);
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
                if (isValidPassword && isValidConfirmedPassword) {
                    btnSignUp.setEnabled(true);
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
            btnSignUp.setEnabled(false);
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
                if (isValidUsername && isValidConfirmedPassword) {
                    btnSignUp.setEnabled(true);
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
    public class ConfirmPasswordValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String password = charSequence.toString();
            isValidConfirmedPassword = false;
            btnSignUp.setEnabled(false);
            if (password.isEmpty()) {
                ietxtConfirmPassword.setError("Confirm Password is required");
            } else if (!isValidPassword) {
                ietxtConfirmPassword.setError("Main Password is not valid");
            } else if (!password.equals(ietxtPassword.getText().toString())) {
                ietxtConfirmPassword.setError("Both passwords does no match");
            } else {
                isValidConfirmedPassword = true;
                ietxtConfirmPassword.setError(null);
                if (isValidUsername && isValidPassword) {
                    btnSignUp.setEnabled(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }
}