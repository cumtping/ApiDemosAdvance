package com.example.android.apis;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.android.apis.advanced.AdvancedFloatView;

/**
 * This is an example of a {@link Application} class.  This can
 * be used as a central repository for per-process information about your app;
 * however it is recommended to use singletons for that instead rather than merge
 * all of these globals from across your application into one place here.
 * <p>
 * In this case, we have not defined any specific work for this Application.
 * <p>
 * See samples/ApiDemos/tests/src/com.example.android.apis/ApiDemosApplicationTests for an example
 * of how to perform unit tests on an Application object.
 */
public class ApiDemosApplication extends Application {
    /**
     * Window layout parameters.
     */
    private final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();

    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new
            ActivityLifecycleCallbacks() {


        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
            AdvancedFloatView.checkToShowFloatView(getApplicationContext(), activity
                    .getComponentName().getClassName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            AdvancedFloatView.removeView(getApplicationContext());
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * Get window parameters.
     *
     * @return Window parameter.
     */
    public WindowManager.LayoutParams getWindowParams() {
        return mLayoutParams;
    }
}
