package com.sklerbidi.handsealstrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sklerbidi.handsealstrainer.Camera.ACamera;
import com.sklerbidi.handsealstrainer.Camera.BCamera;
import com.sklerbidi.handsealstrainer.Camera.CCamera;
import com.sklerbidi.handsealstrainer.Camera.DCamera;
import com.sklerbidi.handsealstrainer.Camera.ECamera;
import com.sklerbidi.handsealstrainer.Camera.FCamera;
import com.sklerbidi.handsealstrainer.Camera.GCamera;
import com.sklerbidi.handsealstrainer.Camera.HCamera;
import com.sklerbidi.handsealstrainer.Camera.ICamera;
import com.sklerbidi.handsealstrainer.Camera.JCamera;
import com.sklerbidi.handsealstrainer.Camera.KCamera;
import com.sklerbidi.handsealstrainer.Camera.LCamera;
import com.sklerbidi.handsealstrainer.Camera.MCamera;
import com.sklerbidi.handsealstrainer.Camera.NCamera;
import com.sklerbidi.handsealstrainer.Camera.OCamera;
import com.sklerbidi.handsealstrainer.Camera.PCamera;
import com.sklerbidi.handsealstrainer.Camera.QCamera;
import com.sklerbidi.handsealstrainer.Camera.RCamera;
import com.sklerbidi.handsealstrainer.Camera.SCamera;
import com.sklerbidi.handsealstrainer.Camera.TCamera;
import com.sklerbidi.handsealstrainer.Camera.UCamera;
import com.sklerbidi.handsealstrainer.Camera.VCamera;
import com.sklerbidi.handsealstrainer.Camera.WCamera;
import com.sklerbidi.handsealstrainer.Camera.XCamera;
import com.sklerbidi.handsealstrainer.Camera.YCamera;

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
        switch (letter.toLowerCase()) {
            case "a":
                startActivity(new Intent(this, ACamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "b":
                startActivity(new Intent(this, BCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "c":
                startActivity(new Intent(this, CCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "d":
                startActivity(new Intent(this, DCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "e":
                startActivity(new Intent(this, ECamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "f":
                startActivity(new Intent(this, FCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "g":
                startActivity(new Intent(this, GCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "h":
                startActivity(new Intent(this, HCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "i":
                startActivity(new Intent(this, ICamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "j":
                startActivity(new Intent(this, JCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "k":
                startActivity(new Intent(this, KCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "l":
                startActivity(new Intent(this, LCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "m":
                startActivity(new Intent(this, MCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "n":
                startActivity(new Intent(this, NCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "o":
                startActivity(new Intent(this, OCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "p":
                startActivity(new Intent(this, PCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "q":
                startActivity(new Intent(this, QCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "r":
                startActivity(new Intent(this, RCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "s":
                startActivity(new Intent(this, SCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "t":
                startActivity(new Intent(this, TCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "u":
                startActivity(new Intent(this, UCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "v":
                startActivity(new Intent(this, VCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "w":
                startActivity(new Intent(this, WCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "x":
                startActivity(new Intent(this, XCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case "y":
                startActivity(new Intent(this, YCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            default:
                // Handle the case where the letter is not recognized
                break;
        }
    }
}