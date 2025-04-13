package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.HomePage;
import com.example.project.R;

public class BeastBattle extends AppCompatActivity {

    Button vhome, vtrain, vbatlle, create, movtohomepage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beasts_battle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vhome = findViewById(R.id.viewhome);
        vhome.setOnClickListener(v -> {
            Intent intent = new Intent (BeastBattle.this, Home.class);
            startActivity(intent);
        });

        vtrain = findViewById(R.id.viewtrain);
        vtrain.setOnClickListener( v -> {
            Intent intent = new Intent (BeastBattle.this, Training.class);
            startActivity(intent);
        });

        vbatlle = findViewById(R.id.viewbattle);
        vbatlle.setOnClickListener(v -> {
            Intent intent = new Intent(BeastBattle.this, Battle.class);
            startActivity(intent);
        });

        create = findViewById(R.id.createnew);
        create.setOnClickListener(v -> {
            Intent intent = new Intent (BeastBattle.this, CreateNewBeasts.class);
            startActivity(intent);
        });

        movtohomepage = findViewById(R.id.bb_move_to_home);
        movtohomepage.setOnClickListener( v-> {
            Intent intent = new Intent (BeastBattle.this, HomePage.class);
            startActivity(intent);
        });

    }
}