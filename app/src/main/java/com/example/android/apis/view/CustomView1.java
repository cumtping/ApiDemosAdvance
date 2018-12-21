/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.view;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;


/**
 * Demonstrates creating a Screen that uses custom views. This example uses
 * {@link LabelView}, which is defined in
 * SDK/src/com/example/android/apis/view/LabelView.java.
 * 
 */
public class CustomView1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_1);
    }
}
