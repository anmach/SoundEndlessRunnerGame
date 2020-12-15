package com.example.soundendlessrunner.MenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundendlessrunner.MenuActivity;
import com.example.soundendlessrunner.R;

public class StartGameFragment extends MenuFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.sgf_message);
        repeatText();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_game, container, false);
    }

    @Override
    public void changeWasFragmentChosen() {
        ((MenuActivity)getActivity()).startGameActivity();
    }
}