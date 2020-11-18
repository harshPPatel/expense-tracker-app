package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.expensetracker.model.ExpenseResponse;
import com.example.expensetracker.model.Model;
import com.example.expensetracker.model.api.AbstractListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class UserDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences sharedPreferences;
    private Model model;
    private final int LAUNCH_EXPENSE_ACTIVITY = 1, LAUNCH_INCOME_ACTIVITY = 2;
    private FragmentRefreshListener fragmentRefreshListener;

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(fragmentRefreshListener != null){
                fragmentRefreshListener.onRefresh();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(UserDashboardActivity.this, R.id.nav_host_fragment);
                int currentId = navController.getCurrentDestination().getId();
                switch (currentId) {
                    case R.id.nav_incomes:
                        Intent incomeIntent = new Intent(UserDashboardActivity.this, IncomeFormActivity.class);
                        startActivityForResult(incomeIntent, LAUNCH_INCOME_ACTIVITY);
                        break;
                    default:
                        // By default, opens new Expense screen
                        Intent expenseIntent = new Intent(UserDashboardActivity.this, ExpenseFormActivity.class);
                        startActivityForResult(expenseIntent, LAUNCH_EXPENSE_ACTIVITY);
                }

            }
        });

        model = Model.getInstance(this.getApplication());

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_expenses, R.id.nav_incomes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
    }

    public void onActionLogoutClicked(MenuItem item) {
        String token = sharedPreferences.getString("token", "");
        if (!token.isEmpty()) {
            model.logout(token, new AbstractListener() {
                @Override
                public void onRequestFailed(JSONObject jsonError) {
                    try {
                        // EXTRA TODO: Custom Toast with error theme
                        Toast.makeText(getApplication(), "ERROR: " + jsonError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplication(), "JSON Parse Error", Toast.LENGTH_LONG).show();
                    }
                }
            });
            // even if this request succeeds or it fails, we will logout user
            logoutUser();
        }

    }

    private void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("token");
        editor.commit();

        // Navigating user back to login screen
        Intent intent = new Intent(UserDashboardActivity.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Your are logged out now!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }
}