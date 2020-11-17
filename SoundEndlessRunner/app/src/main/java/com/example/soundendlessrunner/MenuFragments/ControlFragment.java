package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundendlessrunner.Enums.ControlType;
import com.example.soundendlessrunner.R;

public class ControlFragment extends MenuFragment {
    private ControlType chosenControl;
    private SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.cf_message);
        repeatText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    protected void nextMethod() {
        if(chosenControl.getValue() < ControlType.getMaxValue()){
            chosenControl = ControlType.findByValue((chosenControl.getValue() + 1));
        }
        else{
            chosenControl = ControlType.findByValue(ControlType.getMinValue());
        }
        tts.speak(chosenControl.getMsg(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void previousMethod() {
        if(chosenControl.getValue() > ControlType.getMinValue()){
            chosenControl = ControlType.findByValue((chosenControl.getValue() - 1));
        }
        else{
            chosenControl = ControlType.findByValue(ControlType.getMaxValue());
        }
        tts.speak(chosenControl.getMsg(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void changeWasFragmentChosen() {
        //TODO: Test
        wasFragmentChosen = !wasFragmentChosen;

        if(wasFragmentChosen){
            message = getString(R.string.cf_message2);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            int chosenControlInt = sharedPref.getInt(getString(R.string.settings_control), ControlType.SWIPES.getValue());
            chosenControl = ControlType.findByValue(chosenControlInt);
        }
        else{
            message = getString(R.string.cf_message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_control), chosenControl.getValue());
            editor.apply();
        }
    }
}