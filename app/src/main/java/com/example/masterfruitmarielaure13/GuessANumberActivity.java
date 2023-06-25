package com.example.masterfruitmarielaure13;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuessANumberActivity extends AppCompatActivity {
    private enum Difficulty {
        EASY(1, 10),
        MEDIUM(1, 50),
        HARD(1, 100);

        private final int min;
        private final int max;

        Difficulty(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    private Difficulty difficulty;
    private EditText numberEditText;
    private Button validateButton;
    private ProgressBar progressBar;
    private TextView attemptsTextView;
    private TextView resultTextView;
    private TextView scoreTextView;
    private RadioGroup difficultyRadioGroup;
    private Button startButton;
    private CheckBox changeDifficultyCheckBox;
    private List<Integer> attempts;
    private int score;
    private int targetNumber;
    private int remainingAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_anumber);

        numberEditText = findViewById(R.id.numberEditText);
        validateButton = findViewById(R.id.validateButton);
        progressBar = findViewById(R.id.progressBar);
        attemptsTextView = findViewById(R.id.attemptsTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        startButton = findViewById(R.id.startButton);
        changeDifficultyCheckBox = findViewById(R.id.changeDifficultyCheckBox);

        attempts = new ArrayList<>();
        score = 0;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateNumber();
            }
        });
    }

    private void startGame() {
        difficultyRadioGroup.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        numberEditText.setVisibility(View.VISIBLE);
        validateButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        attemptsTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        changeDifficultyCheckBox.setVisibility(View.VISIBLE);

        difficulty = getSelectedDifficulty();
        targetNumber = generateRandomNumber(difficulty);
        remainingAttempts = 10;
        attempts.clear();
        score = 0;

        progressBar.setMax(10);
        progressBar.setProgress(0);
        attemptsTextView.setText("Tentatives: 0/10");
        scoreTextView.setText("Score: 0");
        resultTextView.setText("");

        numberEditText.setText("");
        numberEditText.requestFocus();
    }

    private void validateNumber() {
        String input = numberEditText.getText().toString();
        if (!input.isEmpty()) {
            int guessedNumber = Integer.parseInt(input);
            attempts.add(guessedNumber);

            if (guessedNumber == targetNumber) {
                score += remainingAttempts;
                resultTextView.setText("Bravo, vous avez trouvé le nombre !");
                resultTextView.setTextColor(getResources().getColor(R.color.colorCorrect));
                progressBar.setProgress(10);
            } else {
                if (guessedNumber < targetNumber) {
                    resultTextView.setText("Le nombre est plus grand !");
                } else {
                    resultTextView.setText("Le nombre est plus petit !");
                }
                resultTextView.setTextColor(getResources().getColor(R.color.colorIncorrect));

                remainingAttempts--;
                progressBar.setProgress(10 - remainingAttempts);
                attemptsTextView.setText("Tentatives: " + (10 - remainingAttempts) + "/10");

                if (remainingAttempts == 0) {
                    resultTextView.append("\n\nPartie terminée !");
                    resultTextView.setTextColor(getResources().getColor(R.color.colorGameOver));

                    // Modifier la visibilité des éléments après la fin de la partie
                    difficultyRadioGroup.setVisibility(View.VISIBLE);
                    startButton.setVisibility(View.VISIBLE);
                    numberEditText.setVisibility(View.GONE);
                    validateButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    attemptsTextView.setVisibility(View.GONE);
                    resultTextView.setVisibility(View.VISIBLE);
                    scoreTextView.setVisibility(View.GONE);
                    changeDifficultyCheckBox.setVisibility(View.GONE);
                }
            }

            numberEditText.setText("");
            numberEditText.requestFocus();

            updateScore();
            updateDifficulty();
        }
    }

    private Difficulty getSelectedDifficulty() {
        int selectedId = difficultyRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioEasy) {
            return Difficulty.EASY;
        } else if (selectedId == R.id.radioMedium) {
            return Difficulty.MEDIUM;
        } else {
            return Difficulty.HARD;
        }
    }

    private int generateRandomNumber(Difficulty difficulty) {
        Random random = new Random();
        return random.nextInt(difficulty.getMax() - difficulty.getMin() + 1) + difficulty.getMin();
    }

    private void updateScore() {
        scoreTextView.setText("Score: " + score);
    }

    private void updateDifficulty() {
        if (changeDifficultyCheckBox.isChecked()) {
            difficultyRadioGroup.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
            numberEditText.setVisibility(View.GONE);
            validateButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            attemptsTextView.setVisibility(View.GONE);
            resultTextView.setVisibility(View.GONE);
            scoreTextView.setVisibility(View.GONE);
            changeDifficultyCheckBox.setVisibility(View.GONE);
        }
    }
}
