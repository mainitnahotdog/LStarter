package com.sklerbidi.handsealstrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnGreeting extends AppCompatActivity {
    Button back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        back_btn = findViewById(R.id.btn_back1);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LearnGreeting.this, ASLActivity.class);
                startActivity(intent);
            }
        });
    }
}