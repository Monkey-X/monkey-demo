package com.example.xlc.monkey.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author: xlc
 * @date: 2018/8/20
 * @descriptor:给图片设置文字和图片水印
 */
public class WaterImageUtil {

    /**
     * 设置水印在左上角
     *
     * @param context
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskLeftTop(Context context, Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark
                , dp2px(context, paddingLeft)
                , dp2px(context, paddingTop));
    }

    /**
     * 设置水印在图片右下角
     *
     * @param context
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskRightBottom(Context context, Bitmap src, Bitmap watermark, int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到右上角
     *
     * @param context
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskRightTop(Context context, Bitmap src, Bitmap watermark, int paddingRight, int paddingTop) {
        return createWaterMaskBitmap(src, watermark
                , src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight)
                , dp2px(context, paddingTop));
    }

    /**
     * 设置水印图片到中间
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark
                , (src.getWidth() - watermark.getWidth()) / 2
                , (src.getHeight() - watermark.getHeight()) / 2);
    }


    /**
     * 设置水印图片到左下角
     *
     * @param context
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(Context context, Bitmap src, Bitmap watermark, int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark
                , dp2px(context, paddingLeft)
                , src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 图片添加文字到左上角
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text, int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint
                , dp2px(context, paddingLeft)
                , dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 图片添加文字在右下角
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text, int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint
                , bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight)
                , bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 图片添加文字到右上方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text, int size, int color, int paddingRight, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint
                , bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight)
                , bounds.height() + dp2px(context, paddingTop));
    }


    /**
     * 图片添加文字在左下方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextLeftBottom(Context context, Bitmap bitmap, String text, int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint
                , dp2px(context, paddingLeft)
                , bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 图片添加文字到中间
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text, int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context,size));
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        return drawTextToBitmap(bitmap,text,paint,
                (bitmap.getWidth()-bounds.width())/2,
                (bitmap.getHeight()+bounds.height())/2);
    }


    /**
     * 在图片上添加水印图片
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }

        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //将该图片作为画布
        Canvas canvas = new Canvas(newBitmap);
        //在画布上绘制原始的图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        //保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        //存储
        canvas.restore();
        return newBitmap;
    }

    /**
     * 图片上绘制文字
     *
     * @param bitmap
     * @param text
     * @param paint
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint.setDither(true);//获取跟清晰的图像采样
        paint.setFilterBitmap(true);//过滤一下
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dp * scale + 0.5f));
    }
}
