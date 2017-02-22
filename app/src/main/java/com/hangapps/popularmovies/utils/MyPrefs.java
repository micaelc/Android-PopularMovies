package com.hangapps.popularmovies.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mcampos on 22/02/2017.
 */

public class MyPrefs {

	// ***************************************
	// ******** Preferences & State **********
	// ***************************************

	public static void StorePreference(Activity activity, String key, String value) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.MyPreferences.MY_FREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String GetStringPreference(Activity activity, String key) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.MyPreferences.MY_FREFS_NAME, MODE_PRIVATE);
		if (key != null) {
			return prefs.getString(key, null);
		}
		return null;
	}

	public static void StorePreference(Activity activity, String key, boolean value) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.MyPreferences.MY_FREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean GetBooleanPreference(Activity activity, String key) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.MyPreferences.MY_FREFS_NAME, MODE_PRIVATE);
		if (key != null) {
			return prefs.getBoolean(key, false);
		}
		return false;
	}
}
