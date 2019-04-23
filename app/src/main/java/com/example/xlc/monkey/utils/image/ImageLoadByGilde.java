package com.example.xlc.monkey.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

/**
 * @author:xlc
 * @date:2019/4/11
 * @descirbe:图片显示的公共类使用Glide
 */
public class ImageLoadByGilde implements ImageLoadInterface {


    private static final String TAG = "GlideUtils";


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
        //判断view是否还存在
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

            RequestOptions requestOptions = new RequestOptions();
            if (config != null) {
                if (config.defaultRes > 0) {
                    requestOptions.placeholder(config.defaultRes);
                }
                if (config.failRes > 0) {
                    requestOptions.error(config.failRes);
                }

                if (config.scaleType != null) {
                    switch (config.scaleType) {
                        case CENTER_CROP:
                            requestOptions.centerCrop();
                            break;
                        case FIT_CENTER:
                            requestOptions.fitCenter();
                            break;
                        default:
                            requestOptions.fitCenter();
                            break;
                    }
                } else {
                    requestOptions.fitCenter();
                }

                if (config.radius > 0) {
                    requestOptions.transform(new RoundedCorners(config.radius));
                }
            }

            BitmapImageViewTarget imageViewTarget = new BitmapImageViewTarget(view) {
                @Override
                public void onLoadStarted(@Nullable Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    if (imageLoadProcessInterface != null) {
                        imageLoadProcessInterface.onLoadStarted();
                    }
                }

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    super.onResourceReady(resource, transition);
                    if (imageLoadProcessInterface != null) {
                        imageLoadProcessInterface.onResourceReady();
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    if (imageLoadProcessInterface != null) {
                        imageLoadProcessInterface.onLoadFailed();
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    super.onLoadCleared(placeholder);
                    if (imageLoadProcessInterface != null) {
                        imageLoadProcessInterface.onLoadCleared();
                    }
                }

                @Override
                public void getSize(@NonNull SizeReadyCallback cb) {

                    if (config != null && config.width >= 0 && config.height >= 0) {
                        cb.onSizeReady(config.width, config.height);
                    } else {
                        super.getSize(cb);
                    }
                }
            };

            if (imageViewTarget != null) {
                Glide.with(context).asBitmap().load(url).apply(requestOptions).into(imageViewTarget);
            } else {
                Glide.with(context).asBitmap().load(url).apply(requestOptions).into(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeLoad(Context context, String url) {
        if (context != null) {
            Glide.with(context).resumeRequests();
        }
    }

    @Override
    public void pauseLoad(Context context, String url) {
        if (context != null) {
            Glide.with(context).pauseRequests();
        }

    }

    @Override
    public void clearImageView(Context context, ImageView imageView, String url) {
        if (context != null && imageView != null) {
            Glide.with(context).clear(imageView);
        }
    }
}
