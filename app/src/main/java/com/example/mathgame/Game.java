package com.example.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {
    //define ALL Component and initialize them at the start of the activity
    TextView score, life, time, question;
    EditText answer;
    Button btnOK, btnNext;
    Random random = new Random();
    int number1, number2;
    int userAnswer, correctAnswer;
    int userScore = 0, userLife = 3;
    CountDownTimer timer;
    private static final long TIME_START_MILLIS = 60000; //one second = 1,000 milliseconds
    long time_remain_in_Millis = TIME_START_MILLIS;
    boolean timer_running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        score = findViewById(R.id.textViewScore);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        btnOK = findViewById(R.id.buttonOK);
        btnNext = findViewById(R.id.buttonNext);

        gameLogic();

        btnOK.setOnClickListener((View v) -> {
            userAnswer = Integer.parseInt(answer.getText().toString());
            pauseTimer(); //stops the timer when the user clicks to answer the question

            if(userAnswer == correctAnswer){
                userScore +=10;
                score.setText(String.valueOf(userScore));
                question.setText(R.string.congratulations_your_answer_is_correct);
            }else{
                userLife -=1;
                life.setText(String.valueOf(userLife));
                question.setText(R.string.sorry_your_answer_is_wrong);

            }
            answer.setText("");
        });

        btnNext.setOnClickListener((View v)->{
            answer.setText("");
            resetTimer();
            if(userLife == 0){
                Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                Intent intentScore = new Intent(Game.this, Result.class);
                intentScore.putExtra("score", userScore);
                startActivity(intentScore);
                finish();
            }else{
                gameLogic();
            }
        });
    }
    public void gameLogic(){
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);
        correctAnswer = number1 + number2;
        question.setText(String.format("%d + %d", number1, number2));
        startTimer();
    }
    public void startTimer(){
        timer = new CountDownTimer(time_remain_in_Millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_remain_in_Millis = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateTimer();
                userLife -=1;
                String user_life = String.valueOf(userLife);
                life.setText(user_life);
                question.setText(R.string.sorry_time_is_up);

            }
        }.start();
        timer_running = true;
    }
    public void updateTimer(){
        int second = (int)(time_remain_in_Millis /1000) % 60; //converted from a long value to an int (for seconds)
        String timeLeft = String.format(Locale.getDefault(), "%02d", second); // String.format() shows the time in specific format; format will be in 2 digits
        time.setText(timeLeft);
    }
    public void pauseTimer(){
        timer.cancel();
        timer_running = false;
    }
    public void resetTimer(){
        time_remain_in_Millis = TIME_START_MILLIS;
        updateTimer();
    }


}