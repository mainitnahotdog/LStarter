package com.sklerbidi.handsealstrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sklerbidi.handsealstrainer.MiniGames.GameActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_asl, btn_fsl, btn_mini_games, btn_tips, btn_exit;
    FloatingActionButton fab_info;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        btn_asl.setOnClickListener(v -> {
            Intent intent = new Intent(this, ASLActivity.class);
            startActivity(intent);
        });

        btn_fsl.setOnClickListener(v -> {

        });

        btn_mini_games.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        btn_tips.setOnClickListener(v -> {
            Intent intent = new Intent(this, InformationActivity.class);
            startActivity(intent);


        });

        btn_exit.setOnClickListener(v -> {

        });

        fab_info.setOnClickListener(v -> {

        });

    }

    private void findView() {
        btn_asl = findViewById(R.id.btnAsl);
        btn_fsl = findViewById(R.id.btnFsl);
        btn_mini_games = findViewById(R.id.btn_miniG);
        btn_tips = findViewById(R.id.btn_tips);
        btn_exit = findViewById(R.id.btn_exit);
        fab_info = findViewById(R.id.fab_info);
    }
}