package com.example.therealeggwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Space;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TimerWatchable{
    private static final String TAG = "MyActivity";
    ImageButton smilingEggButton;
    ImageButton softEggButton;
    ImageButton hardEggButton;
    Button timerButton;
    StopWatchCounter counter = new StopWatchCounter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickButtons();
    }

    //Adds the onclick function to or 3 different eggs
    void setOnClickButtons() {
        smilingEggButton = (ImageButton) findViewById(R.id.smiling_egg_button);
        softEggButton = (ImageButton) findViewById(R.id.softboiled_egg_button);
        hardEggButton = (ImageButton) findViewById(R.id.hardboiled_egg_button);
        timerButton = (Button) findViewById(R.id.start_timer_button);
        TextView tv = (TextView) findViewById(R.id.responseView);

        //adding on click to the buttons.
        smilingEggButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv.setText("7:00");
                timerButton.setEnabled(true);
                startCountDownTimer();
            }
        });

        softEggButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv.setText("5:00");
                timerButton.setEnabled(true);
                startCountDownTimer();
            }
        });

        hardEggButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tv.setText("10:00");
                timerButton.setEnabled(true);
                startCountDownTimer();
            }
        });
    }

    //Gets the count down timer from the TextView.
    long getStopWatchtime(){
        TextView tv = (TextView) findViewById(R.id.responseView);
        String textViewInput = tv.getText().toString().replace(":", "");
        int tempStringValue = Integer.valueOf(textViewInput);
        //Count down timer is in milliseconds.
        long startCountDownTimer = 0;
        switch(tempStringValue) {
            case 1000:
                startCountDownTimer = 600000; //10min
                return startCountDownTimer;
            case 700:
                startCountDownTimer = 420000; //7min
                return startCountDownTimer;
            case 500:
                startCountDownTimer = 300000;//5min
                return startCountDownTimer;
            default:
                //if we get to here then we will check in the stop watch for the value of the count timer
                return startCountDownTimer;
        }
    }

    //Disables the counter UI
    public void disableCounterVisible(){
        TextView tv = (TextView) findViewById(R.id.responseView);
        Space space = (Space) findViewById(R.id.viewSpace);
        TextView eggTimerMin = (TextView) findViewById(R.id.egg_timer_min);
        TextView eggTimerSec = (TextView) findViewById(R.id.egg_timer_sec);
        TextView responseView = (TextView) findViewById(R.id.responseView);
        space.setVisibility(View.GONE);
        eggTimerMin.setVisibility(View.GONE);
        eggTimerSec.setVisibility(View.GONE);
        responseView.setVisibility(View.VISIBLE);
        responseView.setText("done!");
    }

    //Enable the counter UI
    void setCounterVisible(){
        TextView tv = (TextView) findViewById(R.id.responseView);
        Space space = (Space) findViewById(R.id.viewSpace);
        TextView eggTimerMin = (TextView) findViewById(R.id.egg_timer_min);
        TextView eggTimerSec = (TextView) findViewById(R.id.egg_timer_sec);
        tv.setVisibility(View.GONE);
        space.setVisibility(View.VISIBLE);
        eggTimerMin.setVisibility(View.VISIBLE);
        eggTimerSec.setVisibility(View.VISIBLE);
    }

    //Gets seconds till done from stop watch
    public void updateUIFromStopWatch(long minutes, long seconds){
        Log.d(TAG, minutes + "m");
        Log.d(TAG, seconds + "s");
        if (minutes < 0 && seconds < 0){
            disableCounterVisible();
            counter.removeListner(this);
        }
        TextView eggTimerMin = (TextView) findViewById(R.id.egg_timer_min);
        TextView eggTimerSec = (TextView) findViewById(R.id.egg_timer_sec);
        eggTimerMin.setText(minutes + "Minutes");
        eggTimerSec.setText(seconds + "Seconds");
    }


    void startCountDownTimer(){
        timerButton = (Button) findViewById(R.id.start_timer_button);
        counter.addListener(this);
        timerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCounterVisible();
                counter.startStopWatch(getStopWatchtime());
            }
        });
    }

    @Override
    public void updateUi(long minutes, long seconds) {
        updateUIFromStopWatch(minutes,seconds);
    }
}