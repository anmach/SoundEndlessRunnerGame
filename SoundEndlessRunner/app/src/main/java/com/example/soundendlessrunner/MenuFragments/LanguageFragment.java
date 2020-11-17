package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.example.soundendlessrunner.R;

public class LanguageFragment extends MenuFragment {
    private Language chosenLanguage;
    private SharedPreferences sharedPref;

    private enum Language {
        POLISH("polski"),
        ENGLISH("english");

        String msg;
        int value;

        private Language(String msg) {
            this.msg = msg;
        }

        String getMsg() {
            return msg;
        }
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.lf_message);
        repeatText();
    }

    @Override
    protected void nextMethod() {
        if(chosenLanguage == Language.ENGLISH){
            chosenLanguage = Language.POLISH;
        }
        else{
            chosenLanguage = Language.ENGLISH;
        }
        tts.speak(chosenLanguage.getMsg(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void previousMethod() {
        if(chosenLanguage == Language.ENGLISH){
            chosenLanguage = Language.POLISH;
        }
        else{
            chosenLanguage = Language.ENGLISH;
        }
        tts.speak(chosenLanguage.getMsg(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void changeWasFragmentChosen() {
        wasFragmentChosen = !wasFragmentChosen;

        if (wasFragmentChosen) {
            message = getString(R.string.lf_message2);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            //TODO: Refactor this enum thingy
            if(sharedPref.getString(getString(R.string.settings_language), "english") == Language.POLISH.getMsg()){
                chosenLanguage = Language.POLISH;
            }
            else{
                chosenLanguage = Language.ENGLISH;
            }
        } else {
            message = getString(R.string.lf_message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.settings_language), chosenLanguage.getMsg());
            editor.apply();
        }
    }

}