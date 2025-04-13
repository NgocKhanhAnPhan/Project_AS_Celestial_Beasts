package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.View.BeastBattle;
import com.example.project.View.Statistics;
import com.example.project.View.UserInfomation;

public class HomePage extends AppCompatActivity {

    Button viewinfo, viewsta, viewbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewinfo = findViewById(R.id.h_viewinfo);
        viewinfo.setOnClickListener( v-> {
            Intent intent = new Intent(HomePage.this, UserInfomation.class);
            startActivity(intent);
        });

        viewsta = findViewById(R.id.home_viewsta);
        viewsta.setOnClickListener( v-> {
            Intent intent = new Intent(HomePage.this, Statistics.class);
            startActivity(intent);
        });

        //viewbb = findViewById(R.id.h_viewbb);
        //viewbb.setOnClickListener( v-> {
            //Intent intent = new Intent(HomePage.this, BeastBattle.class);
            //startActivity(intent);
        //});
    }
}