package com.sim.localecomapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

/**
 * Fragment representing the login screen for Shrine.
 */
public class Splash extends Fragment {

    private MaterialButton enterbutton;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.splash_fragment, container, false);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((NavigationHost) getActivity()).navigateTo(new EcomProductGrid(), false); // Navigate to the next Fragment
            }
        },1500);

        enterbutton=view.findViewById(R.id.enter_button);
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new EcomProductGrid(), false); // Navigate to the next Fragment
            }
        });
        // Snippet from "Navigate to the next Fragment" section goes here.

        return view;
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
}
