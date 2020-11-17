package com.example.soundendlessrunner.Control.ControlWithStop;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.GameActivityWithStop;
import com.example.soundendlessrunner.GameData;

public class GameTapControlWithStop extends GestureDetector.SimpleOnGestureListener {
    //TODO: get screen center somehow
    private float screenCenter = 500;
    private GameData gameData;
    private GameActivityWithStop gameActivity;

    public GameTapControlWithStop(GameData gameData, GameActivityWithStop gameActivity, float screenCenter) {
        this.gameData = gameData;
        this.gameActivity = gameActivity;
        this.screenCenter = screenCenter;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float position = e.getRawX();
        if (position >= screenCenter) {
            gameData.moveRightIfPossible();
        } else {
            gameData.moveLeftIfPossible();
        }
        gameActivity.changeNoOfTrackTextAndSound();
        return super.onSingleTapUp(e);
    }

    @Override
    public void onLongPress(MotionEvent event) {
        super.onLongPress(event);
        gameActivity.continueGame();
    }
}
