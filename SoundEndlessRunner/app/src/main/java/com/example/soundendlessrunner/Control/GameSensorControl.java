package com.example.soundendlessrunner.Control;

import android.hardware.SensorEvent;

import com.example.soundendlessrunner.GameActivity;
import com.example.soundendlessrunner.GameData;

public class GameSensorControl {
    final float MIN_ROTATION = 0.3f;
    boolean resetedPosition = true;

    private GameData gameData;
    private GameActivity gameActivity;

    public GameSensorControl(GameData gameData, GameActivity gameActivity) {
        this.gameData = gameData;
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
                gameData.moveRightIfPossible();
                gameActivity.changeNoOfTrackTextAndSound();
            }
            else if(xVal < -MIN_ROTATION){
                resetedPosition = false;
                gameData.moveLeftIfPossible();
                gameActivity.changeNoOfTrackTextAndSound();
            }
        }

    }
}
