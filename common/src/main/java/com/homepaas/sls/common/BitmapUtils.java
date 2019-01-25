package com.homepaas.sls.common;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * on 2016/2/24 0024
 *
 * @author zhudongjie .
 */
public class BitmapUtils {

    private static final String TAG = "BitmapUtils";

    public static void compressAndCreatePicture(Bitmap src,File file) {

        Bitmap percentBitmap = src ; //bitmapZoomByScale(src, 0.5f, 0.5f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        percentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        FileOutputStream fos = null;
        try {
            if (bytes != null) {
                fos = new FileOutputStream(file);
                fos.write(bytes);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
           // percentBitmap.recycle();
            try {
                baos.close();
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }

        }
    }


    public static Bitmap bitmapZoomByScale(Bitmap srcBitmap, float scaleWidth, float scaleHeight) {
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth,
                srcHeight, matrix, true);
        if (resizedBitmap != null) {
            return resizedBitmap;
        } else {

            return srcBitmap;
        }
    }
}
