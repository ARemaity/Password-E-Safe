package com.clapsforapps.password_e_safe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ALI on 9/26/2015.
 */
public class About extends AppCompatActivity {

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        settings = getApplicationContext().getSharedPreferences(SharedPrefs.PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefs.isInApp = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SharedPrefs.isInApp && settings.getBoolean(SharedPrefs.PREFS_KEY_BOOL, false))
            startActivityForResult(new Intent(About.this, PasskeyActivity.class), MainActivity.ACTIVITY_PASSKEY);
        SharedPrefs.isInApp = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.ACTIVITY_PASSKEY && resultCode == MainActivity.CLOSE_ALL) {
            setResult(MainActivity.CLOSE_ALL);
            finish();
        }
    }
}
