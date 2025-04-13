package com.example.project.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.HomePage;
import com.example.project.LoginActivity;
import com.example.project.R;

public class UserInfomation extends AppCompatActivity {
    Button btn_logout, btn_movehome;
    TextView username, password, welcome;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_infomation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfomation.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btn_movehome = findViewById(R.id.btn_ifo_movehp);
        btn_movehome.setOnClickListener(v -> {
            Intent intent = new Intent (UserInfomation.this, HomePage.class);
            startActivity(intent);
        });

        String username1 = getIntent().getStringExtra("username");
        String password1 = getIntent().getStringExtra("password");

        username = findViewById(R.id.textView7);
        username.setText("Username: " + username1);
        password = findViewById(R.id.textView8);
        password.setText("Password: " + password1);

        welcome = findViewById(R.id.welcome);
        welcome.setText("WELCOME TO OUR GAME USER " + username1);

    }
}