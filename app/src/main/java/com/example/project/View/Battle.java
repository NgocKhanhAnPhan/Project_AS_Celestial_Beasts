package com.example.project.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.BattleAdapter;
import com.example.project.Model.BeastStorage;
import com.example.project.Model.CreateBeast;
import com.example.project.Model.Location;
import com.example.project.R;

import java.util.List;

public class Battle extends AppCompatActivity {
    private Button endbattle, start, next;

    RecyclerView recyclerView;
    TextView de;
    private CreateBeast playerBeast;

    private CreateBeast lastWinner;

    BattleAdapter battleAdapter;
    private boolean playerTurn = true;
    private boolean battleOver = false;

    private CreateBeast attacker;
    private CreateBeast defender;
    private boolean slashFromLeft = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_battle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        endbattle = findViewById(R.id.end);
        endbattle.setOnClickListener(v -> {
            if (lastWinner != null) {
                // Move Winner to Home
                BeastStorage.getInstance().moveToHome(lastWinner.getBeast().getId());
                battleAdapter.removeFromSelected(lastWinner.getBeast().getId());
                lastWinner = null;  // Update Winner

                // Update RecyclerView
                List<CreateBeast> battleList = BeastStorage.getInstance().getBeastsByLocation(Location.BATTLE);
                battleAdapter.updateData(battleList);

                Toast.makeText(Battle.this, "Beast returned Home!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (Battle.this,Home.class);
                startActivity(intent);
            } else {
                Toast.makeText(Battle.this, "No beast on List.", Toast.LENGTH_SHORT).show();
            }
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(v->{
            Toast.makeText(Battle.this, "Please chose another beast in home to continue battle", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (Battle.this, Home.class);
            startActivity(intent);
        });

        ////setup RecyclerView:
        recyclerView = findViewById(R.id.re_battle);
        List<CreateBeast> battleList = BeastStorage.getInstance().getBeastsByLocation(Location.BATTLE);
        Log.d("BattleActivity", "Beast in battle count: " + battleList.size());
        for (CreateBeast beast : battleList) {
            Log.d("BattleActivity", "Beast in battle: " + beast.getCustomName());
        }
        battleAdapter = new BattleAdapter(this, battleList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(battleAdapter);

        battleAdapter.setOnSelectionChangedListener(count -> {
            // show start when choose 2
            start.setEnabled(count == 2);
        });

        //adding scrolling for battle
        de = findViewById(R.id.decriptionbattle);
        de.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        //using AI to write
        start = findViewById(R.id.button16);
        start.setOnClickListener(v -> {
            List<CreateBeast> selected = battleAdapter.getSelectedBeasts();
            if (selected.size() == 2) {
                showSelectAttackerDialog(selected);
            } else {
                updateBattleInfo("Choose 2 beasts to start battle");
            }
        });
    }
    private void showSelectAttackerDialog(List<CreateBeast> selectedBeasts) {
        new AlertDialog.Builder(this)
                .setTitle("Choose Attacker")
                .setItems(new String[]{selectedBeasts.get(0).getCustomName(), selectedBeasts.get(1).getCustomName()},
                        (dialog, which) -> {
                            if (which == 0) {
                                attacker = selectedBeasts.get(0);
                                defender = selectedBeasts.get(1);
                                slashFromLeft = true;
                            } else {
                                attacker = selectedBeasts.get(1);
                                defender = selectedBeasts.get(0);
                                slashFromLeft = false;
                            }

                            attacker.setPlayerControlled(true);
                            defender.setPlayerControlled(false);

                            playerBeast = attacker;

                            // Battle
                            playerTurn = true;
                            battleOver = false;
                            attacker.getBeast().addMatchPlayed();
                            defender.getBeast().addMatchPlayed();
                            takeTurn(); // start turn
                        })
                .setCancelable(false)
                .show();
    }


    private void updateBattleInfo(String info) {
        String currentText = de.getText().toString();
        String updatedText = currentText + "\n" + info;
        de.setText(updatedText);
    }

    private void swapTurn() {
        playerTurn = !playerTurn;
    }

    private void handleDeath(CreateBeast winner, CreateBeast loser) {
        // add exp for winner
        winner.getBeast().addExperience(1);
        // +1 winer
        winner.getBeast().addWin();
        // +1 losser
        loser.getBeast().addLoss();

        //store winner == lastwinner
        lastWinner = winner;

        if (loser.isPlayerControlled()) {
            // If the player loses, move both beasts (winner and loser) to home
            BeastStorage.getInstance().moveToHome(loser.getBeast().getId());
            BeastStorage.getInstance().moveToHome(winner.getBeast().getId());
            // show that the player lost
            updateBattleInfo(loser.getCustomName() + " is the loser!");
            Toast.makeText(this, "You are the loser!", Toast.LENGTH_LONG).show();
            battleAdapter.removeFromSelected(loser.getBeast().getId());

            // Navigate back to Home immediately when player loses
            Intent intent = new Intent(Battle.this, Home.class);
            startActivity(intent);
            finish(); // Close the current Battle activity
        } else {
            // If the player didn't lose, only move the loser to home
            BeastStorage.getInstance().moveToHome(loser.getBeast().getId());
            updateBattleInfo(winner.getCustomName() + " is the winner!");
        }

        battleOver = true;

        //Update Battle list
        List<CreateBeast> battleList = BeastStorage.getInstance().getBeastsByLocation(Location.BATTLE);
        battleAdapter.updateData(battleList);

        //Update RecyclerView
        battleAdapter.notifyDataSetChanged();
    }


    private void takeTurn() {
        if (battleOver) return;

        if (playerTurn) {
            updateBattleInfo(attacker.getCustomName() + "'s turn to attack!");

            new AlertDialog.Builder(this)
                    .setTitle("Your Turn")
                    .setMessage("Choose an action:")
                    .setPositiveButton("Attack", (dialog, which) -> {
                        int damage = attacker.getBeast().attack();
                        defender.getBeast().defense(damage);

                        // Flash red hit effect
                        View hitFlash = findViewById(R.id.hitFlash);
                        hitFlash.setVisibility(View.VISIBLE);
                        hitFlash.setAlpha(1f);
                        hitFlash.animate()
                                .alpha(0f)
                                .setDuration(300)
                                .withEndAction(() -> hitFlash.setVisibility(View.GONE))
                                .start();

                        // Sword slash effect
                        boolean slashFromLeft = attacker == battleAdapter.getSelectedBeasts().get(0);

                        //sword corresponding
                        ImageView swordEffect = slashFromLeft ? findViewById(R.id.swordEffect) : findViewById(R.id.swordEffect1);

                        float startX = slashFromLeft ? -300f : 300f;
                        float endX = slashFromLeft ? 300f : -300f;

                        swordEffect.setTranslationX(startX);
                        swordEffect.setVisibility(View.VISIBLE);

                        ObjectAnimator swordAnim = ObjectAnimator.ofFloat(swordEffect, "translationX", startX, endX);
                        swordAnim.setDuration(400);
                        swordAnim.setInterpolator(new AccelerateDecelerateInterpolator());

                        swordAnim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                swordEffect.setVisibility(View.INVISIBLE);
                                swordEffect.setTranslationX(startX); // Reset
                            }
                        });

                        swordAnim.start();
                        updateBattleInfo(defender.getCustomName() + " took " + damage + " damage!");

                        //update the HP bar
                        updateHealthBars();

                        if (!defender.getBeast().isAlive()) {
                            handleDeath(attacker, defender);
                        } else {
                            swapTurn();
                            takeTurn(); // proceed to next turn
                        }
                    })
                    .setNegativeButton("Defend", (dialog, which) -> {
                        attacker.getBeast().heal(2);
                        updateBattleInfo(attacker.getCustomName() + " defended and healed!");

                        //Shieldafect
                        ImageView shieldIcon;

                        if (slashFromLeft) {
                            shieldIcon = findViewById(R.id.shieldLeft);
                        } else {
                            shieldIcon = findViewById(R.id.shieldRight);
                        }

                        shieldIcon.setVisibility(View.VISIBLE);
                        shieldIcon.setAlpha(1f);
                        shieldIcon.animate()
                                .alpha(0f)
                                .setDuration(1000)
                                .withEndAction(() -> shieldIcon.setVisibility(View.GONE))
                                .start();

                        swapTurn();
                        takeTurn(); // proceed to next turn
                    })
                    .setCancelable(false)
                    .show();
        } else {
            // AI's turn
            int damage = defender.getBeast().attack();
            attacker.getBeast().defense(damage);

            updateBattleInfo(attacker.getCustomName() + " took " + damage + " damage!");
            updateHealthBars();

            if (!attacker.getBeast().isAlive()) {
                handleDeath(defender, attacker);  // Handle if attacker (Player) dies
            } else {
                swapTurn();
                takeTurn(); // next turn
            }
        }
    }


    private void updateHealthBars() {
        battleAdapter.notifyDataSetChanged();
    }

}