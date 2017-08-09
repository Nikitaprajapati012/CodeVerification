package com.example.nikita.codeverification.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.nikita.codeverification.Utils.Constants.MyPREFERENCES;

/*** Created by nikita on 9/8/17.
 */

public class Utils {
    public Context mContext;
    public SharedPreferences mPreference;

    public Utils(Context mContext) {
        this.mContext = mContext;
        mPreference = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public static void WriteSharedPreference(Context mContext, String key, String value) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = preferences.edit().putString(key, value);
        editor.apply();
    }

    public static void ClearSharedPreference(Context mContext, String value) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = preferences.edit().remove(value);
        editor.apply();
    }

    public static String ReadSharedPreference(Context mContext, String key) {
        String data = "";
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = preferences.edit();
        data = preferences.getString(key, " ");
        editor.apply();
        return data;
    }

}
