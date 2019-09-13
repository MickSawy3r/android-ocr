package com.sixbits.androvisionocv.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sixbits.androvisionocv.R;
import com.sixbits.androvisionocv.ui.database_activity.DatabaseExploreActivity;

public class AdminLoginActivity extends AppCompatActivity {
    private static final String TAG = "AdminLoginActivity";

    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etPassword = findViewById(R.id.etAdminPassword);
    }

    private boolean validatePassword(String plainPassword) {
        SharedPreferences prefs = this.getSharedPreferences("profile", MODE_PRIVATE);
        String encPass = prefs.getString("password_encrepted", "6661");

        // INFO: Supports One Way Encryption
        return encrypt(plainPassword).equals(encPass);
    }

    private String encrypt(String plainPass) {
        return plainPass + "1";
    }

    public void tryLogin(View view) {
        if (view.getId() == R.id.btnTryLogin) {
            if (validatePassword(etPassword.getText().toString())) {
                Intent intent = new Intent(this, DatabaseExploreActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Bad Password!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
