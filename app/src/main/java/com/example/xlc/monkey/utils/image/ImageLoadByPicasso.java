package com.example.xlc.monkey.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * @author:xlc
 * @date:2019/4/11
 * @descirbe:图片显示的公共类使用Picasso
 */
public class ImageLoadByPicasso implements ImageLoadInterface {

    private static final String TAG = "PicassoUtils";

    /**
     * glide加载图片
     *
     * @param context
     * @param view
     * @param url
     * @param config                    配置参数
     * @param imageLoadProcessInterface 加载过程监听
     */
    @Override
    public void display(Context context, ImageView view, String url, final ImageConfig config, final ImageLoadProcessInterface imageLoadProcessInterface) {
        if (context == null) {
            return;
        }

        if (view == null) {
            return;
        }

        Context context1 = view.getContext();
        if (context1 instanceof Activity) {
            if (((Activity) context1).isFinishing()) {
                return;
            }
        }

        try {
            if ((config == null || config.defaultRes <= 0) && TextUtils.isEmpty(url)) {
                return;
            }

            RequestCreator requestCreator = null;
            Uri loadUrl = null;
            if (url.startsWith("http")) {
                loadUrl = Uri.parse(url);
            } else {
                if (url.startsWith("file://")) {
                    //文件的方式
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                        //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
                        url = Uri.parse(url).getPath();
                    }
                }
                File file = new File(url);
                if (file != null && file.exists()) {
                    //本地文件
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                        //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
                        loadUrl = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                    } else {
                        loadUrl = Uri.fromFile(file);
                    }
                } else {
                    //可能是资源路径的地址
                    loadUrl = Uri.parse(url);
                }
            }

            requestCreator = Picasso.get().load(loadUrl);
            if (config !=null) {
                if (config.defaultRes > 0) {
                    requestCreator.placeholder(config.defaultRes);
                }
                if (config.failRes > 0) {
                    requestCreator.error(config.failRes);
                }
                if (config.width > 0 && config.height > 0) {
                    requestCreator.resize(config.width, config.height);
                }
                if (config.radius > 0) {
                    requestCreator.transform(new Transformation() {
                        @Override
                        public Bitmap transform(Bitmap source) {
                            final Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(target);
                            RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
                            canvas.drawRoundRect(rect, config.radius, config.radius, paint);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                            canvas.drawBitmap(source, 0, 0, paint);
                            source.recycle();
                            return target;
                        }

                        @Override
                        public String key() {
                            return "radius-transform";
                        }
                    });
                }
            }
            if (imageLoadProcessInterface != null) {
                requestCreator.tag(url).into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (imageLoadProcessInterface != null) {
                            imageLoadProcessInterface.onResourceReady();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        if (imageLoadProcessInterface != null) {
                            imageLoadProcessInterface.onLoadFailed();
                        }
                    }
                });
            } else {
                requestCreator.tag(url).into(view);
            }

        } catch (Exception e) {
        }
    }

    @Override
    public void resumeLoad(Context context, String url) {
        if (!TextUtils.isEmpty(url))
            Picasso.get().resumeTag(url);
    }

    @Override
    public void pauseLoad(Context context, String url) {
        if (!TextUtils.isEmpty(url))
            Picasso.get().pauseTag(url);
    }

    @Override
    public void clearImageView(Context context, ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url))
            Picasso.get().invalidate(url);
    }
}
