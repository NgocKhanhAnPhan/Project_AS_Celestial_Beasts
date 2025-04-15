package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.HomeAdapter;
import com.example.project.HomePage;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private Button homepage, training, battle;

    private TextView dehome;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.re_home);
        homeAdapter = new HomeAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(homeAdapter);


        // update from HashMap -> List.

        homeAdapter.setData(new ArrayList<>(BeastStorage.getInstance().getAllBeasts().values()));

        homepage = findViewById(R.id.button9);
        homepage.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, HomePage.class);
            startActivity(intent);
        });

        training = findViewById(R.id.button7);
        training.setOnClickListener(v -> {
            CreateBeast selected = homeAdapter.getSelectedBeast();
            if (selected != null) {
                int id = selected.getBeast().getId();

                // Leave HOME class to training
                BeastStorage.getInstance().moveToTraining(id);


                //Update the List
                homeAdapter.setData(new ArrayList<>(BeastStorage.getInstance().getAllBeasts().values()));

                HomeAdapter.setSelectedBeast(null);

                Toast.makeText(this, selected.getCustomName() + " moved to Training!", Toast.LENGTH_SHORT).show();


                Toast.makeText(this, selected.getCustomName() + " moved to Training!", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(Home.this, Training.class);
            startActivity(intent);
        });

        battle = findViewById(R.id.button8);
        battle.setOnClickListener(v -> {
            Intent intent = new Intent (Home.this, Battle.class);
            startActivity(intent);
        });
    }
}