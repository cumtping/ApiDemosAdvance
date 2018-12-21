/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.apis.R;


/**
 * Example of how to use a custom title {@link Window#FEATURE_CUSTOM_TITLE}.
 * <h3>CustomTitle</h3>

<p>This demonstrates how a custom title can be used.</p>

<h4>Demo</h4>
App/Title/Custom Title
 
<h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td >src/com.example.android.apis/app/CustomTitle.java</td>
 *             <td >The Custom Title implementation</td>
 *         </tr>
 *         <tr>
 *             <td >/res/any/layout/custom_title.xml</td>
 *             <td >Defines contents of the screen</td>
 *         </tr>
 * </table> 
 */
public class CustomTitle extends Activity {
    
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link Activity#setContentView(int)} to
     * describe what is to be displayed in the screen.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.custom_title);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_1);
        
        final TextView leftText = (TextView) findViewById(R.id.left_text);
        final TextView rightText = (TextView) findViewById(R.id.right_text);
        final EditText leftTextEdit = (EditText) findViewById(R.id.left_text_edit);
        final EditText rightTextEdit = (EditText) findViewById(R.id.right_text_edit);
        Button leftButton = (Button) findViewById(R.id.left_text_button);
        Button rightButton = (Button) findViewById(R.id.right_text_button);
        
        leftButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                leftText.setText(leftTextEdit.getText());
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                rightText.setText(rightTextEdit.getText());
            }
        });
    }
}
