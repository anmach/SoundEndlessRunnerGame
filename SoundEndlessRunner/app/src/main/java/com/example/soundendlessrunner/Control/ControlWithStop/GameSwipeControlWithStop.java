package com.example.soundendlessrunner.Control.ControlWithStop;

import android.view.MotionEvent;

import com.example.soundendlessrunner.Control.GameSwipeControl;
import com.example.soundendlessrunner.GameManager;

public class GameSwipeControlWithStop extends GameSwipeControl {
    public GameSwipeControlWithStop(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                gameManager.continueGame();
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
