package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.View.BeastBattle;
import com.example.project.View.UserInfomation;

public class LoginActivity extends AppCompatActivity {

    TextView notice;

    DBHelper dbHelper;

    EditText name, password;

    Button btn_regis, btn_login;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBHelper(this);
        TextView notice = findViewById(R.id.textView4);
        notice.setText("If you don't account, please register.");

        name = findViewById(R.id.editTextTextUserName);
        password = findViewById(R.id.editTextTextPassword);
        btn_login = findViewById(R.id.button3);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoggedId = dbHelper.checkUser(name.getText().toString(), password.getText().toString());
                if (isLoggedId){
                    Intent intent = new Intent(LoginActivity.this, HomePage.class);
                    startActivity(intent);

                    Intent intents = new Intent(LoginActivity.this, UserInfomation.class);
                    intents.putExtra("username", name.getText().toString());
                    intents.putExtra("password", password.getText().toString());
                    startActivity(intents);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed. If you do not have an account, please register. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn_regis = findViewById(R.id.button4);
        btn_regis.setOnClickListener(v -> {
            Intent intent3 = new Intent (LoginActivity.this, RegisterActivity.class);
            startActivity(intent3);
        });




    }
}