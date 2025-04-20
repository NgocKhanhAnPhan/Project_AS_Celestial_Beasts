package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.StatisticsAdapter;
import com.example.project.HomePage;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Statistics extends AppCompatActivity {
    private Button mvHomePage, viewchart;

    private RecyclerView recyclerView;
    private StatisticsAdapter statisticsAdapter;

    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.re_statistics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CreateBeast> allBeasts = new ArrayList<>(BeastStorage.getInstance().getAllBeasts().values());


        statisticsAdapter = new StatisticsAdapter(this, allBeasts);
        recyclerView.setAdapter(statisticsAdapter);

        viewchart = findViewById(R.id.button14);
        viewchart.setOnClickListener(v -> {
            Intent intent = new Intent (Statistics.this, ViewChart.class);
            startActivity(intent);
        });

        mvHomePage = findViewById(R.id.button15);
        mvHomePage.setOnClickListener(v -> {
            Intent intent = new Intent (Statistics.this, HomePage.class);
            startActivity(intent);
        });

        TextView total = findViewById(R.id.textView44);

        // Declaire variable
        Map<Integer, CreateBeast> allBeastss = BeastStorage.getInstance().getAllBeasts();
        int totalBeasts = allBeasts.size();
        int totalTrainings = 0;
        int totalBattles = 0;

        // Get size of allBeast
        for (Map.Entry<Integer, CreateBeast> entry : allBeastss.entrySet()) {
            CreateBeast beast = entry.getValue();
            totalTrainings += beast.getBeast().getTrainCount();
            totalBattles += beast.getBeast().getMatchCount();
        }

        //Update info in view
        String stats = "\n" + "\n" +"\n" + "    Total Beasts: " + totalBeasts + "\n"
                + "    Total Trainings: " + totalTrainings + "\n"
                + "    Total Battles: " + totalBattles/2;

        total.setText(stats);
    }
}