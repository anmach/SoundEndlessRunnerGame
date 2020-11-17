package com.example.soundendlessrunner.MenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//TODO: Repair text to speech in init fragment

import com.example.soundendlessrunner.R;

public class InitFragment extends MenuFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.if_message);
        repeatText();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_init, container, false);
    }

    @Override
    public void changeWasFragmentChosen() {}
}