package tamagotchi.controller;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    Timer timer;
    int counter = 0;

    MyTimer(int period, int counter, Timer timer) {
        this.timer = timer;
        counter++;
        if (counter == 3) {
            timer.cancel();
            timer.purge();
            return;
        }

        int finalCounter = counter;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new MyTimer(period, finalCounter, timer);
            }
        }, period * 1000);
    }

    MyTimer(int period, Timer timer) {
        this.timer = timer;
        counter++;

        if (counter == 3) {
            timer.cancel();
            timer.purge();
            return;
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new MyTimer(period, counter, timer);
            }
        }, period * 1000);
    }
}
