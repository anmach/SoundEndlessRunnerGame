package com.example.soundendlessrunner.MenuFragments;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.soundendlessrunner.MenuActivity;
import com.example.soundendlessrunner.R;


public class MenuFragment extends Fragment {
    private TextToSpeech tts;
    protected String message = "";

    //Showing if user chose to interact with fragment
    protected boolean wasFragmentChosen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = ((MenuActivity) getActivity()).getTTS();
        ((MenuActivity) getActivity()).setFragment(this);
    }

    public void next() {
        if (wasFragmentChosen) {
            nextOption();
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_next);
        }
    }

    public void previous() {
        if (wasFragmentChosen) {
            previousOption();
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_previous);
        }
    }

    protected void nextOption() {}

    protected void previousOption() {}

    public void sayMessage() {
        tts.speak(message, TextToSpeech.QUEUE_FLUSH,null,null);
    }

    public void useTTS(String msg){
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH,null,null);
    }

    public void changeWasFragmentChosen() {
        wasFragmentChosen = !wasFragmentChosen;
    }
}
