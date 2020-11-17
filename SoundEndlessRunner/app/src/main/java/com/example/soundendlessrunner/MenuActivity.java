package com.example.soundendlessrunner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.soundendlessrunner.MenuFragments.MenuFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Locale;


public class MenuActivity extends AppCompatActivity {
    private GestureDetector detector;
    private MenuGestureListener menuGestureListener;
    private TextToSpeech tts;
    private MenuFragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuGestureListener = new MenuGestureListener(getApplicationContext());
        detector = new GestureDetector(this, menuGestureListener);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                switch (status) {
                    case TextToSpeech.SUCCESS:
                        if(!Locale.getDefault().toLanguageTag().equals("pl-PL")){
                            Log.d("tts", Locale.getDefault().toLanguageTag());
                            tts.setLanguage(Locale.UK);
                        }

                        if(currentFragment!= null){
                            currentFragment.repeatText();
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

    public TextToSpeech getTTS() {
        return tts;
    }

    public void setFragment(MenuFragment fragment) {
        currentFragment = fragment;
        menuGestureListener.setMenuFragment(currentFragment);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    public void startGameActivity(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(getString(R.string.ma_exit_game),TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(getString(R.string.ma_exit_game), TextToSpeech.QUEUE_FLUSH, null);
        }


        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int noOfTracks = sharedPref.getInt(getString(R.string.settings_no_of_tracks), 3);
        int noOfObjects = sharedPref.getInt(getString(R.string.settings_no_of_objects), 3);
        int control = sharedPref.getInt(getString(R.string.settings_control), 0);
        boolean stopEnabled = sharedPref.getBoolean(getString(R.string.settings_stop_enable), false);

        Intent intent;
        if(stopEnabled){
            intent = new Intent(this, GameActivityWithStop.class);
        }
        else{
            intent = new Intent(this, GameActivity.class);
        }

        intent.putExtra(getString(R.string.settings_no_of_tracks), noOfTracks);
        intent.putExtra(getString(R.string.settings_no_of_objects), noOfObjects);
        intent.putExtra(getString(R.string.settings_control), control);

        while(tts.isSpeaking());
        
        startActivity(intent);
    }
}