package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundendlessrunner.R;

public class FrequencyFragment extends MenuFragment {
    private int noOfObstacles;
    private SharedPreferences sharedPref;

    final int MIN_OBSTACLES = 1;
    final int DEFAULT_FREQUENCY = 6;
    final int MAX_OBSTACLES = 30;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.ff_message);
        repeatText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frequency, container, false);
    }

    @Override
    protected void nextMethod() {
        if(noOfObstacles < MAX_OBSTACLES){
            noOfObstacles++;
            tts.speak("" + noOfObstacles, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(getString(R.string.ff_max), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void previousMethod() {
        if(noOfObstacles > MIN_OBSTACLES){
            noOfObstacles--;
            tts.speak("" + noOfObstacles, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(getString(R.string.ff_min), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void changeWasFragmentChosen() {
        wasFragmentChosen = !wasFragmentChosen;

        if(wasFragmentChosen){
            message = getString(R.string.ff_message2);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            noOfObstacles = sharedPref.getInt(getString(R.string.settings_no_of_objects), DEFAULT_FREQUENCY);
        }
        else{
            message = getString(R.string.ff_message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_no_of_objects), noOfObstacles);
            editor.apply();
        }
    }
}