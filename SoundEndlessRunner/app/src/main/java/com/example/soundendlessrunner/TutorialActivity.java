package com.example.soundendlessrunner;

import android.os.Build;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.example.soundendlessrunner.Enums.ControlType;
import com.example.soundendlessrunner.MenuFragments.TutorialManager;

import java.util.HashMap;
import java.util.Locale;


public class TutorialActivity extends GameActivity {
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
    protected void init(int noOfTracks, int noOfObjects){
        gameManager = new TutorialManager(noOfTracks, noOfObjects, this, handler, withStop);
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

                        setTtsListener();
                        speakIntro();
                        break;
                    case TextToSpeech.ERROR:
                        Log.d("tts", "TTS initialization failed");
                        break;
                }
            }
        });
    }

    private void setTtsListener(){
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onDone(String utteranceId) {
                Log.i("tutorial", "tts done");
                ((TutorialManager)gameManager).continueTutorial();
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
    public void ttsSpeak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null, utteranceId);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParams);
        }
        /*
        while(tts.isSpeaking()){}
        ((TutorialManager)gameManager).continueTutorial();
         */
    }

    private void speakIntro(){
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

    public void speakPart1(){
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

    public void speakPart2(String text){
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

    public void speakPart3(){
        String text = getString(R.string.tut_part2_end);
        text += getString(R.string.tut_part3);
        ttsSpeak(text);
    }

    public void speakEnding(){
        String text = getString(R.string.tut_part3_end);
        ttsSpeak(text);
    }

    @Override
    public void endGame(){
        TutorialActivity.this.finish();
    }

    public boolean isTtsSpeaking(){
        return tts.isSpeaking();
    }
}