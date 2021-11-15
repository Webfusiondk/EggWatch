package com.example.therealeggwatch;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class StopWatchCounter {
    ArrayList<TimerWatchable> counterList = new ArrayList<>();
    private static final String TAG = "StopWatchCounter";


    public void startStopWatch(long time){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable runnable = new Runnable() {
                long startCount = time;

                @Override
                public void run() {
                    long minutes = startCount / (60 * 1000) % 60;
                    long seconds = startCount / 1000 % 60;
                    Log.d(TAG, minutes + "Minutes");
                    Log.d(TAG, seconds + "Seconds");
                    startCount -= 1000;

                    notifieListner(minutes, seconds);
                    if (startCount < 0) {
                        Log.d(TAG, "Done");
                        scheduler.shutdown();
                    }
                }
            };
        scheduler.scheduleAtFixedRate(runnable, 0, 1,SECONDS);
    }

    void addListener(TimerWatchable watcher){
        counterList.add(watcher);
    }

    void removeListner(TimerWatchable watcher){
        counterList.remove(watcher);
    }

    void notifieListner(long m, long s){
        for (int i = 0; i < counterList.size(); i++) {
            counterList.get(i).updateUi(m,s);
        }
    }
}
