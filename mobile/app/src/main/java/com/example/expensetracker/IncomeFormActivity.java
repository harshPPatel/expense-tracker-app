package com.example.expensetracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.IncomeResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IncomeFormActivity extends AppCompatActivity {

    boolean isValidAmount = false, isValidTitle = false, isEdit = false;
    Button btnSaveIncome;
    private TextInputEditText ietIncomeAmount, ietIncomeTitle, ietIncomeDate;
    private TextInputLayout tilIncomeDate;
    Model model;
    SharedPreferences sharedPreferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_form);

        btnSaveIncome = (Button) findViewById(R.id.btnSaveIncome);
        btnSaveIncome.setEnabled(false);

        ietIncomeAmount = findViewById(R.id.ietIncomeAmount);
        ietIncomeTitle = findViewById(R.id.ietIncomeTitle);
        ietIncomeDate = findViewById(R.id.ietIncomeDate);
        tilIncomeDate = findViewById(R.id.tilIncomeDate);

        ietIncomeTitle.addTextChangedListener(new TitleValidator());
        ietIncomeAmount.addTextChangedListener(new AmountValidator());

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);

        model = Model.getInstance(getApplication());
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        if (isEdit) {
            String title = intent.getStringExtra("title");
            String date = intent.getStringExtra("date");
            double amount = intent.getDoubleExtra("amount", 0);
            id = intent.getStringExtra("id");
            ietIncomeTitle.setText(title);
            ietIncomeDate.setText(date);
            ietIncomeAmount.setText(String.valueOf(amount));
            btnSaveIncome.setEnabled(true);
            btnSaveIncome.setText("Update");
        }

        ietIncomeDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int date = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IncomeFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int year, final int month, final int date) {
                        int hour = calendar.get(Calendar.HOUR);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(IncomeFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                Date tempDate = new Date();
                                tempDate.setHours(hour);
                                tempDate.setMinutes(minute);
                                tempDate.setSeconds(0);
                                tempDate.setDate(date);
                                tempDate.setMonth(month);
                                tempDate.setYear(year);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                ietIncomeDate.setText(format.format(tempDate));
                            }
                        }, hour, minute, false);
                        timePickerDialog.show();
                    }
                }, year, month, date);

                if (b) {
                    datePickerDialog.show();
                } else {
                    datePickerDialog.hide();
                }
            }
        });
    }


    public void onBtnSaveClicked(View view) {
        // TODO: Make it dynamic to work with edit as well
        String title = ietIncomeTitle.getText().toString();
        String amountString = ietIncomeAmount.getText().toString();
        int amount = Integer.parseInt(amountString);
        String date = ietIncomeDate.getText().toString();
        String token = sharedPreferences.getString("token", "");
        if (isEdit) {
            if (!token.isEmpty() && id != null) {
                model.updateIncome(token, id, title, amount, date, new AbstractListener() {
                    @Override
                    public void onIncomeUpdated(IncomeResponse incomeResponse) {
                        Toast.makeText(getApplicationContext(), "Updated Income Successfully!", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    public void onRequestFailed(JSONObject jsonError) {
                        Toast.makeText(getApplicationContext(), "ERROR while updating income :(", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            if (!token.isEmpty()) {
                model.createIncome(token, title, amount, date, new AbstractListener() {
                    @Override
                    public void onIncomeCreated(IncomeResponse incomeResponse) {
                        Toast.makeText(getApplicationContext(), "Created Income Successfully!", Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    public void onRequestFailed(JSONObject jsonError) {
                        Toast.makeText(getApplicationContext(), "ERROR while creating income :(", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public class TitleValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = charSequence.toString();
            isValidTitle = false;
            btnSaveIncome.setEnabled(false);
            if (title.isEmpty()) {
                ietIncomeTitle.setError("Title is required!");
            } else if (title.length() < 3) {
                ietIncomeTitle.setError("Title must have at least three characters.");
            } else {
                isValidTitle = true;
                ietIncomeTitle.setError(null);
                if(isValidAmount) {
                    btnSaveIncome.setEnabled(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }

    public class AmountValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = charSequence.toString();
            isValidAmount = false;
            btnSaveIncome.setEnabled(false);
            if (amount.isEmpty()) {
                ietIncomeAmount.setError("Amount is required!");
            } else {

                try {
                    int parsedAmount = Integer.parseInt(amount);
                    if (parsedAmount < 0) {
                        ietIncomeAmount.setError("Please enter value greater than 0.");
                    } else {
                        isValidAmount = true;
                        ietIncomeAmount.setError(null);
                        if(isValidTitle) {
                            btnSaveIncome.setEnabled(true);
                        }
                    }
                } catch (Exception e) {
                    ietIncomeAmount.setError("Please enter valid amount");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // ignore
        }
    }
}