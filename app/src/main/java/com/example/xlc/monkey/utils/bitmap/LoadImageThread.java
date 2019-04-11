package com.example.xlc.monkey.utils.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author:xlc
 * @date:2019/4/10
 * @descirbe:创建线程用于加载图片
 */
public class LoadImageThread extends Thread {

    private Activity mActivity;
    private String imageUrl;
    private ImageLoader mImageLoader;
    private ImageView mImageView;

    public LoadImageThread(Activity activity,ImageLoader imageLoader,ImageView imageView,String imageUrl){
        this.mActivity = activity;
        this.imageUrl = imageUrl;
        this.mImageLoader =imageLoader;
        this.mImageView = imageView;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final Bitmap bitmap = BitmapFactory.decodeStream(is);
                mImageLoader.addBitmapToLruCache("bitmap",bitmap);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection !=null) {
                connection.disconnect();
            }

            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
