package com.example.soundendlessrunner.MenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.example.soundendlessrunner.R;

public class EnableStopFragment extends MenuFragment {
    private SharedPreferences sharedPref;
    boolean stopEnabled;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        stopEnabled = sharedPref.getBoolean(getString(R.string.settings_stop_enable), false);

        if (stopEnabled) {
            message = getString(R.string.esf_message_enabled);
        } else {
            message = getString(R.string.esf_message_disabled);
        }
        repeatText();
    }

    @Override
    public void changeWasFragmentChosen() {
        stopEnabled = !stopEnabled;

        if (stopEnabled) {
            message = getString(R.string.esf_message_enabled);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.settings_stop_enable), stopEnabled);
            editor.apply();
        } else {
            message = getString(R.string.esf_message_disabled);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.settings_stop_enable), stopEnabled);
            editor.apply();
        }
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }

}
