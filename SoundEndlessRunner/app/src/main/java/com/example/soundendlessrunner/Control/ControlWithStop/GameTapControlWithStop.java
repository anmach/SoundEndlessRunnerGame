package com.example.soundendlessrunner.Control.ControlWithStop;

import android.view.MotionEvent;

import com.example.soundendlessrunner.Control.GameTapControl;
import com.example.soundendlessrunner.GameActivity;

public class GameTapControlWithStop extends GameTapControl {
    public GameTapControlWithStop(GameActivity gameActivity, float screenCenter) {
        super(gameActivity,screenCenter);
    }

    @Override
    public void onLongPress(MotionEvent event) {
        super.onLongPress(event);
        gameActivity.continueGame();
    }
}
