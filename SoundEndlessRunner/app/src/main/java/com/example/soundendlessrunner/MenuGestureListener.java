package com.example.soundendlessrunner;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.soundendlessrunner.MenuFragments.MenuFragment;

public class MenuGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private CharSequence text = "";
    private int duration = Toast.LENGTH_LONG;
    private Context context;
    private MenuFragment menuFragment = null;

    public MenuGestureListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (menuFragment != null) {
            menuFragment.sayMessage();
        }

        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (menuFragment != null) {
            menuFragment.changeWasFragmentChosen();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        text = "long press";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
                if (menuFragment != null) {
                    menuFragment.next();
                }
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (menuFragment != null) {
                    menuFragment.previous();
                }
            }
        } catch (Exception e) {
        }

        return true;
    }

    public void setMenuFragment(MenuFragment menuFragment) {
        this.menuFragment = menuFragment;
    }
}
