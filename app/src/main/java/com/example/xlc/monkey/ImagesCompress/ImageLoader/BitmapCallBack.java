package com.example.xlc.monkey.ImagesCompress.ImageLoader;

import android.graphics.Bitmap;

/**
 * @author:xlc
 * @date:2018/12/13
 * @descirbe:
 */
public interface BitmapCallBack {

    void onBitmapLoaded(Bitmap bitmap);
    void onBitmapFailed(Exception e);

    public static class EmptyCallback implements BitmapCallBack{

        @Override
        public void onBitmapLoaded(Bitmap bitmap) {

        }

        @Override
        public void onBitmapFailed(Exception e) {

        }
    }
}
