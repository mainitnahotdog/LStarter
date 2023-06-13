package com.sklerbidi.handsealstrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Information2Activity extends AppCompatActivity {

    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information2);
        back_btn = findViewById(R.id.btn_back1);

        back_btn.setOnClickListener(view -> {
            Intent intent = new Intent(Information2Activity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}