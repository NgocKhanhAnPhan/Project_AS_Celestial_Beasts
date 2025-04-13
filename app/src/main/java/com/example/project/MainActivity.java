package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    TextView ask, au;

    Button btn_yes, btn_no;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView ask = findViewById(R.id.textView);
        ask.setText("Wanna to play game? ");


        @SuppressLint("CutPasteId") Button btn_no = findViewById(R.id.button2);
        btn_no.setOnClickListener(v -> {
            if (btn_no.getText().toString().equals("No")){
                btn_no.setText("Only Yes");
                btn_no.setBackgroundResource(R.drawable.button_yes);
            } else if (btn_no.getText().toString().equals("Only Yes")) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView au = findViewById(R.id.textView2);
        au.setText("An Phan / Sumeya Samsun");

        Button btn_yes = findViewById(R.id.button2);
        btn_yes.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
           startActivity(intent2);
        });

        }
    }