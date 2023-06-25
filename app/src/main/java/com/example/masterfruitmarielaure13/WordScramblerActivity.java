package com.example.masterfruitmarielaure13;

import androidx.appcompat.app.AppCompatActivity;
// WordScramblerActivity.java

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordScramblerActivity extends AppCompatActivity {

    private static final int EASY_TIME_LIMIT = 30;
    private static final int MEDIUM_TIME_LIMIT = 60;
    private static final int HARD_TIME_LIMIT = 90;

    private TextView difficultyTextView;
    private RadioGroup difficultyRadioGroup;
    private RadioButton easyRadioButton;
    private RadioButton mediumRadioButton;
    private RadioButton hardRadioButton;
    private Button startButton;
    private TextView scrambledWordTextView;
    private TextView inputWordTextView;
    private EditText wordEditText;
    private TextView attemptsTextView;
    private TextView remainingTimeTextView;
    private Button validateButton;

    private String word;
    private String scrambledWord;
    private int timeLimit;
    private int remainingTime;
    private int attempts;
    private int maxAttempts;
    private CountDownTimer countDownTimer;

    private List<String> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_scrambler);

        // Initialize views
        difficultyTextView = findViewById(R.id.difficultyTextView);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        easyRadioButton = findViewById(R.id.radioEasy);
        mediumRadioButton = findViewById(R.id.radioMedium);
        hardRadioButton = findViewById(R.id.radioHard);
        startButton = findViewById(R.id.startButton);
        scrambledWordTextView = findViewById(R.id.scrambledWordTextView);
        inputWordTextView = findViewById(R.id.inputWordTextView);
        wordEditText = findViewById(R.id.wordEditText);
        attemptsTextView = findViewById(R.id.attemptsTextView);
        remainingTimeTextView = findViewById(R.id.remainingTimeTextView);
        validateButton = findViewById(R.id.validateButton);

        // Hide unnecessary views
        scrambledWordTextView.setVisibility(View.GONE);
        inputWordTextView.setVisibility(View.GONE);
        wordEditText.setVisibility(View.GONE);
        attemptsTextView.setVisibility(View.GONE);
        remainingTimeTextView.setVisibility(View.GONE);
        validateButton.setVisibility(View.GONE);

        // Initialize word list
        wordList = new ArrayList<>();
        wordList.add("informatique");
        wordList.add("python");
        wordList.add("webstart");
        wordList.add("chocolat");
        wordList.add("developpeur");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateWord();
            }
        });
    }

    private void startGame() {
        // Get selected difficulty
        Difficulty difficulty;
        if (easyRadioButton.isChecked()) {
            difficulty = Difficulty.EASY;
            timeLimit = EASY_TIME_LIMIT;
        } else if (mediumRadioButton.isChecked()) {
            difficulty = Difficulty.MEDIUM;
            timeLimit = MEDIUM_TIME_LIMIT;
        } else {
            difficulty = Difficulty.HARD;
            timeLimit = HARD_TIME_LIMIT;
        }

        // Choose a random word
        word = getRandomWord(difficulty);
        scrambledWord = scrambleWord(word);
        remainingTime = timeLimit;
        attempts = 0;
        maxAttempts = 10;

        // Update UI
        difficultyTextView.setText("Difficulté: " + difficulty.name());
        scrambledWordTextView.setText("Mot mélangé: " + scrambledWord);
        attemptsTextView.setText("Tentatives: " + attempts + "/" + maxAttempts);
        remainingTimeTextView.setText("Temps restant: " + remainingTime + " secondes");

        // Show necessary views
        difficultyTextView.setVisibility(View.VISIBLE);
        difficultyRadioGroup.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        scrambledWordTextView.setVisibility(View.VISIBLE);
        inputWordTextView.setVisibility(View.VISIBLE);
        wordEditText.setVisibility(View.VISIBLE);
        attemptsTextView.setVisibility(View.VISIBLE);
        remainingTimeTextView.setVisibility(View.VISIBLE);
        validateButton.setVisibility(View.VISIBLE);

        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(remainingTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) (millisUntilFinished / 1000);
                remainingTimeTextView.setText("Temps restant: " + remainingTime + " secondes");
            }

            @Override
            public void onFinish() {
                remainingTime = 0;
                remainingTimeTextView.setText("Temps restant: " + remainingTime + " secondes");
                endGame();
            }
        }.start();
    }

    private void validateWord() {
        String userInput = wordEditText.getText().toString().toLowerCase();
        if (userInput.equals(word)) {
            // Correct guess
            countDownTimer.cancel();
            int score = remainingTime;
            Toast.makeText(this, "Bravo ! Votre score est de " + score + " points", Toast.LENGTH_SHORT).show();
            resetGame();
        } else {
            // Incorrect guess
            attempts++;
            attemptsTextView.setText("Tentatives: " + attempts + "/" + maxAttempts);
            if (attempts == maxAttempts) {
                // Reached maximum attempts
                countDownTimer.cancel();
                Toast.makeText(this, "Vous avez atteint le nombre maximum de tentatives. Le mot était : " + word, Toast.LENGTH_SHORT).show();
                resetGame();
            } else {
                // Show a toast with remaining attempts
                int remainingAttempts = maxAttempts - attempts;
                Toast.makeText(this, "Ce n'est pas le mot. Il vous reste " + remainingAttempts + " tentative(s)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void endGame() {
        Toast.makeText(this, "Temps écoulé ! Le mot était : " + word, Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void resetGame() {
        difficultyTextView.setText("Choisissez un niveau de difficulté :");
        difficultyTextView.setVisibility(View.VISIBLE);
        difficultyRadioGroup.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        scrambledWordTextView.setVisibility(View.GONE);
        inputWordTextView.setVisibility(View.GONE);
        wordEditText.setVisibility(View.GONE);
        attemptsTextView.setVisibility(View.GONE);
        remainingTimeTextView.setVisibility(View.GONE);
        validateButton.setVisibility(View.GONE);

        wordEditText.setText("");
        countDownTimer = null;
    }

    private String getRandomWord(Difficulty difficulty) {
        Random random = new Random();
        List<String> words;
        switch (difficulty) {
            case EASY:
                words = wordList.subList(0, 3);
                break;
            case MEDIUM:
                words = wordList.subList(3, 5);
                break;
            case HARD:
                words = wordList.subList(0, wordList.size());
                break;
            default:
                words = wordList;
                break;
        }
        return words.get(random.nextInt(words.size()));
    }

    private String scrambleWord(String word) {
        char[] letters = word.toCharArray();
        Random random = new Random();
        for (int i = 0; i < letters.length; i++) {
            int j = random.nextInt(letters.length);
            char temp = letters[i];
            letters[i] = letters[j];
            letters[j] = temp;
        }
        return new String(letters);
    }

    private enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
}
