package com.example.soundendlessrunner.MenuFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soundendlessrunner.R;

public class CreditsFragment extends MenuFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getString(R.string.crf_message);
        sayMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    public void changeWasFragmentChosen() {
        super.changeWasFragmentChosen();

        if(wasFragmentChosen){
            message = getString(R.string.crf_message2);
        }
        else{
            message = getString(R.string.crf_message);
        }

        sayMessage();
    }
}