package com.example.xlc.monkey.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author:xlc
 * @date:2019/4/10
 * @descirbe:封装获取缓存Bitmap，添加Bitmap到缓存中以及从缓存中移除Bitmap(内存缓存)
 */
public class ImageLoader {

    private LruCache<String, Bitmap> mLruCache;

    public ImageLoader() {
        int memorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = memorySize / 8;
        //初始化LruCache且设置缓存大小
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算每一个缓存Bitmap所占的内存大小，内存单位应该和cachesize的单位保持一致
                return value.getByteCount() / 1024;
            }
        };
    }


    /**
     * 获取缓存的Bitmap
     * @param key
     * @return
     */
    public Bitmap getBitmapFormLruCache(String key){
        if (key!=null) {
            return mLruCache.get(key);
        }

        return null;
    }

    /**
     * 添加bitmap到LruCache
     * @param key
     * @param bitmap
     */
    public void addBitmapToLruCache(String key,Bitmap bitmap){
        if (getBitmapFormLruCache(key) == null) {
            mLruCache.put(key,bitmap);
        }
    }

    /**
     * 移除缓存
     * @param key
     */
    public void removeBitmapFormLruCache(String key){
        if (key !=null) {
            mLruCache.remove(key);
        }
    }
}
