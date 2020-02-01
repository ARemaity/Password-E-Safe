package com.clapsforapps.password_e_safe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by ALI on 9/30/2015.
 */
public class FragmentRestore extends Fragment implements View.OnClickListener, ImportDbListener {

    private Button btRestore, btSkipRestore, btNext;
    private LinearLayout loRestore;
    private InitialConfig initialConfig;
    private boolean isRestored = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initialConfig = (InitialConfig)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restore, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loRestore = (LinearLayout)getActivity().findViewById(R.id.loRestore);
        btRestore = (Button)getActivity().findViewById(R.id.btRestore);
        btSkipRestore = (Button)getActivity().findViewById(R.id.btSkipRestore);
        btNext = (Button)getActivity().findViewById(R.id.btNext);
        btRestore.setOnClickListener(this);
        btSkipRestore.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isRestored)
            updateViewDbRestored();
        else
            btNext.setEnabled(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btRestore:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    SharedPrefs.importDB(getActivity(), this);
                else
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SharedPrefs.REQUEST_READ_EXTERNAL_STORAGE);
                break;
            case R.id.btSkipRestore:
                initialConfig.skipRestore();
                break;
        }
    }

    @Override
    public void onImport(boolean isDbImported) {
        if(isDbImported){
            updateViewDbRestored();
            isRestored = true;
            if(SharedPrefs.passkey != null)
                getActivity().getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE).edit().putBoolean(SharedPrefs.PREFS_KEY_BOOL, true).commit();
            Snackbar.make(getView(), R.string.backup_restored, Snackbar.LENGTH_LONG).show();
        }
        else
            Snackbar.make(getView(), R.string.backup_fail, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SharedPrefs.REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SharedPrefs.importDB(getActivity(), this);
                } else
                    Snackbar.make(getView(), R.string.cant_restore, Snackbar.LENGTH_LONG).show();
                return;
            }
        }
    }

    private void updateViewDbRestored(){
        loRestore.setVisibility(View.VISIBLE);
        btRestore.setVisibility(View.GONE);
        btSkipRestore.setVisibility(View.GONE);
        btNext.setEnabled(true);
    }
}
