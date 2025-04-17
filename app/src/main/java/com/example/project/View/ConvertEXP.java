package com.example.project.View;

import android.annotation.SuppressLint;
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

import com.example.project.Adapter.ConvertAdapter;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.Model.Location;
import com.example.project.R;

import java.util.List;

public class ConvertEXP extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConvertAdapter ConvertAdapter;

    private TextView deexp;

    private Button movetotrain;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_convert_exp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        movetotrain = findViewById(R.id.mvtrain);
        movetotrain.setOnClickListener(v -> {
            Intent intent = new Intent(ConvertEXP.this, Training.class);
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.re_select);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CreateBeast> convertList = BeastStorage.getInstance().getBeastsByLocation(Location.CONVERT);
        ConvertAdapter adapter = new ConvertAdapter(this, convertList);
        recyclerView.setAdapter(adapter);

        deexp = findViewById(R.id.textView48);

        if (!convertList.isEmpty()) {
            int exp = convertList.get(0).getBeast().getExperience();
            deexp.setText("EXP: " + exp);
        }

    }
}