/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.preference;

import com.example.android.apis.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * This activity is an example of a simple settings screen that has default
 * values.
 * <p>
 * In order for the default values to be populated into the
 * {@link SharedPreferences} (from the preferences XML file), the client must
 * call
 * {@link PreferenceManager#setDefaultValues(Context, int, boolean)}.
 * <p>
 * This should be called early, typically when the application is first created.
 * An easy way to do this is to have a common function for retrieving the
 * SharedPreferences that takes care of calling it.
 */
public class DefaultValues extends PreferenceActivity {
    // This is the global (to the .apk) name under which we store these
    // preferences.  We want this to be unique from other preferences so that
    // we do not have unexpected name conflicts, and the framework can correctly
    // determine whether these preferences' defaults have already been written.
    static final String PREFS_NAME = "defaults";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPrefs(this);
        getPreferenceManager().setSharedPreferencesName(PREFS_NAME);
        addPreferencesFromResource(R.xml.default_values);
    }

    static SharedPreferences getPrefs(Context context) {
        PreferenceManager.setDefaultValues(context, PREFS_NAME, MODE_PRIVATE,
                R.xml.default_values, false);
        return context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}
