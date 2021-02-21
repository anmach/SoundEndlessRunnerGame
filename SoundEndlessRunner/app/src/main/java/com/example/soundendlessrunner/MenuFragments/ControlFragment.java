package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
        sayMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false);
    }

    @Override
    protected void nextOption() {
        if(chosenControl.getValue() < ControlType.getMaxValue()){
            chosenControl = ControlType.findByValue((chosenControl.getValue() + 1));
        }
        else{
            chosenControl = ControlType.findByValue(ControlType.getMinValue());
        }
        useTTS(chosenControl.getMsg());
    }

    @Override
    protected void previousOption() {
        if(chosenControl.getValue() > ControlType.getMinValue()){
            chosenControl = ControlType.findByValue((chosenControl.getValue() - 1));
        }
        else{
            chosenControl = ControlType.findByValue(ControlType.getMaxValue());
        }
        useTTS(chosenControl.getMsg());
    }

    @Override
    public void changeWasFragmentChosen() {
        super.changeWasFragmentChosen();

        if(wasFragmentChosen){
            message = getString(R.string.cf_message2);
            sayMessage();
            sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            int chosenControlInt = sharedPref.getInt(getString(R.string.settings_control), ControlType.SWIPES.getValue());
            chosenControl = ControlType.findByValue(chosenControlInt);
        }
        else{
            message = getString(R.string.cf_message);
            sayMessage();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_control), chosenControl.getValue());
            editor.apply();
        }
    }
}