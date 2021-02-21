package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        sayMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_of_tracks, container, false);
    }

    @Override
    protected void nextOption() {
        if(noOfTracks < MAX_TRACKS){
            noOfTracks ++;
            message = getString(R.string.notf_message2) + noOfTracks;
            useTTS("" + noOfTracks);
        }
        else{
            useTTS(getString(R.string.notf_max));
        }
    }

    @Override
    protected void previousOption() {
        if(noOfTracks > MIN_TRACKS){
            noOfTracks --;
            message = getString(R.string.notf_message2) + noOfTracks;
            useTTS("" + noOfTracks);
        }
        else{
            useTTS(getString(R.string.notf_min));
        }
    }

    @Override
    public void changeWasFragmentChosen() {
        super.changeWasFragmentChosen();

        if (wasFragmentChosen) {
            message = getString(R.string.notf_message2) + noOfTracks;
            sayMessage();
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            noOfTracks = sharedPref.getInt(getString(R.string.settings_no_of_tracks), DEFAULT_TRACKS_NUMBER);
        } else {
            message = getString(R.string.notf_message);
            sayMessage();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_no_of_tracks), noOfTracks);
            editor.apply();
        }
    }
}