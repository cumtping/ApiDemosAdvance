package com.example.android.apis.advanced;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.android.apis.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Advanced activity.
 */
public class AdvancedActivity extends Activity {
    /** Extra data for current activity name. */
    public static final String EXTRA_CUR_ACTIVITY_NAME = "cur_activity_name";
    /** Log tag. */
    private static final String TAG = AdvancedActivity.class.getSimpleName();
    /** Request code for write external storage permission. */
    private static final int REQ_CODE_WRITE_STORAGE_PERMISSION = 0;
    /** Current activity name. */
    private String mCurActivityName;
    private TextView mCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

        mCodeView = findViewById(R.id.code_content);

        mCurActivityName = getIntent().getStringExtra(EXTRA_CUR_ACTIVITY_NAME);
        Log.i(TAG, "mCurActivityName=" + mCurActivityName);
        requestWriteStoragePermission();
    }

    /**
     * Request write storage permission. Later we will copy the code zip from project asset to
     * sdcard.
     */
    private void requestWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int ret = getPackageManager().checkPermission(Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, getPackageName());
            if (ret == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQ_CODE_WRITE_STORAGE_PERMISSION);
            } else {
                copyAndUnzip();
            }
        }
    }

    /**
     * Copy the code zip from project asset to sdcard.
     */
    private void copyAndUnzip() {
        new CopyUnzipTask(this).execute();
    }

    private static class CopyUnzipTask extends AsyncTask<Void, Void, Boolean> {
        /** Weak reference. */
        private WeakReference<AdvancedActivity> mActivityWeakReference;
        /** App sdcard path. */
        private String mAppSdcardPath = AdvancedConstants.CODE_SDCARD_PATH;
        /** Code zip name. */
        private String mCodeZipName = AdvancedConstants.CODE_ASSET_PATH;

        /**
         * Constructor.
         *
         * @param advancedActivity AdvancedActivity
         */
        public CopyUnzipTask(AdvancedActivity advancedActivity) {
            mActivityWeakReference = new WeakReference<>(advancedActivity);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (null == mActivityWeakReference.get()) {
                return false;
            }
            boolean copyDone = AdvancedUtils.copyAssetToSdcard(mActivityWeakReference.get(),
                    mCodeZipName, mAppSdcardPath);
            boolean unzipDone = false;
            if (copyDone) {
                unzipDone = AdvancedUtils.unZipFile(mAppSdcardPath + mCodeZipName, mAppSdcardPath);
            }
            Log.i(TAG, "Copy and unzip, copy=" + copyDone + ", unzip=" + unzipDone);
            return unzipDone;
        }

        @Override
        protected void onPostExecute(Boolean unzipOk) {
            super.onPostExecute(unzipOk);
            if (unzipOk && mActivityWeakReference.get() != null) {
                mActivityWeakReference.get().loadCode();
            }
        }
    }

    /**
     * Load code.
     */
    private void loadCode() {
        if (TextUtils.isEmpty(mCurActivityName)) {
            Log.e(TAG, "Load code. Wrong activity name, return.");
            return;
        }

        // Notice: Should not use split(".");
        String[] splits = mCurActivityName.split("\\.");
        if (null == splits || splits.length <= 0) {
            Log.e(TAG, "Load code. Wrong splits, return.");
            return;
        }

        String fileName = splits[splits.length - 1] + ".java";
        List<File> fileList = new ArrayList<>();
        AdvancedSearchUtils.findFiles(AdvancedConstants.CODE_SDCARD_PATH, fileName, fileList);
        Log.i(TAG, "Load code. fileName: " + fileName + ", result: " + fileList);

        if (fileList.size() < 1) {
            Log.i(TAG, "Load code. No result.");
            return;
        }

        new LoadCodeTask(this).execute(fileList.get(0));
    }

    /**
     * Load code task.
     */
    public static class LoadCodeTask extends AsyncTask<File, Void, String> {
        /** Weak reference. */
        private WeakReference<AdvancedActivity> mActivityWeakReference;

        /**
         * Constructor.
         *
         * @param advancedActivity AdvancedActivity.
         */
        public LoadCodeTask(AdvancedActivity advancedActivity) {
            mActivityWeakReference = new WeakReference<>(advancedActivity);
        }

        @Override
        protected String doInBackground(File... files) {
            if (files.length <= 0) {
                return null;
            }
            return AdvancedSearchUtils.getFileContent(files[0]);
        }

        @Override
        protected void onPostExecute(String ret) {
            super.onPostExecute(ret);
            if (!TextUtils.isEmpty(ret) && mActivityWeakReference.get() != null) {
                mActivityWeakReference.get().mCodeView.setText(ret);
            } else {
                Log.i(TAG, "Empty file content");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_CODE_WRITE_STORAGE_PERMISSION && permissions.length > 0 &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            copyAndUnzip();
        }
    }
}
