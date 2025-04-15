package com.example.project.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.BeastTemplateAdapter;
import com.example.project.Model.Beast;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class CreateNewBeasts extends AppCompatActivity {
    private Button btnCreate, btncan;

    private RecyclerView revBeast;
    private BeastTemplateAdapter beastTemplateAdapter;
    private EditText beastname;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_beasts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        revBeast = findViewById(R.id.re_beast);
        beastname = findViewById(R.id.beat_name);
        btnCreate = findViewById(R.id.btn_create);

        btncan = findViewById(R.id.button6);
        btncan.setOnClickListener(v -> {
            Intent intent = new Intent(CreateNewBeasts.this, BeastBattle.class);
            startActivity(intent);
        });

        beastTemplateAdapter = new BeastTemplateAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        revBeast.setLayoutManager(linearLayoutManager);

        beastTemplateAdapter.setData(getListBeast());
        revBeast.setAdapter(beastTemplateAdapter);

        List<Beast> createdBeastList = new ArrayList<>();
//Get name from input and create
        btnCreate.setOnClickListener(v -> {
            String name = beastname.getText().toString().trim();
            Beast selected = BeastTemplateAdapter.getSelectedBeast();

            if (name.length() > 8) {
                Toast.makeText(this, "Name is too long! Please enter a shorter name.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selected == null) {
                Toast.makeText(this, "Please select a beast.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Clone the selected beast and assign the name
            CreateBeast newCreatedBeast = new CreateBeast(name, selected);

            newCreatedBeast.getBeast().setExperience(0);


            BeastStorage.getInstance().addBeast(newCreatedBeast);
            // Reset selection
            BeastTemplateAdapter.clearSelection();
            beastTemplateAdapter.notifyDataSetChanged();

            Toast.makeText(this, "Beast '" + name + "' created!", Toast.LENGTH_SHORT).show();

            // Optionally clear the name input
            beastname.setText("");

            Intent intent = new Intent(CreateNewBeasts.this, BeastBattle.class);
            startActivity(intent);
            finish();
        });

        }
//Data of beast Template
        public List<Beast> getListBeast(){
            List<Beast> list = new ArrayList<>();
            list.add(new Beast("Fire", "Phoenix", 6, 9, 18, 18, R.drawable.image1));
            list.add(new Beast("Water", "Tortoise", 9, 7, 16, 16, R.drawable.image2));
            list.add(new Beast("Wood", "Dragon", 8, 7, 19, 19, R.drawable.image3));
            list.add(new Beast("Mental", "Tiger", 7, 9, 15, 15, R.drawable.image4));
            list.add(new Beast("Earth", "Qilin", 8, 7, 20, 20, R.drawable.image5));
            list.add(new Beast("Light", "Unicorn", 9, 6, 19, 19, R.drawable.image6));
            list.add(new Beast("Dark", "Snake", 7, 8, 17, 17, R.drawable.image7));
            return list;
        }
    }