package com.example.xlc.monkey.ImagesCompress.ImageLoader;

/**
 * @author:xlc
 * @date:2018/12/13
 * @descirbe:
 */
public interface ILoaderStrategy {

    void loadImage(LoaderOptions options);


    /**
     * 清理内存缓存
     */
    void clearMemoryCache();

    /**
     * 清理磁盘缓存
     */

    void clearDiskCache();
}
