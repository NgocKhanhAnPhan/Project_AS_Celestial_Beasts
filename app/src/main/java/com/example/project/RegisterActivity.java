package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText newuser, newpass, repass;
    Button btn_register;

    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        newuser = findViewById(R.id.editTextTextUserName);
        newpass = findViewById(R.id.editTextTextPassword);
        repass = findViewById(R.id.editTextTextRetypePassword);
        btn_register = findViewById(R.id.button4);
        dbHelper = new DBHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   String user, pass, repwd;
                   user = newuser.getText().toString();
                   pass = newpass.getText().toString();
                   repwd = repass.getText().toString();
                   if (user.equals("") || pass.equals("") ||  repwd.equals("")){
                       Toast.makeText(RegisterActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
            } else {
                       if (pass.equals(repwd)) {
                           if(dbHelper.checkUsername(user)){
                               Toast.makeText(RegisterActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                               return;
                           }
                           boolean registeredSuccess = dbHelper.insertData(user, pass);
                           if (registeredSuccess) {
                               Toast.makeText(RegisterActivity.this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                               startActivity(intent);
                               finish();
                           } else {
                               Toast.makeText(RegisterActivity.this, "User registration failed. ", Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           Toast.makeText(RegisterActivity.this, "Password does not match.", Toast.LENGTH_SHORT).show();
                       }
                   }
        }
        });
    }
}