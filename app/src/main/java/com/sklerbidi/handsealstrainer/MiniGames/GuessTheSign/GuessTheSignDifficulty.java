package com.sklerbidi.handsealstrainer.MiniGames.GuessTheSign;

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
import com.sklerbidi.handsealstrainer.R;

public class GuessTheSignDifficulty extends Fragment {

    Button btn_easy, btn_medium, btn_hard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guess_the_sign_difficulty, container, false);

        findView(view);

        btn_easy.setOnClickListener(v -> {
            openGuessTheSignGame("Easy");
        });

        btn_medium.setOnClickListener(v -> {
            openGuessTheSignGame("Medium");
        });

        btn_hard.setOnClickListener(v -> {
            openGuessTheSignGame("Hard");
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

    private void openGuessTheSignGame(String difficulty) {
        GuessTheSignGame guessTheSignGame = new GuessTheSignGame();

        Bundle bundle = new Bundle();
        bundle.putString("difficulty", difficulty);
        guessTheSignGame.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        Util.openFragment(fragmentManager, R.id.container_game, guessTheSignGame, "guess_the_sign_game", true);
    }

}