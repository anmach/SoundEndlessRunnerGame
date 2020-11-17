package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.soundendlessrunner.MenuActivity;
import com.example.soundendlessrunner.R;

public class NoOfTracksFragment extends MenuFragment {
    private int noOfTracks;
    private SharedPreferences sharedPref;

    final int MIN_TRACKS = 3;
    final int DEFAULT_TRACKS_NUMBER = 3;
    final int MAX_TRACKS = 7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.notf_message);
        repeatText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_of_tracks, container, false);
    }

    @Override
    protected void nextMethod() {
        if(noOfTracks < MAX_TRACKS){
            noOfTracks ++;
            message = getString(R.string.notf_message2) + "Current number of tracks: " + noOfTracks;
            tts.speak("" + noOfTracks, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(getString(R.string.notf_max), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void previousMethod() {
        if(noOfTracks > MIN_TRACKS){
            noOfTracks --;
            message = getString(R.string.notf_message2) + "Current number of tracks: " + noOfTracks;
            tts.speak("" + noOfTracks, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(getString(R.string.notf_min), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void changeWasFragmentChosen() {
        wasFragmentChosen = !wasFragmentChosen;

        if (wasFragmentChosen) {
            message = getString(R.string.notf_message2) + "Current number of tracks: " + noOfTracks;
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            noOfTracks = sharedPref.getInt(getString(R.string.settings_no_of_tracks), DEFAULT_TRACKS_NUMBER);
        } else {
            message = getString(R.string.notf_message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_no_of_tracks), noOfTracks);
            editor.apply();
        }
    }
}