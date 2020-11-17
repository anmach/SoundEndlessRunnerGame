package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundendlessrunner.R;

public class VolumeFragment extends MenuFragment {
    private int volume;
    private SharedPreferences sharedPref;

    final int MIN_VOLUME = 1;
    final int DEFAULT_VOLUME = 5;
    final int MAX_VOLUME = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.vf_message);
        repeatText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volume, container, false);
    }

    @Override
    protected void nextMethod() {
        if(volume < MAX_VOLUME){
            volume ++;
            tts.speak("" + volume, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(getString(R.string.vf_max), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void previousMethod() {
        if(volume > MIN_VOLUME){
            volume--;
            tts.speak("" + volume, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(getString(R.string.vf_min), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void changeWasFragmentChosen() {
        //TODO: Test
        wasFragmentChosen = !wasFragmentChosen;

        if(wasFragmentChosen){
            message = getString(R.string.vf_message2);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            volume = sharedPref.getInt(getString(R.string.settings_volume), DEFAULT_VOLUME);
        }
        else{
            message = getString(R.string.vf_message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_volume), volume);
            editor.apply();
        }
    }
}