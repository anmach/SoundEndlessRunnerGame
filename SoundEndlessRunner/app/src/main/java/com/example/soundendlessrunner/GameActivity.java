package com.example.soundendlessrunner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.soundendlessrunner.Control.ControlWithStop.GameSensorControlWithStop;
import com.example.soundendlessrunner.Control.ControlWithStop.GameSwipeControlWithStop;
import com.example.soundendlessrunner.Control.ControlWithStop.GameTapControlWithStop;
import com.example.soundendlessrunner.Control.GameSensorControl;
import com.example.soundendlessrunner.Control.GameSwipeControl;
import com.example.soundendlessrunner.Control.GameTapControl;
import com.example.soundendlessrunner.Enums.ControlType;

import java.util.Locale;

public class GameActivity  extends AppCompatActivity implements SensorEventListener {
    protected TextToSpeech tts;
    protected GestureDetector detector;

    // Sensor
    protected GameSensorControl gameSensorControl;
    protected SensorManager sensorManager;
    protected Sensor sensor;

    protected int control;
    protected boolean withStop;

    protected GameManager gameManager;

    private Thread thread;
    protected Handler handler;

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

        setControls();

        handler = HandlerCompat.createAsync(Looper.getMainLooper());

        init(noOfTracks, noOfObjects);
    }

    protected void init(int noOfTracks, int noOfObjects){
        gameManager = new GameManager(noOfTracks, noOfObjects, this, handler);
        runGame();
    }

    protected void setControls(){
        if (control == ControlType.SWIPES.getValue()) {
            GameSwipeControl gameSwipeControl;
            if(withStop){
                gameSwipeControl = new GameSwipeControlWithStop(gameManager);
            }
            else{
                gameSwipeControl = new GameSwipeControl(gameManager);
            }
            detector = new GestureDetector(this, gameSwipeControl);
        }
        else if(control == ControlType.TAPS.getValue()){
            int screenCenter = this.getResources().getDisplayMetrics().widthPixels / 2;
            GameTapControl gameTapControl;
            if(withStop){
                gameTapControl = new GameTapControlWithStop(gameManager, screenCenter);;
            }
            else{
                gameTapControl = new GameTapControl(gameManager, screenCenter);
            }
            detector = new GestureDetector(this, gameTapControl);
        }
        else{
            if(withStop){
                gameSensorControl = new GameSensorControlWithStop(gameManager);
            }
            else{
                gameSensorControl = new GameSensorControl(gameManager);
            }
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        }
    }

    protected void runGame(){
        gameManager.play();

        if(!withStop){
            thread = new Thread(gameManager);
            thread.start();
        }
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
                        break;
                    case TextToSpeech.ERROR:
                        Log.d("tts", "TTS initialization failed");
                        break;
                }
            }
        });
    }

    public void endGame() {
        if(thread != null){
            thread.interrupt();
        }

        gameManager.stop();

        ttsSpeak(getString(R.string.game_over) + getString(R.string.add_point) + gameManager.getPoints());

        while(tts.isSpeaking()){}

        GameActivity.this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(thread != null){
            thread.interrupt();
        }

        tts.stop();
        gameManager.stop();

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
        if(event.getPointerCount() == 4 && event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("TOUCH", "TOUCH TYPE: " + event.getAction());
            endGame();
        }
        else if(detector != null){
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
    public void onAccuracyChanged(Sensor sensor, int i) {}

    protected void ttsSpeak(String text){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null ,null);
    }
}