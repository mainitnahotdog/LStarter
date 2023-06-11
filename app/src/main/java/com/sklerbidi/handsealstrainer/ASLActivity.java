package com.sklerbidi.handsealstrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.opencv.android.OpenCVLoader;

public class ASLActivity extends AppCompatActivity {

    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }

    FloatingActionButton alpha_btn, alpha_btn2,num_btn, greet_btn, camera_btn;
    FrameLayout overlay;
    CardView learn_alphabet, learn_number, learn_greetings;
    private TextView alpha_tv, alpha_tv2,num_tv, greet_tv, realtime_tv;
    private boolean clicked = false;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asl);
        findView();


        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        alpha_btn.setOnClickListener(view -> {
            //REALTIME ALPHABET
            startActivity(new Intent(ASLActivity.this, Alphabet1Camera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

        });

        alpha_btn2.setOnClickListener(view -> startActivity(new Intent(ASLActivity.this, Alphabet1Camera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)));

        num_btn.setOnClickListener(view -> {
            //REALTIME NUMBER
            startActivity(new Intent(ASLActivity.this,NumberCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));


        });

        greet_btn.setOnClickListener(view -> {
            //REALTIME GREETINGS
            startActivity(new Intent(ASLActivity.this,GreetingCamera.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

        });

        learn_alphabet.setOnClickListener(view -> {
            Intent intent = new Intent(ASLActivity.this, LearnAlphabet.class);
            startActivity(intent);
        });

        learn_number.setOnClickListener(view -> {
            Intent intent = new Intent(ASLActivity.this, LearnNumber.class);
            startActivity(intent);
        });

        learn_greetings.setOnClickListener(view -> {
            Intent intent = new Intent(ASLActivity.this, LearnGreeting.class);
            startActivity(intent);
        });

        camera_btn.setOnClickListener(view -> onCameraButtonClicked());

        overlay.setOnClickListener(view -> onCameraButtonClicked());
    }

    @Override
    public void onBackPressed() {
        if(clicked){
            onCameraButtonClicked();
        }else{
            super.onBackPressed();
        }
    }


    private void onCameraButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisibility (boolean clicked){
        if(!clicked){
            alpha_btn2.setVisibility(View.VISIBLE);
            alpha_btn.setVisibility(View.VISIBLE);
            num_btn.setVisibility(View.VISIBLE);
            greet_btn.setVisibility(View.VISIBLE);
            alpha_tv2.setVisibility(View.VISIBLE);
            alpha_tv.setVisibility(View.VISIBLE);
            num_tv.setVisibility(View.VISIBLE);
            greet_tv.setVisibility(View.VISIBLE);
            realtime_tv.setVisibility(View.VISIBLE);
            overlay.setVisibility(View.VISIBLE);
        }else{
            alpha_btn.setVisibility(View.GONE);
            alpha_btn.setVisibility(View.GONE);
            num_btn.setVisibility(View.GONE);
            greet_btn.setVisibility(View.GONE);
            alpha_tv2.setVisibility(View.GONE);
            alpha_tv.setVisibility(View.GONE);
            num_tv.setVisibility(View.GONE);
            greet_tv.setVisibility(View.GONE);
            realtime_tv.setVisibility(View.GONE);
            overlay.setVisibility(View.GONE);
        }
    }

    private void setAnimation (boolean clicked){
        if(!clicked){
            alpha_btn2.startAnimation(fromBottom);
            alpha_btn.startAnimation(fromBottom);
            num_btn.startAnimation(fromBottom);
            greet_btn.startAnimation(fromBottom);
            alpha_tv2.startAnimation(fromBottom);
            alpha_tv.startAnimation(fromBottom);
            num_tv.startAnimation(fromBottom);
            greet_tv.startAnimation(fromBottom);
            camera_btn.startAnimation(rotateOpen);
            realtime_tv.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            overlay.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        }else{
            alpha_btn2.startAnimation(toBottom);
            alpha_btn.startAnimation(toBottom);
            num_btn.startAnimation(toBottom);
            greet_btn.startAnimation(toBottom);
            alpha_tv2.startAnimation(toBottom);
            alpha_tv.startAnimation(toBottom);
            num_tv.startAnimation(toBottom);
            greet_tv.startAnimation(toBottom);
            camera_btn.startAnimation(rotateClose);
            realtime_tv.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
            overlay.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        }
    }

    private void findView(){
        overlay = findViewById(R.id.overlay);
        alpha_btn = findViewById(R.id.realtime_alphabet_btn);
        alpha_btn2 = findViewById(R.id.realtime_alphabet2_btn);
        num_btn = findViewById(R.id.realtime_number_btn);
        greet_btn = findViewById(R.id.realtime_gesture_btn);
        alpha_tv = findViewById(R.id.alphabet_text);
        alpha_tv2 = findViewById(R.id.alphabet_text2);
        greet_tv = findViewById(R.id.gesture_text);
        num_tv = findViewById(R.id.number_text);
        camera_btn = findViewById(R.id.camera_btn);
        realtime_tv = findViewById(R.id.realtime_text);
        learn_alphabet = findViewById(R.id.learn_alphabet);
        learn_number = findViewById(R.id.learn_numbers);
        learn_greetings = findViewById(R.id.learn_greetings);
    }

}