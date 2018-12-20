/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.app;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;


/**
 * <h3>Fancy Blur Activity</h3>
 * 
 * <p>This demonstrates the how to write an activity that is translucent,
 * allowing windows underneath to show through, with a fancy blur
 * compositing effect.</p>
 */
public class TranslucentBlurActivity extends Activity {
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle icicle) {
        // Be sure to call the super class.
        super.onCreate(icicle);

        // Have the system blur any windows behind this one.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        
        // See assets/res/any/layout/translucent_background.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.translucent_background);
    }
}
