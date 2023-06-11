package com.sklerbidi.handsealstrainer.MiniGames.TicTacSign;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sklerbidi.handsealstrainer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacSignGame extends Fragment {

    private CardView[] cardTiles = new CardView[9];
    private Button[] btnPieces = new Button[9];

    private static final int[] drawableIds = {
            R.drawable.guessa, R.drawable.guessb, R.drawable.guessc,
            R.drawable.guessd, R.drawable.guesse, R.drawable.guessf,
            R.drawable.guessg, R.drawable.guessh, R.drawable.guessi,
            R.drawable.guessj, R.drawable.guessk, R.drawable.guessl,
            R.drawable.guessm, R.drawable.guessn, R.drawable.guesso,
            R.drawable.guessp, R.drawable.guessq, R.drawable.guessr,
            R.drawable.guesss, R.drawable.guesst, R.drawable.guessu,
            R.drawable.guessv, R.drawable.guessw, R.drawable.guessx,
            R.drawable.guessy
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tic_tac_sign_game, container, false);

        findViews(view);

        startGame();

        return view;
    }

    private void startGame() {
        List<Integer> availableTiles = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Random random = new Random();

        int drawableIndex = 0;
        int maxDrawableIndex = drawableIds.length - 1;

        for (int i = 0; i < cardTiles.length; i++) {
            int randomIndex = random.nextInt(availableTiles.size());
            int tileIndex = availableTiles.get(randomIndex);

            int randomDrawableIndex = drawableIndex % drawableIds.length; // Ensure cyclic selection of drawables
            cardTiles[tileIndex].setBackgroundResource(drawableIds[randomDrawableIndex]);
            btnPieces[tileIndex].setText(getTextFromDrawable(drawableIds[randomDrawableIndex]));

            drawableIndex++;
            availableTiles.remove(randomIndex);
        }
    }

    private String getTextFromDrawable(int drawableId) {
        char character = (char) (drawableId + 97);

        return Character.toString(character).toUpperCase();
    }

    private void findViews(View view) {
        int[] cardIds = {
                R.id.card_tile1, R.id.card_tile2, R.id.card_tile3,
                R.id.card_tile4, R.id.card_tile5, R.id.card_tile6,
                R.id.card_tile7, R.id.card_tile8, R.id.card_tile9
        };

        int[] buttonIds = {
                R.id.btn_piece1, R.id.btn_piece2, R.id.btn_piece3,
                R.id.btn_piece4, R.id.btn_piece5, R.id.btn_piece6,
                R.id.btn_piece7, R.id.btn_piece8, R.id.btn_piece9
        };

        for (int i = 0; i < cardIds.length; i++) {
            cardTiles[i] = view.findViewById(cardIds[i]);
            btnPieces[i] = view.findViewById(buttonIds[i]);
        }
    }

}