package com.example.soundendlessrunner.Control;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.GameManager;

public class GameTapControl extends GestureDetector.SimpleOnGestureListener {
    protected float screenCenter;
    protected GameManager gameManager;

    public GameTapControl(GameManager gameManager, float screenCenter) {
        this.gameManager = gameManager;
        this.screenCenter = screenCenter;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float position = e.getRawX();
        if (position >= screenCenter) {
            gameManager.moveRightIfPossible();
        } else {
            gameManager.moveLeftIfPossible();
        }
        return super.onSingleTapUp(e);
    }
}
