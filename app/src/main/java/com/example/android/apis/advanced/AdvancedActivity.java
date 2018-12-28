package com.example.android.apis.advanced;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.apis.R;

/**
 * Advanced activity.
 */
public class AdvancedActivity extends Activity {
    /** Log tag. */
    private static final String TAG = AdvancedActivity.class.getSimpleName();
    /** Request code for write external storage permission. */
    private static final int REQ_CODE_WRITE_STORAGE_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

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
        String appSdcardPath = AdvancedConstants.CODE_SDCARD_PATH;
        String codeZipName = AdvancedConstants.CODE_ASSET_PATH;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                boolean copyDone = AdvancedUtils.copyAssetToSdcard(AdvancedActivity.this,
                        codeZipName, appSdcardPath);
                boolean unzipDone = false;
                if (copyDone) {
                    unzipDone = AdvancedUtils.unZipFile(appSdcardPath + codeZipName, appSdcardPath);
                }
                Log.i(TAG, "Copy and unzip, copy=" + copyDone + ", unzip=" + unzipDone);
                return null;
            }
        }.execute();
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
