package com.example.project.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

import com.example.project.Adapter.TrainingBeastAdapter;
import com.example.project.Model.Beast;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.Model.Location;
import com.example.project.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Training extends AppCompatActivity {
    private Button btn_trainingselect, mvtohome, btn_trainingexp, btn_convert;

    private TextView txt_status;

    private RecyclerView recyclerView;
    private SpinKitView spinKitView;
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
            int selectedBeastId = trainingBeastAdapter.getSelectedBeastId();
            if (selectedBeastId != -1) {
                // Move Beast from Training to Home
                BeastStorage.getInstance().moveToHome(selectedBeastId);
                // update after mopve
                List<CreateBeast> updatedList = BeastStorage.getInstance().getBeastsByLocation(Location.TRAINING);
                trainingBeastAdapter.updateData(updatedList);

                // tranfer to home clas
                Intent intent = new Intent(Training.this, Home.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Training.this, Home.class);
                startActivity(intent);
            }
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

        // Get intent from training selected again
        Intent intent = getIntent();
        selectedPosition = intent.getIntExtra("selectedPosition", RecyclerView.NO_POSITION);

        btn_trainingexp = findViewById(R.id.button12);

        // If address valid
        if (selectedPosition != RecyclerView.NO_POSITION) {
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
        // Make the text animation. Using Ai to do.
        Handler handler = new Handler();
        String baseText = "The Beast is training. Please wait a moment";
        final int[] dotCount = {0};
        AtomicReference<Runnable> loadingRunnable = new AtomicReference<>();

        txt_status = findViewById(R.id.txt_status);

        btn_trainingexp.setOnClickListener(v -> {

            SpinKitView spinKitView = findViewById(R.id.spin_kit);


            int selectedBeastId = trainingBeastAdapter.getSelectedBeastId();

            // check the item
            if (selectedBeastId == -1) {
                Toast.makeText(Training.this, "Please select a beast first!", Toast.LENGTH_SHORT).show();
                return;
            }

            spinKitView.setVisibility(View.VISIBLE); // show spinkit

            String buttonText = btn_trainingexp.getText().toString();
            trainingButtonClickCount++;

            String numericPart = buttonText.replaceAll("[^0-9]", "");  // Keeps only the digits
            final int expToApp = Integer.parseInt(numericPart);
            // Set the training duration based on the EXP
            int duration = expToApp == 1 ? 10000 : (expToApp == 2 ? 30000 : 60000);  // 10s = 10000 ms, 30 = 30000 ms, 1 minutes = 60000 ms

            //Reset dot count for animation
            dotCount[0] = 0;

            loadingRunnable.set(new Runnable() {
                @Override
                public void run() {
                    String dots = new String(new char[dotCount[0] % 4]).replace("\0", ".");
                    txt_status.setText(baseText + dots);
                    dotCount[0]++;
                    handler.postDelayed(this, 500); ; // Update every 500 ms
                }
            });

            // Start the animation
            handler.post(loadingRunnable.get());

            // Stop the animation after the duration (1, 2, or 3 minutes)
            Runnable stopRunable = () -> {
                handler.removeCallbacks(loadingRunnable.get());
                // hide spinner after training
                spinKitView.setVisibility(View.GONE);
                txt_status.setText("Training complete! Beast gained +" + expToApp + " EXP.");

                if (selectedBeastId != -1) {
                    CreateBeast createBeast = BeastStorage.getInstance().getBeastById(selectedBeastId);  // get beast from BeastStorage
                    Beast beast = createBeast.getBeast();  // get beast from CreateBeast
                    beast.setExperience(beast.getExperience() + expToApp);  // + EXP for this

                    beast.addTrainCount();
                    BeastStorage.getInstance().updateBeast(createBeast);
                }

                // Update list in RecyclerView
                List<CreateBeast> updatedList = BeastStorage.getInstance().getBeastsByLocation(Location.TRAINING);
                trainingBeastAdapter.updateData(updatedList);


            };

            handler.postDelayed(stopRunable, duration);

            btn_trainingexp.setEnabled(false);
            handler.postDelayed(() -> btn_trainingexp.setEnabled(true), duration);

        });

    }

}
