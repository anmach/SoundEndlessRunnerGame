package com.example.soundendlessrunner.Control;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.GameActivity;

public class GameTapControl extends GestureDetector.SimpleOnGestureListener {
    //TODO: get screen center somehow
    protected float screenCenter = 500;
    protected GameActivity gameActivity;

    public GameTapControl(GameActivity gameActivity, float screenCenter) {
        this.gameActivity = gameActivity;
        this.screenCenter = screenCenter;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float position = e.getRawX();
        if (position >= screenCenter) {
            gameActivity.moveRightIfPossible();
        } else {
            gameActivity.moveLeftIfPossible();
        }
        return super.onSingleTapUp(e);
    }
}
