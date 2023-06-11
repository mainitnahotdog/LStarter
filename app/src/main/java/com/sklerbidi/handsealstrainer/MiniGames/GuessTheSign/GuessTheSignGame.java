package com.sklerbidi.handsealstrainer.MiniGames.GuessTheSign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sklerbidi.handsealstrainer.Helpers.Util;
import com.sklerbidi.handsealstrainer.MiniGames.GameActivity;
import com.sklerbidi.handsealstrainer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class GuessTheSignGame extends Fragment implements View.OnClickListener {

    private static final int NUM_OPTIONS = 8;
    private static final int NUM_QUESTIONS = 10;
    private static int QUESTION_TIME_LIMIT_MS = 0;

    private TextView tv_mode;
    private TextView tv_questionNum;
    private TextView tv_correctAnswersNum;
    private TextView tv_timer;
    private ImageView img_guess;
    private Button[] btn_options;
    private Button btn_exit;
    private Button btn_restart;
    private Button btn_restart1;
    private Button btn_quit;
    private Button btn_change_difficulty;
    private Button btn_change_difficulty2;
    private String correctAnswer;
    private String difficulty;
    private int currentQuestion;
    private int correctAnswers;
    private List<String> allOptions;
    private List<String> currentOptions;
    private CountDownTimer timer;
    private CardView card_result;
    private KonfettiView konfettiView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_guess_the_sign_game, container, false);

        findViews(view);
        retrieveBundle();
        setModeText();
        setupGame();
        setupListeners();
        startQuestion();

        return view;
    }

    private void changeDifficulty() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Difficulty");
        builder.setMessage("Are you sure you want to change the difficulty?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            GuessTheSignDifficulty guessTheSignDifficulty = new GuessTheSignDifficulty();
            FragmentManager fragmentManager = getParentFragmentManager();
            Util.openFragment(fragmentManager, R.id.container_game, guessTheSignDifficulty, "guess_the_sign_difficulty", true);
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void quitGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Quit Game");
        builder.setMessage("Are you sure you want to quit the game?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void findViews(View view) {
        tv_mode = view.findViewById(R.id.tv_mode);
        tv_questionNum = view.findViewById(R.id.tv_question_num);
        tv_correctAnswersNum = view.findViewById(R.id.tv_correct_answers_num);
        tv_timer = view.findViewById(R.id.tv_timer);
        img_guess = view.findViewById(R.id.img_guess);
        btn_options = new Button[NUM_OPTIONS];
        btn_options[0] = view.findViewById(R.id.btn_option1);
        btn_options[1] = view.findViewById(R.id.btn_option2);
        btn_options[2] = view.findViewById(R.id.btn_option3);
        btn_options[3] = view.findViewById(R.id.btn_option4);
        btn_options[4] = view.findViewById(R.id.btn_option5);
        btn_options[5] = view.findViewById(R.id.btn_option6);
        btn_options[6] = view.findViewById(R.id.btn_option7);
        btn_options[7] = view.findViewById(R.id.btn_option8);
        btn_exit = view.findViewById(R.id.btn_exit);
        btn_restart = view.findViewById(R.id.btn_restart);
        btn_restart1 = view.findViewById(R.id.btn_final_restart);
        btn_quit = view.findViewById(R.id.btn_final_quit);
        btn_exit = view.findViewById(R.id.btn_exit);
        btn_change_difficulty = view.findViewById(R.id.btn_change_difficulty);
        btn_change_difficulty2 = view.findViewById(R.id.btn_final_change_difficulty);
        card_result = view.findViewById(R.id.card_result);
        konfettiView = view.findViewById(R.id.konfetti_view);
    }

    private void setOptionButtonListeners() {
        for (Button option : btn_options) {
            option.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Button selectedButton = (Button) view;
        String selectedOption = selectedButton.getText().toString();
        checkAnswer(selectedOption);

        moveToNextQuestion();
    }

    private void showEndGameView() {
        card_result.setVisibility(View.VISIBLE);
        if (getView() != null) {
            TextView tv_final_score = getView().findViewById(R.id.tv_final_score);
            TextView tv_message = getView().findViewById(R.id.tv_message);

            String finalScoreText = "Final Score: " + correctAnswers + "/" + NUM_QUESTIONS;
            tv_final_score.setText(finalScoreText);

            if (correctAnswers == NUM_QUESTIONS) {
                tv_message.setText("PERFECT");
            } else {
                tv_message.setText("GAME OVER");
            }

            konfettiView.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5))
                    .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                    .streamFor(200, 2000L);
        }
    }

    private void restartGame() {
        card_result.setVisibility(View.GONE);
        currentQuestion = 1;
        correctAnswers = 0;
        allOptions = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "J", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"));
        currentOptions = new ArrayList<>();

        for (int i = 0; i < NUM_OPTIONS; i++) {
            currentOptions.add("");
        }

        correctAnswer = null;

        konfettiView.reset();

        setOptionsText();
        setCurrentQuestionNumText();
        setCorrectAnswersNumText();

        startQuestion();
    }

    private void incrementCorrectAnswers() {
        correctAnswers++;
        setCorrectAnswersNumText();
    }

    private void moveToNextQuestion() {
        currentQuestion++;
        startQuestion();
    }

    private void retrieveBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            difficulty = bundle.getString("difficulty");
            if(difficulty.equalsIgnoreCase("easy")){
                QUESTION_TIME_LIMIT_MS = 15000;
            }else if(difficulty.equalsIgnoreCase("medium")){
                QUESTION_TIME_LIMIT_MS = 8000;
            }else{
                QUESTION_TIME_LIMIT_MS = 2000;
            }
        }
    }

    private void setModeText() {
        if (difficulty != null) {
            String modeText = difficulty;
            tv_mode.setText(modeText);
        }
    }

    private void setupGame() {
        currentQuestion = 1;
        correctAnswers = 0;
        allOptions = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I","K", "L", "M", "J", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"));
        currentOptions = new ArrayList<>();
        for (int i = 0; i < NUM_OPTIONS; i++) {
            currentOptions.add("");
        }
    }

    private void setupListeners() {
        setOptionButtonListeners();

        btn_restart.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Restart Game");
            builder.setMessage("Are you sure you want to restart the game?");
            builder.setPositiveButton("Yes", (dialog, which) -> restartGame());
            builder.setNegativeButton("No", null);
            builder.show();
        });

        btn_restart1.setOnClickListener(v -> {
            restartGame();
        });

        btn_change_difficulty.setOnClickListener(v -> {
            changeDifficulty();
        });

        btn_change_difficulty2.setOnClickListener(v -> {
            changeDifficulty();
        });

        btn_exit.setOnClickListener(v -> {
            quitGame();
        });

        btn_quit.setOnClickListener(v -> {
            quitGame();
        });
    }

    private void startQuestion() {
        if (timer != null) {
            timer.cancel();
        }

        if (currentQuestion <= NUM_QUESTIONS) {
            Collections.shuffle(allOptions);
            String correctOption = allOptions.get(0);
            Collections.shuffle(allOptions); // Shuffle again to randomize the order of all options

            currentOptions.set(0, correctOption);
            for (int i = 1; i < NUM_OPTIONS; i++) {
                currentOptions.set(i, allOptions.get(i));
            }

            correctAnswer = getRandomLetter();

            boolean isCorrectAnswerPresent = currentOptions.contains(correctAnswer);

            // If the correct answer is already present, find another random letter
            while (isCorrectAnswerPresent) {
                correctAnswer = getRandomLetter();
                isCorrectAnswerPresent = currentOptions.contains(correctAnswer);
            }

            // Set the correct answer in one of the options randomly
            int correctOptionIndex = new Random().nextInt(NUM_OPTIONS);
            currentOptions.set(correctOptionIndex, correctAnswer);

            setOptionsText();
            setCurrentQuestionNumText();
            setCorrectAnswersNumText();
            startTimer();

            String resourceName = "guess" + correctAnswer;
            Log.wtf("wtf", "showed image: " + resourceName);

            int resourceId = getResources().getIdentifier(resourceName, "drawable", requireContext().getPackageName());
            img_guess.setImageResource(resourceId);
        } else {
            showEndGameView();
        }
    }


    private final Set<Character> usedLetters = new HashSet<>();

    private String getRandomLetter() {
        Random random = new Random();
        char randomChar;

        do {
            randomChar = (char) (random.nextInt(26) + 'a');
        } while (randomChar == 'z' || usedLetters.contains(randomChar));

        usedLetters.add(randomChar);
        return String.valueOf(randomChar);
    }

    private void setOptionsText() {
        Collections.shuffle(currentOptions); // Shuffle all options
        for (int i = 0; i < NUM_OPTIONS; i++) {
            btn_options[i].setText(currentOptions.get(i));
            btn_options[i].setEnabled(true); // Enable all buttons
        }
    }

    private void setCurrentQuestionNumText() {
        String questionNumText = String.format(Locale.ENGLISH, "%d/%d", currentQuestion, NUM_QUESTIONS);
        tv_questionNum.setText(questionNumText);
    }

    private void setCorrectAnswersNumText() {
        String correctAnswersNumText = String.valueOf(correctAnswers);
        tv_correctAnswersNum.setText(correctAnswersNumText);
    }

    private void startTimer() {
        timer = new CountDownTimer(QUESTION_TIME_LIMIT_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                String timerText = String.valueOf(secondsRemaining);
                tv_timer.setText(timerText);
            }

            @Override
            public void onFinish() {
                // Time's up, handle end of question
                goToNextQuestion();
            }
        }.start();
    }

    private void checkAnswer(String selectedOption) {
        String correctOption = correctAnswer;
        Log.wtf("wtf", correctOption + " : " + selectedOption);
        if (selectedOption.equalsIgnoreCase(correctOption)) {
            incrementCorrectAnswers();
            Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToNextQuestion() {
        currentQuestion++;
        timer.cancel();
        startQuestion();
    }

}