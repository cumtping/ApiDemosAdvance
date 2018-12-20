/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.os;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import com.example.android.apis.R;

/**
 * <h3>Application showing the Trigger Sensor API for the Significant Motion sensor. </h3>

<p>This demonstrates the {@link SensorManager android.hardware.SensorManager
   android.hardware.TriggerEventListener} class.

<h4>Demo</h4>
OS / TriggerSensors

<h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td >src/com.example.android.apis/os/TriggerSensors.java</td>
 *             <td >TriggerSensors</td>
 *         </tr>
 * </table>
 */


class TriggerListener extends TriggerEventListener {
    private Context mContext;
    private TextView mTextView;

    TriggerListener(Context context, TextView textView) {
        mContext = context;
        mTextView = textView;
    }

    @Override
    public void onTrigger(TriggerEvent event) {
        if (event.values[0] == 1) {
            mTextView.append(mContext.getString(R.string.sig_motion) + "\n");
            mTextView.append(mContext.getString(R.string.sig_motion_auto_disabled) + "\n");
        }
        // Sensor is auto disabled.
    }
}

public class TriggerSensors extends Activity {
    private SensorManager mSensorManager;
    private Sensor mSigMotion;
    private TriggerListener mListener;
    private TextView mTextView;

    @Override
    protected void onResume() {
        super.onResume();
        if (mSigMotion != null && mSensorManager.requestTriggerSensor(mListener, mSigMotion))
                mTextView.append(getString(R.string.sig_motion_enabled) + "\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Call disable only if needed for cleanup.
        // The sensor is auto disabled when triggered.
        if (mSigMotion != null) mSensorManager.cancelTriggerSensor(mListener, mSigMotion);
    }


    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_sensors);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSigMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        mTextView = (TextView)findViewById(R.id.text);
        mListener = new TriggerListener(this, mTextView);
        if (mSigMotion == null) {
            mTextView.append(getString(R.string.no_sig_motion) + "\n");
        }
    }

    @Override
    protected void onStop() {
        if (mSigMotion != null) mSensorManager.cancelTriggerSensor(mListener, mSigMotion);
        super.onStop();
    }
}
