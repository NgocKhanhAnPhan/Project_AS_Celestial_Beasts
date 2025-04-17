package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.HomeAdapter;
import com.example.project.Adapter.TrainingSelectedAdapter;
import com.example.project.Model.TrainingSelected;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class TrainingSelectedPage extends AppCompatActivity {
    Button movetohome;
    private RecyclerView recyclerView;
    private TrainingSelectedAdapter trainingseAdapter;

    private int selectedPosition = RecyclerView.NO_POSITION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training_select_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.re_se_train);
        trainingseAdapter = new TrainingSelectedAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(trainingseAdapter);
        trainingseAdapter.setData(getListSelected());

        trainingseAdapter.setOnItemSelectedListener(position -> {
            selectedPosition = position;
        });

        movetohome = findViewById(R.id.button13);
        movetohome.setOnClickListener(v -> {
            if (selectedPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(TrainingSelectedPage.this, Training.class);
                intent.putExtra("selectedPosition", selectedPosition);
                startActivity(intent);
            } else {
                Toast.makeText(TrainingSelectedPage.this, "Please select a training mode", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Update any UI changes if necessary.
    }

    public List<TrainingSelected> getListSelected(){
        List<TrainingSelected> list = new ArrayList<>();
        list.add(new TrainingSelected("Easy","Beast can collect 1 EXP after training this module."));
        list.add(new TrainingSelected("Medium","Beast can collect 2 EXP after training this module."));
        list.add(new TrainingSelected("Hard","Beast can collect 3EXP after training this module. "));
        return list;
    }
}