package com.example.soundendlessrunner.Control.ControlWithStop;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.GameActivityWithStop;
import com.example.soundendlessrunner.GameData;

public class GameSwipeControlWithStop extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GameData gameData;
    private GameActivityWithStop gameActivity;

    public GameSwipeControlWithStop(GameData gameData, GameActivityWithStop gameActivity) {
        this.gameData = gameData;
        this.gameActivity = gameActivity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                gameActivity.continueGame();
                return false;
            }
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                gameData.moveRightIfPossible();
                gameActivity.changeNoOfTrackTextAndSound();
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                gameData.moveLeftIfPossible();
                gameActivity.changeNoOfTrackTextAndSound();
            }
        } catch (Exception e) {
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        super.onLongPress(event);

        int action = event.getActionMasked();

        Log.d("touch", "Long press counted pointers: " + event.getPointerCount());
    }
}
