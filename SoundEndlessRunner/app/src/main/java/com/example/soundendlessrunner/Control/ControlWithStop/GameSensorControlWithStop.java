package com.example.soundendlessrunner.Control.ControlWithStop;

import android.hardware.SensorEvent;
import android.util.Log;

import com.example.soundendlessrunner.Control.GameSensorControl;
import com.example.soundendlessrunner.GameActivity;

public class GameSensorControlWithStop extends GameSensorControl {
    final float MIN_RESET_ROTATION = 0.2f;
    final float MIN_CONTINUE_RESET_ROTATION = 0.f;
    final float MIN_CONTINUE_ROTATION = -0.3f;
    boolean resetedContinue = true;

    public GameSensorControlWithStop(GameActivity gameActivity) {
        super(gameActivity);
    }

    @Override
    public void manageEvent(SensorEvent sensorEvent) {
        float xVal = sensorEvent.values[1];
        float yVal = sensorEvent.values[0];

        if (xVal < MIN_RESET_ROTATION && xVal > -MIN_RESET_ROTATION) {
            resetedPosition = true;
        } else if (resetedPosition) {
            if (xVal > MIN_ROTATION) {
                Log.d("sensor", "LEFT");
                resetedPosition = false;
                gameActivity.moveRightIfPossible();
            } else if (xVal < -MIN_ROTATION) {
                resetedPosition = false;
                gameActivity.moveLeftIfPossible();
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
