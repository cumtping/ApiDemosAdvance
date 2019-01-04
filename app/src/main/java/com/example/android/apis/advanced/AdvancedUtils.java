package com.example.android.apis.advanced;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * Advanced utils.
 */
public class AdvancedUtils {
    /** Log tag. */
    public static final String TAG = AdvancedUtils.class.getSimpleName();

    /**
     * Drawable to bitmap.
     *
     * @param drawable Drawable.
     *
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            // will be created of 1x1 pixel
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * Copy an asset file to sdcard.
     *
     * @param context    Context
     * @param assetName  Asset file name
     * @param sdcardPath Sdcard path
     *
     * @return Whether copy successfully
     */
    public static boolean copyAssetToSdcard(Context context, String assetName, String sdcardPath) {
        String filePath = sdcardPath + assetName;
        // Create folder if needed.
        File dirFile = new File(sdcardPath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File file = new File(filePath);
        if (file.exists()) {
            // File exists, return.
            return true;
        }

        AssetManager asset = context.getAssets();
        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = asset.open(assetName);
            out = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteCount);
            }
            out.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Open file error", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                // AssetManager shouldn't be closed.
                // if (asset != null) {
                //     asset.close();
                // }
            } catch (IOException e) {
                Log.e(TAG, "Open file error2", e);
            }
        }
        return false;
    }

    /**
     * Unzip a local zip file to a target path.
     *
     * @param zipFile    Local zip file path.
     * @param folderPath Target folder path.
     *
     * @return Whether unzip successfully.
     */
    public static boolean unZipFile(String zipFile, String folderPath) {
        ZipFile zfile;
        try {
            zfile = new ZipFile(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            try {
                ze = (ZipEntry) zList.nextElement();
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "IllegalArgumentException", e);
            }
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                dirstr.trim();
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os;
            FileOutputStream fos;
            File realFile = getRealFileName(folderPath, ze.getName());
            try {
                fos = new FileOutputStream(realFile);
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
            os = new BufferedOutputStream(fos);
            InputStream is;
            try {
                is = new BufferedInputStream(zfile.getInputStream(ze));
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
            int readLen;
            try {
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
        }
        try {
            zfile.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Get real file name.
     *
     * @param baseDir     Base dir
     * @param absFileName absolute file name.
     *
     * @return File
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        absFileName = absFileName.replace("\\", "/");
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                ret = new File(ret, substr);
            }

            if (!ret.exists()) {
                ret.mkdirs();
            }
            substr = dirs[dirs.length - 1];
            ret = new File(ret, substr);
            return ret;
        } else {
            ret = new File(ret, absFileName);
        }
        return ret;
    }
}
