package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.example.project.HomePage;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ViewChart extends AppCompatActivity {
    Button movesta, movehome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        movehome = findViewById(R.id.button17);
        movehome.setOnClickListener(v -> {
            Intent intent = new Intent (ViewChart.this, HomePage.class);
            startActivity(intent);
        });

        movesta = findViewById(R.id.button19);
        movesta.setOnClickListener(v -> {
            Intent intent = new Intent (ViewChart.this, Statistics.class);
            startActivity(intent);
        });

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Cartesian cartesian = AnyChart.column();

        new Thread(() -> {
            // Background thread
            List<CreateBeast> allBeasts = new ArrayList<>(BeastStorage.getInstance().getAllBeasts().values());
            int totalBeasts = allBeasts.size();
            int totalTrainings = 0;
            int totalBattles = 0;

            for (CreateBeast beast : allBeasts) {
                totalTrainings += beast.getBeast().getTrainCount();
                totalBattles += beast.getBeast().getMatchCount();
            }

            List<DataEntry> data = new ArrayList<>();
            data.add(new ValueDataEntry("Beasts", totalBeasts));
            data.add(new ValueDataEntry("Training", totalTrainings));
            data.add(new ValueDataEntry("Battles", totalBattles));

            runOnUiThread(() -> {
                // UI thread
                Column column = cartesian.column(data);
                column.tooltip()
                        .titleFormat("{%X}")
                        .format("{%Value}");

                cartesian.animation(true);
                cartesian.title("Beast Statistics");
                cartesian.title().fontWeight("bold");

                cartesian.yScale().minimum(0d);
                cartesian.xAxis(0).title("Category");
                cartesian.yAxis(0).title("Total Count");

                anyChartView.setChart(cartesian);
            });
        }).start();


    }
}