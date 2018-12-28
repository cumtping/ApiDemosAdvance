/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis.advanced;

import android.os.Environment;

/**
 * Constants for advanced functions.
 */
public interface AdvancedConstants {
    /**
     * Float view margin.
     */
    float FLOAT_VIEW_MARGIN = 0.02f;
    /**
     * Asset path for code zip file.
     */
    String CODE_ASSET_PATH = "code-p.zip";
    /**
     * Sdcard path for code.
     */
    String CODE_SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/ApiDemos/";
}
