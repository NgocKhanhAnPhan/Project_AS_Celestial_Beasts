package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.HomePage;
import com.example.project.Model.BeastStorage;
import com.example.project.R;

public class BeastBattle extends AppCompatActivity {

    Button vhome, vtrain, vbatlle, create, movtohomepage;
    TextView debehome, detrain, debat;

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

        create = findViewById(R.id.button5);
        create.setBackgroundColor(ContextCompat.getColor(this, R.color.in));
        create.setOnClickListener(v -> {
            Intent intent2 = new Intent (BeastBattle.this, CreateNewBeasts.class);
            startActivity(intent2);
        });

        movtohomepage = findViewById(R.id.bb_move_to_home);
        movtohomepage.setOnClickListener( v-> {
            Intent intent = new Intent (BeastBattle.this, HomePage.class);
            startActivity(intent);
        });

        debehome = findViewById(R.id.textView15);
        detrain = findViewById(R.id.textView17);
        debat = findViewById(R.id.textView22);

        updateCounts();
    }

    // demonstrating the beast in home.
    protected void onResume() {
        super.onResume();
        updateCounts(); // Update when come back BeastBattle
    }

    //update count

    private void updateCounts() {
        BeastStorage storage = BeastStorage.getInstance();

        int homeCount = storage.countBeastsInHome();
        int trainingCount = storage.countBeastsInTraining();
        int battleCount = storage.countBeastsInBattle();

        debehome.setText("You have " + homeCount + " in home.");
        detrain.setText("You have " + trainingCount + " in training field.");
        debat.setText("You have " + battleCount + " in battle field.");
    }




}