package com.sklerbidi.handsealstrainer.MiniGames.TicTacSign;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sklerbidi.handsealstrainer.Helpers.Util;
import com.sklerbidi.handsealstrainer.MiniGames.GameActivity;
import com.sklerbidi.handsealstrainer.MiniGames.GuessTheSign.GuessTheSignGame;
import com.sklerbidi.handsealstrainer.R;

public class TicTacSignDifficulty extends Fragment {

    Button btn_easy, btn_medium, btn_hard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tic_tac_sign_difficulty, container, false);

        findView(view);

        btn_easy.setOnClickListener(v -> {
            openTicTacSignGame("Easy");
        });

        btn_medium.setOnClickListener(v -> {
            openTicTacSignGame("Medium");
        });

        btn_hard.setOnClickListener(v -> {
            openTicTacSignGame("Hard");
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void findView(View view) {
        btn_easy = view.findViewById(R.id.btn_easy);
        btn_medium = view.findViewById(R.id.btn_medium);
        btn_hard = view.findViewById(R.id.btn_god);
    }

    private void openTicTacSignGame(String difficulty) {
        TicTacSignGame ticTacSignGame = new TicTacSignGame();

        Bundle bundle = new Bundle();
        bundle.putString("difficulty", difficulty);
        ticTacSignGame.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        Util.openFragment(fragmentManager, R.id.container_game, ticTacSignGame, "tic_tac_sign_game",true);
    }
}