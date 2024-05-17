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

public class Multiplication extends AppCompatActivity {
    TextView score, life, time, question;
    EditText answer;
    Button btnOk, btnNextQuestion;
    Random random = new Random(100);
    int userAnswer, correctAnswer, selectedAnswer;
    int userScore = 0, userLife = 3;
    CountDownTimer timer;
    public static final long START_TIMER_IN_MILLIS = 60000;
    long time_left_in_millis = START_TIMER_IN_MILLIS;
    boolean isTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multiplication);
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

        btnOk = findViewById(R.id.buttonOK);
        btnNextQuestion = findViewById(R.id.buttonNextQuestion);
        gameLogic();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = Integer.parseInt(answer.getText().toString());
                pauseTimer();
                if(userAnswer == correctAnswer){//if correct: increase the score by 10, pause the time,
                    question.setText(R.string.congratulations_your_answer_is_correct);
                    userScore +=10;
                    score.setText(String.valueOf(userScore));

                }else{
                    question.setText(R.string.sorry_your_answer_is_wrong);
                    userLife -=1;
                    life.setText(String.valueOf(userLife));
                }
                answer.setText("");
            }
        });
        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                resetTimer();
                if(userLife ==0){
                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                    Intent intentMultiplication = new Intent(Multiplication.this, Result.class);
                    intentMultiplication.putExtra("multiplication score", userScore); // needs the reception of the intent in Results.
                    startActivity(intentMultiplication);
                    finish();
                }else{
                    gameLogic();
                   // resetTimer();
                }
            }
        });


    }
    public void gameLogic() {
        int number2 = random.nextInt(20);
        int number3 = random.nextInt(20);

        correctAnswer = number2 * number3;
        question.setText(String.format("%d * %d", number2, number3));

        startTimer();
    }
    public void startTimer(){
        timer = new CountDownTimer(time_left_in_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_millis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                isTimerRunning= false;
                pauseTimer();
                resetTimer();
                updateTimer();
                userLife -=1;
                question.setText(R.string.sorry_time_is_up);
            }
        }.start();
        isTimerRunning = true;
    }

    public void updateTimer(){
        //time left
        int second = (int)(time_left_in_millis/1000) %60;
        String time_left = (String.format(Locale.getDefault(),"%02d", second));
        time.setText(time_left);

    }
    public void pauseTimer(){
        timer.cancel();
        isTimerRunning = false;
    }
    public void resetTimer(){
        time_left_in_millis = START_TIMER_IN_MILLIS;
        updateTimer();
        isTimerRunning = true;

    }


}
