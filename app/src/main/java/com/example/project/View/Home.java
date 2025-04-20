package com.example.project.View;

import android.annotation.SuppressLint;
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
import com.example.project.Model.Location;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Home extends AppCompatActivity {
    private Button homepage, training, battle;

    private TextView dehome;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    @SuppressLint("NotifyDataSetChanged")
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

        //move to Battle files
        battle = findViewById(R.id.button8);
        battle.setOnClickListener(v -> {
            List<CreateBeast> selectedBeasts = homeAdapter.getSelectedBeasts();  // Get list of selected beasts
            if (!selectedBeasts.isEmpty()) {
                for (CreateBeast selected : selectedBeasts) {
                    int id = selected.getBeast().getId();

                    // Move each selected beast to battle
                    BeastStorage.getInstance().moveToBattle(id);
                }

                // Update the List
                homeAdapter.setData(BeastStorage.getInstance().getBeastsByLocation(Location.HOME));
                homeAdapter.notifyDataSetChanged();

                // Clear selection
                homeAdapter.setSelectedBeast(null);

                // Display toast
                Toast.makeText(this, "Selected beasts moved to Battle!", Toast.LENGTH_SHORT).show();

                // Start Battle Activity
                Intent intent = new Intent(Home.this, Battle.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No beasts selected!", Toast.LENGTH_SHORT).show();
                // Start Battle Activity even if no beasts are selected
                Intent intent = new Intent(Home.this, Battle.class);
                startActivity(intent);
            }
        });


        training = findViewById(R.id.button7);
        training.setOnClickListener(v -> {
            List<CreateBeast> selectedBeasts = homeAdapter.getSelectedBeasts();  // Get list of selected beasts
            if (!selectedBeasts.isEmpty()) {
                for (CreateBeast selected : selectedBeasts) {
                    int id = selected.getBeast().getId();

                    // Move each selected beast to training
                    BeastStorage.getInstance().moveToTraining(id);
                }

                // Update the List
                homeAdapter.setData(BeastStorage.getInstance().getBeastsByLocation(Location.HOME));
                homeAdapter.notifyDataSetChanged();

                // Clear selection
                homeAdapter.setSelectedBeast(null);

                // Display toast
                Toast.makeText(this, "Selected beasts moved to Training!", Toast.LENGTH_SHORT).show();

                // Start Training Activity
                Intent intent = new Intent(Home.this, Training.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No beasts selected!", Toast.LENGTH_SHORT).show();
                // Start Training Activity even if no beasts are selected
                Intent intent = new Intent(Home.this, Training.class);
                startActivity(intent);
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //  Beast in HOME
        List<CreateBeast> homeBeasts = new ArrayList<>();
        for (CreateBeast beast : BeastStorage.getInstance().getAllBeasts().values()) {
            if (beast.getLocation() == Location.HOME) {  // Only selected beast in HOME
                homeBeasts.add(beast);
                beast.restoreHP();
            }
        }

        // Update RecyclerView
        homeAdapter.setData(homeBeasts); // Update the data.
    }
}