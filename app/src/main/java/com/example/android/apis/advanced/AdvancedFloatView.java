package com.example.android.apis.advanced;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Toast;

import com.example.android.apis.ApiDemos;
import com.example.android.apis.ApiDemosApplication;
import com.example.android.apis.R;

/**
 * Advanced float view.
 */
public class AdvancedFloatView extends AppCompatImageView {
    /**
     * Extra key for activity.
     */
    public static final String EXTRA_ACTIVITY = "extra_activity";
    /**
     * Log tag.
     */
    private static final String TAG = AdvancedFloatView.class.getSimpleName();
    /**
     * Current instance.
     */
    private static AdvancedFloatView sInstance;
    /**
     * Touch start x.
     */
    private float mTouchStartX;
    /**
     * Touch start y.
     */
    private float mTouchStartY;
    /**
     * x value.
     */
    private float x;
    /**
     * y value.
     */
    private float y;
    /**
     * Is move.
     */
    private boolean mIsMove;
    /**
     * Window manager.
     */
    private WindowManager mWindowManager = (WindowManager) getContext().getApplicationContext()
            .getSystemService(Context.WINDOW_SERVICE);
    /**
     * Window manager layout parameter.
     */
    private WindowManager.LayoutParams mWindowParamater = ((ApiDemosApplication) getContext()
            .getApplicationContext()).getWindowParams();

    /**
     * Constructor.
     *
     * @param context Context.
     */
    private AdvancedFloatView(Context context) {
        super(context);
    }

    /**
     * Show view.
     *
     * @param context Context.
     *
     * @return AdvancedFloatView instance.
     */
    public static AdvancedFloatView showView(Context context) {
        if (sInstance != null) {
            AdvancedFloatView.removeView(context);
        }
        AdvancedFloatView advancedFloatView = new AdvancedFloatView(context.getApplicationContext
                ());
        advancedFloatView.setBackgroundResource(R.drawable.ic_code);
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService
                (Context.WINDOW_SERVICE);
        WindowManager.LayoutParams param = ((ApiDemosApplication) context.getApplicationContext()
        ).getWindowParams();

        param.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        param.format = 1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        param.alpha = 1.0f;

        param.gravity = Gravity.LEFT | Gravity.TOP;
        param.width = 60;
        param.height = 60;

        wm.addView(advancedFloatView, param);
        sInstance = advancedFloatView;

        return advancedFloatView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY() - 25;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsMove = false;
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                mIsMove = true;
                updateViewPosition();
                break;

            case MotionEvent.ACTION_UP:
                if (!mIsMove) {
                    performClick();
                }
                mTouchStartX = mTouchStartY = 0;
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Update view position.
     */
    private void updateViewPosition() {
        mWindowParamater.x = (int) (x - mTouchStartX);
        mWindowParamater.y = (int) (y - mTouchStartY);
        mWindowManager.updateViewLayout(this, mWindowParamater);
    }

    /**
     * Remove the view from window manager.
     */
    public static void removeView(Context context) {
        if (context == null) {
            Log.e(TAG, "removeView, context is null.");
            return;
        }
        if (sInstance == null) {
            Log.i(TAG, "removeView, sInstance is null.");
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        windowManager.removeView(sInstance);
        sInstance = null;
    }

    @Override
    public boolean performClick() {
        // TODO: Go to code activity.
        return super.performClick();
    }

    public static void checkToShowFloatView(Context context, String currentActivity) {
        if (currentActivity != null && context != null) {
            boolean isApiDemosPage = currentActivity.startsWith(context.getPackageName());
            boolean isApiDemosListPage = currentActivity.equals(ApiDemos.class.getName());
            // TODO: Check whether is advance page.
            boolean isAdvancedPage = false;

            if (isApiDemosPage && !isApiDemosListPage && !isAdvancedPage) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(context)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + context.getPackageName()));
                        context.startActivity(intent);
                        Toast.makeText(context, R.string.message_overlay_permission_required,
                                Toast.LENGTH_LONG).show();
                    } else {
                        AdvancedFloatView.showView(context);
                    }
                } else {
                    AdvancedFloatView.showView(context);
                }
            }
        }
    }
}
