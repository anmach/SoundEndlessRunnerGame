package com.example.soundendlessrunner.Control;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.GameManager;

public class GameSwipeControl extends GestureDetector.SimpleOnGestureListener {
    protected static final int SWIPE_MIN_DISTANCE = 120;
    protected static final int SWIPE_MAX_OFF_PATH = 250;
    protected static final int SWIPE_THRESHOLD_VELOCITY = 200;

    protected GameManager gameManager;

    public GameSwipeControl(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                gameManager.moveRightIfPossible();
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                gameManager.moveLeftIfPossible();
            }
        } catch (Exception e) {
        }

        return true;
    }
}
