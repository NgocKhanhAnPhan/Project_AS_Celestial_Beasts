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

import com.example.project.Adapter.convertAdapterInstance;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.Model.Location;
import com.example.project.R;

import java.util.List;

public class ConvertEXP extends AppCompatActivity {

    private RecyclerView recyclerView;
    private convertAdapterInstance convertAdapterInstance;


    private Button inatt, indefe, inmaxhealth;
    private int availableExp = 0;

    private TextView deexp;

    private Button movetotrain;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
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
            int selectedBeastId = convertAdapterInstance.getSelectedBeastId();
            if (selectedBeastId != -1) {
                // Move Beast from Convert to training
                BeastStorage.getInstance().moveToTraining(selectedBeastId);
                // update after move
                List<CreateBeast> updatedList = BeastStorage.getInstance().getBeastsByLocation(Location.CONVERT);
                convertAdapterInstance.updateData(updatedList);

                // tranfer to home clas
                Intent intent = new Intent(ConvertEXP.this, Training.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ConvertEXP.this, Training.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.re_select);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CreateBeast> convertList = BeastStorage.getInstance().getBeastsByLocation(Location.CONVERT);
        convertAdapterInstance = new convertAdapterInstance(this, convertList);
        recyclerView.setAdapter(convertAdapterInstance);


        deexp = findViewById(R.id.textView48);

        if (!convertList.isEmpty()) {
            availableExp = convertList.get(0).getBeast().getExperience();
            deexp.setText("Total\n"+ "EXP: " + availableExp);
        }

        inatt = findViewById(R.id.buttonatt);
        indefe = findViewById(R.id.buttondefe);
        inmaxhealth = findViewById(R.id.buttonhealth);

        inatt.setOnClickListener( v -> {
            if (convertAdapterInstance != null && convertAdapterInstance.selectedPosition != -1) {  // Selected beast
                CreateBeast selectedBeast = convertList.get(convertAdapterInstance.selectedPosition);
            if (availableExp >= 2) {
                availableExp -= 2;

                int currentAttack = selectedBeast.getBeast().getAttack();
                selectedBeast.getBeast().setAttack(currentAttack + 1);

                deexp.setText("EXP: " + availableExp);

                convertAdapterInstance.notifyDataSetChanged();
            } else {
                deexp.setText("Not enough EXP to raise Attack!");
            }
        } else
            {
                Toast.makeText(ConvertEXP.this, "Please select a Beast first!", Toast.LENGTH_SHORT).show();
            }
        });

        indefe.setOnClickListener(v -> {
            if (convertAdapterInstance != null && convertAdapterInstance.selectedPosition != -1) {  // Selected beast
                CreateBeast selectedBeast = convertList.get(convertAdapterInstance.selectedPosition);
                if (availableExp >= 3) {
                    availableExp -= 3;

                    int currentDefense = selectedBeast.getBeast().getDefe();
                    selectedBeast.getBeast().setDefe(currentDefense + 1);

                    deexp.setText("EXP: " + availableExp);

                    convertAdapterInstance.notifyDataSetChanged();
                } else {
                    deexp.setText("Not enough EXP to raise Defense!");
                }
            } else
            {
                Toast.makeText(ConvertEXP.this, "Please select a Beast first!", Toast.LENGTH_SHORT).show();
            }
        });

        inmaxhealth.setOnClickListener(v -> {
            if (convertAdapterInstance != null && convertAdapterInstance.selectedPosition != -1) {  // Selected beast
                CreateBeast selectedBeast = convertList.get(convertAdapterInstance.selectedPosition);
                if (availableExp >= 4) {
                    availableExp -= 4;

                    int currentMaxHealth = selectedBeast.getBeast().getMaxHeal();
                    selectedBeast.getBeast().setMaxHeal(currentMaxHealth + 1);

                    int cuurrentHealth = selectedBeast.getBeast().getHeal();
                    selectedBeast.getBeast().setHeal(cuurrentHealth +1);

                    deexp.setText("EXP: " + availableExp);

                    convertAdapterInstance.notifyDataSetChanged();
                } else {
                    deexp.setText("Not enough EXP to raise MaxHealth!");
                }
            } else
            {
                Toast.makeText(ConvertEXP.this, "Please select a Beast first!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}