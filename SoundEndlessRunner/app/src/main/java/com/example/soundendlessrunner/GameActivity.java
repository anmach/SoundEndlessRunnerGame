package com.example.soundendlessrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.soundendlessrunner.Control.GameSensorControl;
import com.example.soundendlessrunner.Control.GameSwipeControl;
import com.example.soundendlessrunner.Control.GameTapControl;
import com.example.soundendlessrunner.Enums.ControlType;
import com.example.soundendlessrunner.Enums.ObjectType;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    GameData gameData;
    private GestureDetector detector;
    private SoundManager soundManager;
    ScheduledThreadPoolExecutor exec;

    // Sensor
    private GameSensorControl gameSensorControl;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int noOfTracks = intent.getIntExtra(getString(R.string.settings_no_of_tracks), 3);
        int noOfObjects = intent.getIntExtra(getString(R.string.settings_no_of_objects), 3);
        int control = intent.getIntExtra(getString(R.string.settings_control), 0);

        gameData = new GameData(noOfTracks, noOfObjects);
        soundManager = new SoundManager(this, gameData.getTimeBetweenObjects());

        if (control == ControlType.SWIPES.getValue()) {
            GameSwipeControl gameSwipeControl = new GameSwipeControl(gameData, this);
            detector = new GestureDetector(this, gameSwipeControl);
        }
        else if(control == ControlType.TAPS.getValue()) {
            //TODO: Get screen center
            GameTapControl gameTapControl = new GameTapControl(gameData, this, 500);
            detector = new GestureDetector(this, gameTapControl);
        }
        else{
            gameSensorControl = new GameSensorControl(gameData, this);
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        }

        runGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getPointerCount() == 4){
            startMenuActivity();
        }

        if(detector != null){
            detector.onTouchEvent(event);
        }

        return true;
    }

    public void runGame(){
        gameData.drawObject();
        playSound();

        exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {
        public void run() {
            continueGame();
        }
        }, gameData.getTimeBetweenObjects(), gameData.getTimeBetweenObjects(), TimeUnit.MILLISECONDS);
    }

    public void continueGame(){
        if(gameData.didWeDied()){
            this.startMenuActivity();
        }

        gameData.drawObject();
        soundManager.stopPlayingSound();
        playSound();
    }

    private void playSound(){
        ObjectType objectType = gameData.getObjectType();
        if(objectType == ObjectType.Life){
            soundManager.playHeartSound();
        }
        else if(objectType == ObjectType.Point) {
            soundManager.playPointSound();
        }
        else{
            soundManager.playObstacleSound();
        }

        adjustVolume();
    }

    public void adjustVolume(){
        int difference = gameData.getDifferenceBetweenPlayerAndObjectTrack();
        if(difference == 0){
            soundManager.setVolume(1,1f);
        }
        else if(difference > 0){
            float volume = 1 - (0.1f * difference);
            soundManager.setVolume(volume, 0);
        }
        else{
            difference -= 2*difference;
            float volume = 1 - (0.1f * difference);
            soundManager.setVolume(0, volume);
        }
    }

    public void startMenuActivity() {
        exec.shutdownNow();
        soundManager.stopPlayingSound();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO: Manage it somehow
        exec.shutdownNow();
        soundManager.stopPlayingSound();

        if(sensorManager != null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager != null){
            sensorManager.registerListener(this, sensor, Sensor.TYPE_GAME_ROTATION_VECTOR);
        }
    }

    //TODO: Delete this after graphic delete
    public void changeNoOfTrackTextAndSound() {
        int number = gameData.getNoOfPlayerTrack();
        String no = Integer.toString(number);
        TextView textView = (TextView) findViewById(R.id.textViewNoOfTrack);
        textView.setText(no);

        adjustVolume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(gameSensorControl != null){
            gameSensorControl.manageEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}