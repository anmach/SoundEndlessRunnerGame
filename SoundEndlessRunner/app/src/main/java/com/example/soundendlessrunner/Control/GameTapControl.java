package com.example.soundendlessrunner.Control;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.GameActivity;
import com.example.soundendlessrunner.GameData;

public class GameTapControl extends GestureDetector.SimpleOnGestureListener {
    //TODO: get screen center somehow
    private float screenCenter = 500;
    private GameData gameData;
    private GameActivity gameActivity;

    public GameTapControl(GameData gameData, GameActivity gameActivity, float screenCenter) {
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
}
