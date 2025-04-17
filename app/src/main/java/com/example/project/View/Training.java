package com.example.project.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.ui.contextmenu.Item;
import com.example.project.Adapter.HomeAdapter;
import com.example.project.Adapter.TrainingBeastAdapter;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.Model.Location;
import com.example.project.Model.TrainingModeManager;
import com.example.project.R;

import java.util.List;

public class Training extends AppCompatActivity {
    private Button btn_trainingselect, mvtohome, btn_trainingexp, btn_convert;

    private RecyclerView recyclerView;
    private TrainingBeastAdapter trainingBeastAdapter;

    private int selectedPosition;

    // count training
    private int trainingButtonClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


//setup RecyclerView:
        recyclerView = findViewById(R.id.re_trainingbeast);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CreateBeast> trainingBeasts = BeastStorage.getInstance().getBeastsByLocation(Location.TRAINING);

        trainingBeastAdapter = new TrainingBeastAdapter(this, trainingBeasts);
        recyclerView.setAdapter(trainingBeastAdapter);

        // button move to training select
        btn_trainingselect = findViewById(R.id.button10);
        btn_trainingselect.setOnClickListener(v -> {
            Intent intent = new Intent(Training.this, TrainingSelectedPage.class);
            startActivity(intent);
        });

        //button move to home
        mvtohome = findViewById(R.id.button11);
        mvtohome.setOnClickListener(v -> {
            Intent intent = new Intent(Training.this, Home.class);
            startActivity(intent);
        });

        btn_convert = findViewById(R.id.button18);
        btn_convert.setOnClickListener(v -> {
            int selectedId = trainingBeastAdapter.getSelectedBeastId();
            if (selectedId != -1) {
                BeastStorage.getInstance().moveToConvert(selectedId);
                Intent intent = new Intent(Training.this, ConvertEXP.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Training.this, ConvertEXP.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        // Update ist
        List<CreateBeast> updatedList = BeastStorage.getInstance().getBeastsByLocation(Location.TRAINING);
        trainingBeastAdapter.updateData(updatedList);

        // Get intent from training selected again
        Intent intent = getIntent();
        selectedPosition = intent.getIntExtra("selectedPosition", RecyclerView.NO_POSITION);

        // If address valid
        if (selectedPosition != RecyclerView.NO_POSITION) {
            btn_trainingexp = findViewById(R.id.button12);
            switch (selectedPosition) {
                case 0:
                    btn_trainingexp.setText("+1 Experience");
                    break;
                case 1:
                    btn_trainingexp.setText("+2 Experience");
                    break;
                case 2:
                    btn_trainingexp.setText("+3 Experience");
                    break;
            }
        }
        // Make the text animation. Using Ai to know.
        Handler handler = new Handler();
        String baseText = "The Beast is training. Please wait a moment";
        final int [] dotCount = {0};
        Runnable loadingRunnable;
        Runnable stopRunable;

        btn_trainingexp.setOnClickListener(v -> {
            String buttonText = btn_trainingexp.getText().toString();
            trainingButtonClickCount++;
            int expToApp = Integer.parseInt(buttonText.replace("+",""));
            int duration = (expToApp == 1) ? 2000 : 4000;

            TrainingBeastAdapter seclectedItem = trainingBeastAdapter

        });


    }

}
