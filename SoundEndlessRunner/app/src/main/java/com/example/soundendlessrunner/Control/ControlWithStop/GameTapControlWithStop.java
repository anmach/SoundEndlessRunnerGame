package com.example.soundendlessrunner.Control.ControlWithStop;

import android.view.MotionEvent;

import com.example.soundendlessrunner.Control.GameTapControl;
import com.example.soundendlessrunner.GameManager;

public class GameTapControlWithStop extends GameTapControl {
    public GameTapControlWithStop(GameManager gameManager, float screenCenter) {
        super(gameManager,screenCenter);
    }

    @Override
    public void onLongPress(MotionEvent event) {
        super.onLongPress(event);
        gameManager.continueGame();
    }
}
