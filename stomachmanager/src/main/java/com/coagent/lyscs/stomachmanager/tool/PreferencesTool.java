package com.coagent.lyscs.stomachmanager.tool;

import android.content.SharedPreferences;

/**
 * Created by lyscs on 2017/6/10.
 */
public class PreferencesTool {

    private PreferencesTool mPreferencesTool;

    public void writePreferencesData(String key, String data, SharedPreferences preferences){
        if (preferences == null){
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

}
