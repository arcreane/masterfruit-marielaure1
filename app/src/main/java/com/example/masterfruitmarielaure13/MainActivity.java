package com.example.masterfruitmarielaure13;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guessANumberButton = findViewById(R.id.guessANumberButton);
        guessANumberButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, com.example.masterfruitmarielaure13.GuessANumberActivity.class));
        });

        Button wordScramblerButton = findViewById(R.id.wordScramblerButton);
        wordScramblerButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WordScramblerActivity.class));
        });
    }
}
