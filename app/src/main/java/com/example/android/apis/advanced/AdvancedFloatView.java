package com.example.android.apis.advanced;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Toast;

import com.example.android.apis.ApiDemosApplication;
import com.example.android.apis.R;

/**
 * Advanced float view.
 *
 * <p>
 * Features :
 * (1) Show as an application overlay view;
 * (2) Use BitmapShader to create circle image;
 * Notice: I want to extend the {@link CardView} for circle image. But the image is white and I
 * don't know why. So I just use BitmapShader for this feature.
 * (3) Move with finger touch;
 * </p>
 *
 * <p>
 * Future features:
 * (1) Animation leading user to click;
 * </p>
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
     * Touch start mTouchStartWindowX.
     */
    private float mTouchStartX;
    /**
     * Touch start mTouchStartWindowY.
     */
    private float mTouchStartY;
    /**
     * mTouchStartWindowX value.
     */
    private float mTouchStartWindowX;
    /**
     * mTouchStartWindowY value.
     */
    private float mTouchStartWindowY;
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
    private WindowManager.LayoutParams mWindowParamater;
    /**
     * Bitmap shader.
     */
    private BitmapShader mBitmapShader;
    /**
     * Bitmap paint.
     */
    private Paint mBitmapPaint = new Paint();
    /**
     * Circle mTouchStartWindowX.
     */
    private int mCircleX;
    /**
     * Circle mTouchStartWindowY.
     */
    private int mCircleY;
    /**
     * Circle radius.
     */
    private int mRadius;
    /**
     * Shader matrix.
     */
    private final Matrix mShaderMatrix = new Matrix();

    /**
     * Constructor.
     *
     * @param context Context.
     */
    public AdvancedFloatView(@NonNull Context context) {
        super(context);
        init();
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
        AdvancedFloatView advancedFloatView = new AdvancedFloatView(context);
        advancedFloatView.setImageResource(R.drawable.ic_code);
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
        param.horizontalMargin = AdvancedConstants.FLOAT_VIEW_MARGIN;
        param.verticalMargin = AdvancedConstants.FLOAT_VIEW_MARGIN;

        // Notice : Do not set param gravity, otherwise there will be shrink when moving the view.
        // param.gravity = Gravity.RIGHT | Gravity.TOP;
        // Notice end.

        int viewSize = context.getResources().getDimensionPixelOffset(R.dimen
                .advanced_float_view_size);
        param.width = viewSize;
        param.height = viewSize;

        advancedFloatView.mWindowParamater = param;

        wm.addView(advancedFloatView, param);
        sInstance = advancedFloatView;

        return advancedFloatView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsMove = false;
                // Notice : Should use getRawX/Y. If getX/Y is used, there will be shrink when
                // moving the view.
                mTouchStartX = event.getRawX();
                mTouchStartY = event.getRawY();
                // Record the window parameter.
                mTouchStartWindowX = mWindowParamater.x;
                mTouchStartWindowY = mWindowParamater.y;

                break;
            case MotionEvent.ACTION_MOVE:
                mIsMove = true;
                updateViewPosition(event.getRawX() - mTouchStartX, event.getRawY() - mTouchStartY);
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
     *
     * @param deltaX Move distance in screen x.
     * @param deltaY Move distance in screen y.
     */
    private void updateViewPosition(float deltaX, float deltaY) {
        if (null != mWindowParamater) {
            mWindowParamater.x = (int) (mTouchStartWindowX + deltaX);
            mWindowParamater.y = (int) (mTouchStartWindowY + deltaY);
            mWindowManager.updateViewLayout(this, mWindowParamater);
        }
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
            boolean isApiDemosListPage = false;//currentActivity.equals(ApiDemos.class.getName());
            // TODO: Check whether is advanced page.
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

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setupBitmapShader();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        setupBitmapShader();
    }

    private void setupBitmapShader() {
        if (mBitmapPaint == null) {
            return;
        }
        mShaderMatrix.set(null);
        Bitmap bitmap = AdvancedUtils.drawableToBitmap(getDrawable());
        if (bitmap == null) {
            invalidate();
            return;
        }
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);

        float scale = Math.max(getWidth() * 1f / bitmap.getWidth(), getHeight() * 1f / bitmap
                .getHeight());
        float dx = (getWidth() - bitmap.getWidth() * scale) / 2;
        float dy = (getHeight() - bitmap.getHeight() * scale) / 2;
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(dx, dy);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
        invalidate();
    }

    /**
     * Init.
     */
    private void init() {
        mBitmapPaint.setAntiAlias(true);
        super.setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleX = w / 2;
        mCircleY = h / 2;
        mRadius = Math.min(getWidth(), getHeight()) / 2;
        setupBitmapShader();

        // Move view to the right, top corner of the window.
        mWindowParamater.x = mWindowManager.getDefaultDisplay().getWidth() / 2 - getWidth();
        mWindowParamater.y = -1 * mWindowManager.getDefaultDisplay().getWidth() / 2;
        mWindowManager.updateViewLayout(this, mWindowParamater);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCircleX, mCircleY, mRadius, mBitmapPaint);
    }
}
