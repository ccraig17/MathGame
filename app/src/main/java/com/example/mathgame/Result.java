package com.example.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {
    TextView result;
    Button btnPlayAgain;
    Button btnExitGame;
    int score = 0;
    String userScore, userScore1, userScore2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result = findViewById(R.id.textViewResult);
        btnPlayAgain = findViewById(R.id.buttonPlayAgain);
        btnExitGame = findViewById(R.id.buttonExitGame);

        Intent intentScore = getIntent();
        score = intentScore.getIntExtra("score", 0);
        userScore = String.valueOf(score);
        result.setText(String.format("Your Final Score: %s", userScore));

        Intent intentSubtraction = getIntent();
        score = intentSubtraction.getIntExtra("subtraction score", 0);
        userScore1 = String.valueOf(score);
        result.setText(String.format("Your Final Score: %s ", userScore1));

        Intent intentMultiplication = getIntent();
        score = intentMultiplication.getIntExtra("multiplication score", 0);
        userScore2 = String.valueOf(score);
        result.setText(String.format("Your Final Score: %s", userScore2));

        btnPlayAgain.setOnClickListener(v -> {
           Intent intent = new Intent(Result.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnExitGame.setOnClickListener(v -> finish()); //finish() method closes the activity.
    }
}