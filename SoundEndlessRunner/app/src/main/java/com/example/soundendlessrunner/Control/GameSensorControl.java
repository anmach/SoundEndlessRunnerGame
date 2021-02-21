package com.example.soundendlessrunner.Control;

import android.hardware.SensorEvent;

import com.example.soundendlessrunner.GameManager;

public class GameSensorControl {
    protected final float MIN_ROTATION = 0.3f;
    protected boolean resetedPosition = true;

    protected GameManager gameManager;

    public GameSensorControl(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void manageEvent(SensorEvent sensorEvent){
        float xVal = sensorEvent.values[1];

        if(xVal < MIN_ROTATION && xVal > -MIN_ROTATION){
            resetedPosition = true;
        }
        else if(resetedPosition == true){
            if(xVal > MIN_ROTATION){
                resetedPosition = false;
                gameManager.moveRightIfPossible();
            }
            else if(xVal < -MIN_ROTATION){
                resetedPosition = false;
                gameManager.moveLeftIfPossible();
            }
        }

    }
}
