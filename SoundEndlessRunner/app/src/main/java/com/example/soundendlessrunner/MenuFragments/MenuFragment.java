package com.example.soundendlessrunner.MenuFragments;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.soundendlessrunner.MenuActivity;
import com.example.soundendlessrunner.R;


public class MenuFragment extends Fragment {
    protected TextToSpeech tts;
    protected String message = "";

    //TODO: Change variable name...
    protected boolean wasFragmentChosen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = ((MenuActivity) getActivity()).getTTS();
        ((MenuActivity) getActivity()).setFragment(this);
    }

    public void next() {
        if (wasFragmentChosen) {
            nextMethod();
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_next);
        }
    }

    public void previous() {
        if (wasFragmentChosen) {
            previousMethod();
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_previous);
        }
    }

    //TODO: Change method name...
    protected void nextMethod() {}

    //TODO: Change method name...
    protected void previousMethod() {}

    public void repeatText() {
        tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    //TODO: Change method name...
    public void changeWasFragmentChosen() {
        wasFragmentChosen = !wasFragmentChosen;
    }
}
