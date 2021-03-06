/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.app;

import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;


public class RotationAnimation extends Activity {

    private int mRotationAnimation = LayoutParams.ROTATION_ANIMATION_ROTATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRotationAnimation(mRotationAnimation);

        setContentView(R.layout.rotation_animation);

        ((CheckBox)findViewById(R.id.windowFullscreen)).setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setFullscreen(isChecked);
                }
            }
        );

        ((RadioGroup)findViewById(R.id.rotation_radio_group)).setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        default:
                        case R.id.rotate:
                            mRotationAnimation = LayoutParams.ROTATION_ANIMATION_ROTATE;
                            break;
                        case R.id.crossfade:
                            mRotationAnimation = LayoutParams.ROTATION_ANIMATION_CROSSFADE;
                            break;
                        case R.id.jumpcut:
                            mRotationAnimation = LayoutParams.ROTATION_ANIMATION_JUMPCUT;
                            break;
                        case R.id.seamless:
                            mRotationAnimation = LayoutParams.ROTATION_ANIMATION_SEAMLESS;
                            break;
                    }
                    setRotationAnimation(mRotationAnimation);
                }
            }
        );
    }

    private void setFullscreen(boolean on) {
        Window win = getWindow();
        LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |=  LayoutParams.FLAG_FULLSCREEN;
        } else {
            winParams.flags &= ~LayoutParams.FLAG_FULLSCREEN;
        }
        win.setAttributes(winParams);
    }

    private void setRotationAnimation(int rotationAnimation) {
        Window win = getWindow();
        LayoutParams winParams = win.getAttributes();
        winParams.rotationAnimation = rotationAnimation;
        win.setAttributes(winParams);
    }
}
