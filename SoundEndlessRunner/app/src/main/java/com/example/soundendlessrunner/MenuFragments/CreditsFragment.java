package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundendlessrunner.Enums.ControlType;
import com.example.soundendlessrunner.MenuFragments.MenuFragment;
import com.example.soundendlessrunner.R;

public class CreditsFragment extends MenuFragment {
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
    public void changeWasFragmentChosen() {
        wasFragmentChosen = !wasFragmentChosen;

        if(wasFragmentChosen){
            message = getString(R.string.crf_message2);
        }
        else{
            message = getString(R.string.crf_message);
        }

        repeatText();
    }
}