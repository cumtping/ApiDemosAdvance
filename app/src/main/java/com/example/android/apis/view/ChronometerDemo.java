/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.view;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;

public class ChronometerDemo extends Activity {
    Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chronometer);

        Button button;

        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        // Watch for button clicks.
        button = (Button) findViewById(R.id.start);
        button.setOnClickListener(mStartListener);

        button = (Button) findViewById(R.id.stop);
        button.setOnClickListener(mStopListener);

        button = (Button) findViewById(R.id.reset);
        button.setOnClickListener(mResetListener);

        button = (Button) findViewById(R.id.set_format);
        button.setOnClickListener(mSetFormatListener);

        button = (Button) findViewById(R.id.clear_format);
        button.setOnClickListener(mClearFormatListener);
    }

    OnClickListener mStartListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.start();
        }
    };

    OnClickListener mStopListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.stop();
        }
    };

    OnClickListener mResetListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.setBase(SystemClock.elapsedRealtime());
        }
    };

    OnClickListener mSetFormatListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.setFormat("Formatted time (%s)");
        }
    };

    OnClickListener mClearFormatListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.setFormat(null);
        }
    };
}
