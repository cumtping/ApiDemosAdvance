/*
 * Copyright (c) 2018. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.example.android.apis;

import android.app.Application;

/**
 * This is an example of a {@link Application} class.  This can
 * be used as a central repository for per-process information about your app;
 * however it is recommended to use singletons for that instead rather than merge
 * all of these globals from across your application into one place here.
 * 
 * In this case, we have not defined any specific work for this Application.
 * 
 * See samples/ApiDemos/tests/src/com.example.android.apis/ApiDemosApplicationTests for an example
 * of how to perform unit tests on an Application object.
 */
public class ApiDemosApplication extends Application {
    @Override
    public void onCreate() {
    }
}
