package com.example.soundendlessrunner;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SoundManager {
    Thread fadeInThread;
    Timer timer;
    TimerTask timerTask;

    final private float VOLUME_MOVING_STEP = 0.1f;
    final private float MIN_VOLUME = 0.05f;
    private float leftVolume = 0.1f;
    private float rightVolume = 0.1f;

    private MediaPlayer currentlyPlaying;

    private long soundTime;
    private Context context;

    public SoundManager(Context context, long soundTime) {
        this.soundTime = soundTime;
        this.context = context;
    }

    public void playObstacleSound() {
        currentlyPlaying = MediaPlayer.create(context, R.raw.sound_obstacle);
        playCurrentSound();
    }

    public void playHeartSound() {
        currentlyPlaying = MediaPlayer.create(context, R.raw.sound_heart);
        playCurrentSound();
    }

    public void playPointSound() {
        currentlyPlaying = MediaPlayer.create(context, R.raw.sound_money);
        playCurrentSound();
    }

    private void playCurrentSound(){
        currentlyPlaying.setLooping(true);
        currentlyPlaying.setVolume(leftVolume, rightVolume);
        currentlyPlaying.start();
        startFadeIn();
    }

    public void stopPlayingSound() {
        if(currentlyPlaying != null) {
            timer.cancel();
            timer.purge();
            currentlyPlaying.release();
        }
    }

    private void startFadeIn(){
        Log.d("SOUND", "START FADE IN");
        final float max_volume = 1.2f;
        final long timeBetweenSteps = 250;
        final int numberOfSteps = (int)soundTime/(int)timeBetweenSteps;
        final float deltaVolume = (max_volume - MIN_VOLUME) / (float)numberOfSteps;

        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                fadeInStep(deltaVolume);
                if(leftVolume >= max_volume || rightVolume >= max_volume){
                    timer.cancel();
                    timer.purge();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask,timeBetweenSteps,timeBetweenSteps);
    }

    private void fadeInStep(float deltaVolume){
        Log.d("SOUND", "FADE STEP");
        if(rightVolume != 0){
            rightVolume += deltaVolume;
        }
        if(leftVolume != 0){
            leftVolume += deltaVolume;
        }
        currentlyPlaying.setVolume(leftVolume, rightVolume);
    }

    public void adjustVolume(int difference){
        if(difference == 0){
            if(leftVolume != 0){
                leftVolume = leftVolume + VOLUME_MOVING_STEP;
                rightVolume = leftVolume;
            }
            else{
                rightVolume = rightVolume + VOLUME_MOVING_STEP;
                leftVolume = rightVolume;
            }
        }
        else if(difference > 0){
            leftVolume = leftVolume - VOLUME_MOVING_STEP;
            if(leftVolume <= 0){
                leftVolume = MIN_VOLUME;
            }
            rightVolume = 0;
        }
        else{
            rightVolume = rightVolume - VOLUME_MOVING_STEP;
            if(rightVolume <= MIN_VOLUME){
                rightVolume = MIN_VOLUME;
            }
            leftVolume = 0;
        }
        setVolume(leftVolume, rightVolume);
    }

    private void setVolume(float leftVolume, float rightVolume) {
        currentlyPlaying.setVolume(leftVolume, rightVolume);
    }

    public void setStartingVolume(int difference){
        if(difference == 0){
            leftVolume = MIN_VOLUME;
            rightVolume = MIN_VOLUME;
        }
        else if(difference > 0){
            leftVolume = MIN_VOLUME;
            rightVolume = 0;
        }
        else{
            rightVolume = MIN_VOLUME;
            leftVolume = 0;
        }
    }
}
