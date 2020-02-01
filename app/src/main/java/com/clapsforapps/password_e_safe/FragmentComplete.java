package com.clapsforapps.password_e_safe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by ALI on 9/26/2015.
 */
public class FragmentComplete extends Fragment {

    private Button btStart, btNext;
    private SharedPreferences settings;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings = getActivity().getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        btStart = (Button)getActivity().findViewById(R.id.btStart);
        btNext = (Button)getActivity().findViewById(R.id.btNext);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.edit().putBoolean(SharedPrefs.PREFS_FIRST_BOOL, false).commit();
                SharedPrefs.isInApp =true;
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complete, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        btNext.setVisibility(View.GONE);
    }
}
