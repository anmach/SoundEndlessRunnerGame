package com.example.soundendlessrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.soundendlessrunner.Control.ControlWithStop.GameSensorControlWithStop;
import com.example.soundendlessrunner.Control.ControlWithStop.GameSwipeControlWithStop;
import com.example.soundendlessrunner.Control.ControlWithStop.GameTapControlWithStop;
import com.example.soundendlessrunner.Control.GameSensorControl;
import com.example.soundendlessrunner.Control.GameSwipeControl;
import com.example.soundendlessrunner.Control.GameTapControl;
import com.example.soundendlessrunner.Enums.ControlType;
import com.example.soundendlessrunner.Enums.ObjectType;

import java.util.Locale;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameActivity  extends AppCompatActivity implements SensorEventListener {
    GameData gameData;
    protected TextToSpeech tts;
    protected GestureDetector detector;
    protected SoundManager soundManager;
    ScheduledThreadPoolExecutor exec;

    // Sensor
    protected GameSensorControl gameSensorControl;
    protected SensorManager sensorManager;
    protected Sensor sensor;

    protected boolean withStop;
    protected int control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initTTS();

        Intent intent = getIntent();
        int noOfTracks = intent.getIntExtra(getString(R.string.settings_no_of_tracks), 3);
        int noOfObjects = intent.getIntExtra(getString(R.string.settings_no_of_objects), 3);
        control = intent.getIntExtra(getString(R.string.settings_control), 0);
        withStop = intent.getBooleanExtra(getString(R.string.settings_stop_enable), false);

        gameData = new GameData(noOfTracks, noOfObjects);
        soundManager = new SoundManager(this, gameData.getTimeBetweenObjects());

        if (control == ControlType.SWIPES.getValue()) {
            GameSwipeControl gameSwipeControl;
            if(withStop){
                gameSwipeControl = new GameSwipeControlWithStop(this);
            }
            else{
                gameSwipeControl = new GameSwipeControl(this);
            }
            detector = new GestureDetector(this, gameSwipeControl);
        }
        else if(control == ControlType.TAPS.getValue()){
            //TODO: Get screen center
            GameTapControl gameTapControl;
            if(withStop){
                gameTapControl = new GameTapControlWithStop(this, 500);;
            }
            else{
                gameTapControl = new GameTapControl(this, 500);
            }
            detector = new GestureDetector(this, gameTapControl);
        }
        else{
            if(withStop){
                gameSensorControl = new GameSensorControlWithStop(this);
            }
            else{
                gameSensorControl = new GameSensorControl(this);
            }
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        }

        runGame();
    }

    public void initTTS(){
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                switch (status) {
                    case TextToSpeech.SUCCESS:
                        if(!Locale.getDefault().toLanguageTag().equals("pl-PL")){
                            Log.d("tts", Locale.getDefault().toLanguageTag());
                            tts.setLanguage(Locale.UK);
                        }

                        Log.d("tts", "TTS initialization succes");

                        break;
                    case TextToSpeech.ERROR:
                        Log.d("tts", "TTS initialization failed");
                        break;
                }
            }
        });
    }

    public void runGame(){
        if(withStop){
            gameData.drawObject();
            playSound();
        }
        else{
            gameData.drawObject();
            playSound();

            exec = new ScheduledThreadPoolExecutor(1);
            exec.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    continueGame();
                }
            }, gameData.getTimeBetweenObjects(), gameData.getTimeBetweenObjects(), TimeUnit.MILLISECONDS);
        }
    }

    public void continueGame(){
        if(gameData.didWeHit()){
            ObjectType type = gameData.getObjectType();
            if(type == ObjectType.Point){
                ttsSpeak(getString(R.string.add_point) + gameData.getPoints());
            }
            else{
                if(gameData.didWeDied()){
                    ttsSpeak(getString(R.string.game_over) + getString(R.string.add_point) + gameData.getPoints());
                    while(tts.isSpeaking());
                    this.startMenuActivity();
                }
                else{
                    ttsSpeak(getString(R.string.add_life) + gameData.getLives());
                }
            }
        }

        gameData.drawObject();
        soundManager.stopPlayingSound();
        playSound();
    }

    protected void playSound(){
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

    public void changeNoOfTrackTextAndSound() {
        int number = gameData.getNoOfPlayerTrack();
        String no = Integer.toString(number);
        TextView textView = (TextView) findViewById(R.id.textViewNoOfTrack);
        textView.setText(no);

        adjustVolume();
    }

    public void moveLeftIfPossible(){
        gameData.moveLeftIfPossible();
        changeNoOfTrackTextAndSound();
    }

    public void moveRightIfPossible(){
        gameData.moveRightIfPossible();
        changeNoOfTrackTextAndSound();
    }

    public void startMenuActivity() {
        tts.stop();
        if(exec != null){
            exec.shutdownNow();
        }
        soundManager.stopPlayingSound();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
        if(exec != null){
            exec.shutdownNow();
        }
        //TODO Pause (not stop) sound
        soundManager.stopPlayingSound();

        if(sensorManager != null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO Resume sound
        if(sensorManager != null){
            sensorManager.registerListener(this, sensor, Sensor.TYPE_GAME_ROTATION_VECTOR);
        }
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(gameSensorControl != null){
            gameSensorControl.manageEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void ttsSpeak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null ,null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}