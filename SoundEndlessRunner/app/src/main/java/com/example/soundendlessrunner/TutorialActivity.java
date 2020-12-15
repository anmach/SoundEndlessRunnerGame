package com.example.soundendlessrunner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.example.soundendlessrunner.Enums.ControlType;

import java.util.HashMap;
import java.util.Locale;


public class TutorialActivity extends GameActivity {
    private int tutorialPart = 0;
    private HashMap<String, String> ttsParams;
    private String utteranceId = "justId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ttsParams = new HashMap<String, String>();
        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
    }

    @Override
    public void initTTS() {
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

                        part0();
                        break;
                    case TextToSpeech.ERROR:
                        Log.d("tts", "TTS initialization failed");
                        break;
                }
            }
        });
    }

    @Override
    public void runGame() {}

    private void setTTSListener(){
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onDone(String utteranceId) {
                Log.i("tutorial", "tts done");
                continueTutorial();
            }

            @Override
            public void onError(String utteranceId) {
                Log.i("tutorial", "tts error");
            }

            @Override
            public void onStart(String utteranceId) {
                Log.i("tutorial", "tts start");
            }
        });
    }

    @Override
    protected void ttsSpeak(String text){
        setTTSListener();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null ,utteranceId);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams);
        }
    }

    private void part0(){
        String text = getString(R.string.tut_part0);
        if (control == ControlType.SWIPES.getValue()) {
            text += getString(R.string.tut_part0_swipe);
        }
        else if(control == ControlType.TAPS.getValue()){
            text += getString(R.string.tut_part0_tap);
        }
        else{
            text += getString(R.string.tut_part0_sensor);
        }
        ttsSpeak(text);
    }

    private void part1(){
        String text = getString(R.string.tut_part0_end);
        text += getString(R.string.tut_part1);
        if (control == ControlType.SWIPES.getValue()) {
            text += getString(R.string.tut_part1_swipe);
        }
        else if(control == ControlType.TAPS.getValue()){
            text += getString(R.string.tut_part1_tap);
        }
        else{
            text += getString(R.string.tut_part1_sensor);
        }
        ttsSpeak(text);
    }

    private void part2(String text){
        text += getString(R.string.tut_part2);
        if (control == ControlType.SWIPES.getValue()) {
            text += getString(R.string.tut_part2_swipe);
        }
        else if(control == ControlType.TAPS.getValue()){
            text += getString(R.string.tut_part2_tap);
        }
        else{
            text += getString(R.string.tut_part2_sensor);
        }
        ttsSpeak(text);
    }

    private void part3(){
        String text = getString(R.string.tut_part2_end);
        text += getString(R.string.tut_part3);
        ttsSpeak(text);
    }

    private void part4(){
        String text = getString(R.string.tut_part3_end);
        ttsSpeak(text);
    }

    @Override
    public void continueGame() {
        if(tutorialPart == 1){
            part2(" ");
        }
    }

    private void continueTutorial(){
        switch (tutorialPart){
            case 0:
                soundManager.playObstacleSound();
                break;
            case 1:
                break;
            case 2:
                soundManager.playPointSound();
                adjustVolume();
                break;
            case 3:
                soundManager.playHeartSound();
                adjustVolume();
                break;
            default:
                startMenuActivity();
        }
    }

    @Override
    public void moveLeftIfPossible() {
        if(tutorialPart == 2){
            tutorialPart++;
            soundManager.stopPlayingSound();
            part3();
        }
        else if(tutorialPart == 3){
            gameData.moveLeftIfPossible();
            adjustVolume();
        }
    }

    @Override
    public void moveRightIfPossible() {
        if(tutorialPart == 0){
            soundManager.stopPlayingSound();
            tutorialPart++;
            if(withStop){
                part1();
            }
            else{
                tutorialPart++;
                part2(getString(R.string.tut_part0_end));
            }
        }
        else if(tutorialPart == 3){
            gameData.moveRightIfPossible();
            if(gameData.getNoOfPlayerTrack() == 3){
                soundManager.stopPlayingSound();
                tutorialPart++;
                part4();
            }
            else {
                adjustVolume();
            }
        }
    }

    public void adjustVolume(){
        int difference = 0;

        switch (tutorialPart){
            case 2:
                difference = 1;
                break;
            case 3:
                difference = gameData.getNoOfPlayerTrack() - 3;
                break;
        }

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
}