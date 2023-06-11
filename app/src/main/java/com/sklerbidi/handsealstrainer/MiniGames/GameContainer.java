package com.sklerbidi.handsealstrainer.MiniGames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.sklerbidi.handsealstrainer.Helpers.Util;
import com.sklerbidi.handsealstrainer.MiniGames.GuessTheSign.GuessTheSignDifficulty;
import com.sklerbidi.handsealstrainer.MiniGames.GuessTheSign.GuessTheSignGameLoading;
import com.sklerbidi.handsealstrainer.MiniGames.TicTacSign.TicTacSignDifficulty;
import com.sklerbidi.handsealstrainer.MiniGames.TicTacSign.TicTacSignGame;
import com.sklerbidi.handsealstrainer.MiniGames.TicTacSign.TicTacSignLoading;
import com.sklerbidi.handsealstrainer.R;

public class GameContainer extends AppCompatActivity {

    private static final long LOADING_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_container);

        Intent intent = getIntent();
        String game = intent.getStringExtra("game");

        if(game != null){
            if(game.equals("guess_the_sign")){
                GuessTheSignGameLoading guessTheSignGameLoading = new GuessTheSignGameLoading();
                Util.openFragment(getSupportFragmentManager(), R.id.container_game, guessTheSignGameLoading, "guess_the_sign_loading", false);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    GuessTheSignDifficulty guessTheSignDifficulty = new GuessTheSignDifficulty();
                    Util.openFragment(getSupportFragmentManager(), R.id.container_game, guessTheSignDifficulty, "guess_the_sign_difficulty", true);
                }, LOADING_DELAY);
            }else if(game.equals("tic_tac_sign")){
                TicTacSignLoading ticTacSignLoading = new TicTacSignLoading();
                Util.openFragment(getSupportFragmentManager(), R.id.container_game, ticTacSignLoading, "tic_tac_sign_loading", false);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    TicTacSignDifficulty ticTacSignDifficulty = new TicTacSignDifficulty();
                    Util.openFragment(getSupportFragmentManager(), R.id.container_game, ticTacSignDifficulty, "tic_tac_sign_difficulty", true);
                }, LOADING_DELAY);
            }
        }
    }
}