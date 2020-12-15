package com.example.soundendlessrunner;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.VolumeShaper;

public class SoundManager {
    private final long soundTime; //in ms

    private VolumeShaper volumeShaper;

    private MediaPlayer mediaPlayerObstacle;
    private MediaPlayer mediaPlayerHeart;
    private MediaPlayer mediaPlayerPoint;
    private MediaPlayer currentlyPlaying;
    VolumeShaper.Configuration volumeConfig;

    public SoundManager(Context context, long soundTime) {
        this.soundTime = soundTime;

        volumeConfig = new VolumeShaper.Configuration.Builder()
                .setDuration(soundTime)
                .setCurve(new float[]{0.f, 1.f}, new float[]{0.f, 1.f})
                .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_LINEAR)
                .build();

        mediaPlayerObstacle = MediaPlayer.create(context, R.raw.sound_obstacle);
        mediaPlayerObstacle.setLooping(true);

        mediaPlayerHeart = MediaPlayer.create(context, R.raw.sound_heart);
        mediaPlayerHeart.setLooping(true);

        mediaPlayerPoint = MediaPlayer.create(context, R.raw.sound_money);
        mediaPlayerPoint.setLooping(true);
    }

    public void playObstacleSound() {
        currentlyPlaying = mediaPlayerObstacle;
        playCurrentSound();
    }

    public void playHeartSound() {
        currentlyPlaying = mediaPlayerHeart;
        playCurrentSound();
    }

    public void playPointSound() {
        currentlyPlaying = mediaPlayerPoint;
        playCurrentSound();
    }

    public void playCurrentSound(){
        volumeShaper = currentlyPlaying.createVolumeShaper(volumeConfig);
        currentlyPlaying.start();
        volumeShaper.apply(VolumeShaper.Operation.PLAY);
    }

    public void stopPlayingSound() {
        if(currentlyPlaying != null) {
            currentlyPlaying.pause();
        }
    }

    public void setVolume(float leftVolume, float rightVolume) {
        currentlyPlaying.setVolume(leftVolume, rightVolume);
    }
}
