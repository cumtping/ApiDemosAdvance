/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Demonstrates how a well behaved view with internal selection
 * ({@link InternalSelectionView}) can cause its parent {@link ScrollView}
 * to scroll to keep the internally interesting rectangle on the screen.
 *
 * {@link InternalSelectionView} achieves this by calling {@link android.view.View#requestRectangleOnScreen}
 * each time its internal selection changes.
 *
 * {@link ScrollView}, in turn, implements {@link android.view.View#requestRectangleOnScreen}
 * thereby acheiving the result.  Note that {@link android.widget.ListView} also implements the
 * method, so views that call {@link android.view.View#requestRectangleOnScreen} that are embedded
 * within either {@link ScrollView}s or {@link android.widget.ListView}s can
 * expect to keep their internal interesting rectangle visible.
 */
public class InternalSelectionScroll extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView sv = new ScrollView(this);
        ViewGroup.LayoutParams svLp = new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(svLp);
        sv.addView(ll);

        InternalSelectionView isv = new InternalSelectionView(this, 10);
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2 * screenHeight);  // 2x screen height to ensure scrolling
        isv.setLayoutParams(llLp);
        ll.addView(isv);
        
        setContentView(sv);
    }
}
