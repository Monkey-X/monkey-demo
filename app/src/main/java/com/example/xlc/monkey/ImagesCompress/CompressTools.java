package com.example.xlc.monkey.ImagesCompress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author:xlc
 * @date:2018/9/12
 * @descirbe: 图片压缩工具
 */
public class CompressTools {

    private static volatile CompressTools INSTANCE;

    private Context context;
    //默认宽度
    private int maxWidth = 720;
    //默认高度
    private int maxHeight = 960;

    //默认压缩后的方式为JPEG
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;

    //默认的图片处理方式是ARGB_8888
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    //默认压缩质量为60
    private int quality = 60;

    //默认压缩质量是否为最优
    private boolean optimize = true;

    //是否使用原图分辨率，默认为false
    private boolean keepResolution = false;

    //储存路径
    private String destinationDirectoryPath;

    //文件名前缀
    private String fileNamePrefix;

    //文件名
    private String fileName;

    public static CompressTools getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CompressTools.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CompressTools(context);
                }
            }
        }
        return INSTANCE;
    }

    private CompressTools(Context context) {
        this.context = context;
        destinationDirectoryPath = context.getCacheDir().getPath() + File.pathSeparator + FileUtil.FILES_PATH;
    }

    public void compressToFile(final File file, final OnCompressListener mOnCompressListener) {
        FileUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                compressImageJni(context, Uri.fromFile(file), maxWidth, maxHeight, compressFormat, bitmapConfig, quality, destinationDirectoryPath,
                        fileNamePrefix, fileName, optimize, keepResolution, mOnCompressListener);
            }
        });
    }

    private void compressImageJni(Context context, Uri imageUri, int maxWidth, int maxHeight, Bitmap.CompressFormat compressFormat,
                                  Bitmap.Config bitmapConfig, int quality, String parentPath, String prefix, String fileName, boolean optimize, boolean keepResolution,
                                  final CompressTools.OnCompressListener mOnCompressListener) {
        final String filename = generateFilePath(context, parentPath, imageUri, compressFormat.name().toLowerCase(), prefix, fileName);
        FileUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOnCompressListener.onStart();
            }
        });
        Bitmap newBitmap;
        if (keepResolution) {
            newBitmap = readBitMap(FileUtil.getRealPathFromURI(context, imageUri));
        }else{
            NativeUtil.saveBitmapWithMaxWH(readBitMap(FileUtil.getRealPathFromURI(context, imageUri)), quality, filename, optimize, maxWidth, maxHeight);
        }

        FileUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (FileUtil.fileIsExists(filename)) {
                    mOnCompressListener.onSuccess(new File(filename));
                } else {
                    mOnCompressListener.onFail("创建文件失败");
                }
            }
        });
    }


    public static void compressTOBitmapJni(final Context context, final Uri imageUri, int maxWidth, int maxHeight, Bitmap.CompressFormat compressFormat,
                                           Bitmap.Config bitmapConfig, int quality, String parentPath, String prefix, String fileName, boolean optimize,
                                           final CompressTools.OnCompressBitmapListener mOnCompressBitmapListener) {
        final String filename = generateFilePath(context, parentPath, imageUri, compressFormat.name().toLowerCase(), prefix, fileName);
        FileUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOnCompressBitmapListener.onStart();
            }
        });
        Bitmap newBmp = readBitMap(FileUtil.getRealPathFromURI(context, imageUri));
        if (newBmp != null) {
            NativeUtil.saveBitmapWithMaxWH(newBmp, quality, filename, optimize, maxWidth, maxHeight);
        }
        FileUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (FileUtil.fileIsExists(filename)) {
                    mOnCompressBitmapListener.onSuccess(readBitMap(filename));
                } else {
                    mOnCompressBitmapListener.onFail("创建文件失败");
                }
            }
        });
    }

    private static String generateFilePath(Context context, String parentPath, Uri uri, String extension, String prefix, String fileName) {
        File file = new File(parentPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + File.separator + System.currentTimeMillis() + "." + extension;
    }


    public static class Builder {
        private CompressTools compressTools;

        public Builder(Context context) {
            compressTools = new CompressTools(context);
        }

        /**
         * 设置图片最大宽度
         *
         * @param maxWidth
         * @return
         */
        public Builder setMaxWidth(int maxWidth) {
            compressTools.maxWidth = maxWidth;
            return this;
        }

        /**
         * 设置图片最大高度
         *
         * @param maxHeight 最大高度
         */
        public Builder setHeight(int maxHeight) {
            compressTools.maxHeight = maxHeight;
            return this;
        }

        /**
         * 设置压缩的后缀格式
         */
        public Builder setBitmapFormat(Bitmap.CompressFormat compressFormat) {
            compressTools.compressFormat = compressFormat;
            return this;
        }

        /**
         * 设置是否开启压缩最优化
         */
        public Builder setOptimize(boolean optimize) {
            compressTools.optimize = optimize;
            return this;
        }

        /**
         * 设置Bitmap的参数
         */
        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            compressTools.bitmapConfig = bitmapConfig;
            return this;
        }

        /**
         * 设置分辨率是否保持原图分辨率
         */
        public Builder setKeepResolution(boolean keepResolution) {
            compressTools.keepResolution = keepResolution;
            return this;
        }

        /**
         * 设置压缩质量，建议50,50就足够了，采用底层压缩，最优解决方案
         *
         * @param quality 压缩质量，[0,100]
         */
        public Builder setQuality(int quality) {
            compressTools.quality = quality;
            return this;
        }

        /**
         * 设置目的存储路径
         *
         * @param destinationDirectoryPath 目的路径
         */
        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressTools.destinationDirectoryPath = destinationDirectoryPath;
            return this;
        }

        /**
         * 设置文件前缀
         *
         * @param prefix 前缀
         */
        public Builder setFileNamePrefix(String prefix) {
            compressTools.fileNamePrefix = prefix;
            return this;
        }

        /**
         * 设置文件名称
         *
         * @param fileName 文件名
         */
        public Builder setFileName(String fileName) {
            compressTools.fileName = fileName;
            return this;
        }

        public CompressTools build() {
            return compressTools;
        }
    }

    public interface OnCompressBitmapListener {
        void onStart();

        void onSuccess(Bitmap bitmap);

        void onFail(String error);
    }

    public interface OnCompressListener {
        void onStart();

        void onSuccess(File file);

        void onFail(String error);
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     */
    public static Bitmap readBitMap(String path) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        File file = new File(path);
        FileInputStream is;
        try {
            is = new FileInputStream(file);
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


}
