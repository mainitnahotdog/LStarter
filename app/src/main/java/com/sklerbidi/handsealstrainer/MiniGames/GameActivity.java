package com.sklerbidi.handsealstrainer.MiniGames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.sklerbidi.handsealstrainer.R;

public class GameActivity extends AppCompatActivity {

    CardView card_guess_the_sign, card_tic_tac_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findView();

        card_guess_the_sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameContainer.class);
            intent.putExtra("game", "guess_the_sign");
            startActivity(intent);
        });

        card_tic_tac_sign.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameContainer.class);
            intent.putExtra("game", "tic_tac_sign");
            startActivity(intent);
        });
    }

    private void findView() {
        card_guess_the_sign = findViewById(R.id.card_guess_the_sign);
        card_tic_tac_sign = findViewById(R.id.card_tic_tac_toe);
    }
}