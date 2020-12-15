package com.example.soundendlessrunner.Control;

import android.hardware.SensorEvent;

import com.example.soundendlessrunner.GameActivity;

public class GameSensorControl {
    protected final float MIN_ROTATION = 0.3f;
    protected boolean resetedPosition = true;

    protected GameActivity gameActivity;

    public GameSensorControl(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public void manageEvent(SensorEvent sensorEvent){
        float xVal = sensorEvent.values[1];

        if(xVal < MIN_ROTATION && xVal > -MIN_ROTATION){
            resetedPosition = true;
        }
        else if(resetedPosition == true){
            if(xVal > MIN_ROTATION){
                resetedPosition = false;
                gameActivity.moveRightIfPossible();
            }
            else if(xVal < -MIN_ROTATION){
                resetedPosition = false;
                gameActivity.moveLeftIfPossible();
            }
        }

    }
}
