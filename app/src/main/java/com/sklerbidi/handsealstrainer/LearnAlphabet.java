package com.sklerbidi.handsealstrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LearnAlphabet extends AppCompatActivity {

    Button back_btn;
    Map<String, CardView> letterCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        findView();

        for (Map.Entry<String, CardView> entry : letterCards.entrySet()) {
            String letter = entry.getKey();
            CardView cardView = entry.getValue();
            cardView.setOnClickListener(v -> startAlphabetCameraActivity(letter));
        }

        back_btn.setOnClickListener(view -> {
            finish();
        });
    }

    private void findView() {
        back_btn = findViewById(R.id.btn_back1);
        letterCards = new HashMap<>();

        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y"};

        for (String letter : letters) {
            letterCards.put(letter, (CardView) findViewById(getResources().getIdentifier("letter_" + letter, "id", getPackageName())));
        }
    }

    private void startAlphabetCameraActivity(String letter) {
        Intent intent = new Intent(LearnAlphabet.this, Alphabet1Camera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("letter", letter);
        startActivity(intent);
    }
}