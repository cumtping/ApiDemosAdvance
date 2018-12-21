/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;

/**
 * This is an example implementation of the {@link Instrumentation}
 * class, allowing you to run tests against application code.  The
 * instrumentation implementation here is loaded into the application's
 * process, for controlling and monitoring what it does.
 */
public class ContactsSelectInstrumentation extends Instrumentation {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        // When this instrumentation is created, we simply want to start
        // its test code off in a separate thread, which will call back
        // to us in onStart().
        start();
    }

    @Override
    public void onStart() {
        super.onStart();
        // First start the activity we are instrumenting -- the contacts
        // list.
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(getTargetContext(),
                "com.android.phone.Dialer");
        Activity activity = startActivitySync(intent);

        // This is the Activity object that was started, to do with as we want.
        Log.i("ContactsSelectInstrumentation", "Started: " + activity);

        // Monitor for the expected start activity call.
        ActivityMonitor am = addMonitor(IntentFilter.create(
            Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_ITEM_TYPE), null, true);

        // We are going to enqueue a couple key events to simulate the user
        // selecting an item in the list.
        sendKeySync(new KeyEvent(
            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
        sendKeySync(new KeyEvent(
            KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN));
        sendKeySync(new KeyEvent(
            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER));
        sendKeySync(new KeyEvent(
            KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_CENTER));

        // Was the expected activity started?
        if (checkMonitorHit(am, 1)) {
            Log.i("ContactsSelectInstrumentation", "Activity started!");
        } else {
            Log.i("ContactsSelectInstrumentation", "*** ACTIVITY NOT STARTED!");
        }

        // And we are done!
        Log.i("ContactsSelectInstrumentation", "Done!");
        finish(Activity.RESULT_OK, null);
    }
}

