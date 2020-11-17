package com.example.soundendlessrunner.Control.ControlWithStop;

import android.hardware.SensorEvent;
import android.util.Log;

import com.example.soundendlessrunner.GameActivityWithStop;
import com.example.soundendlessrunner.GameData;

public class GameSensorControlWithStop {
    final float MIN_RESET_ROTATION = 0.2f;
    final float MIN_CONTINUE_RESET_ROTATION = 0.f;
    final float MIN_ROTATION = 0.3f;
    final float MIN_CONTINUE_ROTATION = -0.3f;
    boolean resetedPosition = true;
    boolean resetedContinue = true;

    private GameData gameData;
    private GameActivityWithStop gameActivity;

    public GameSensorControlWithStop(GameData gameData, GameActivityWithStop gameActivity) {
        this.gameData = gameData;
        this.gameActivity = gameActivity;
    }

    public void manageEvent(SensorEvent sensorEvent) {
        float xVal = sensorEvent.values[1];
        float yVal = sensorEvent.values[0];

        if (xVal < MIN_RESET_ROTATION && xVal > -MIN_RESET_ROTATION) {
            resetedPosition = true;
        } else if (resetedPosition) {
            if (xVal > MIN_ROTATION) {
                Log.d("sensor", "LEFT");
                resetedPosition = false;
                gameData.moveRightIfPossible();
                gameActivity.changeNoOfTrackTextAndSound();
            } else if (xVal < -MIN_ROTATION) {
                resetedPosition = false;
                gameData.moveLeftIfPossible();
                gameActivity.changeNoOfTrackTextAndSound();
            }
        }

        if (yVal > MIN_CONTINUE_RESET_ROTATION) {
            resetedContinue = true;
        } else if (resetedContinue && yVal < MIN_CONTINUE_ROTATION) {
            Log.d("sensor", "MOVE");
            resetedContinue = false;
            gameActivity.continueGame();
        }
    }
}
