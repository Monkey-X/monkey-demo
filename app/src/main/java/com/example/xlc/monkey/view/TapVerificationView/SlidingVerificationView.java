package com.example.xlc.monkey.view.TapVerificationView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.xlc.monkey.R;

import java.util.Random;

import androidx.annotation.Nullable;

/**
 * @author:xlc
 * @date:2019/7/10
 * @descirbe: https://www.jianshu.com/p/a6a7a1ea85d1
 */
public class SlidingVerificationView extends View {

    private int width;
    private int height;

    private Paint paintShadow;
    private Paint paintSrc;
    private Paint bgPaint;

    private int shadowSize = dp2px(60);
    private int padding = dp2px(40);

    private int shadowLeft;
    private int srcLeft = padding;

    private Bitmap bgBitmap;
    private Bitmap newBgBitmap;
    private Bitmap srcBitmap;

    private float curX;
    private float lastX;
    private int dx;

    public SlidingVerificationView(Context context) {
        this(context, null);
    }

    public SlidingVerificationView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingVerificationView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paintShadow = new Paint();
        paintShadow.setAntiAlias(true);
        paintShadow.setColor(Color.parseColor("#AA000000"));

        paintSrc = new Paint();
        paintSrc.setAntiAlias(true);
        paintSrc.setFilterBitmap(true);
        paintSrc.setStyle(Paint.Style.FILL_AND_STROKE);
        paintSrc.setColor(Color.WHITE);

        bgPaint = new Paint();
        bgPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.OUTER));
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);

        bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minimumWidth = getSuggestedMinimumWidth();

        //根据原背景图宽高比设置画布尺寸
        width = measureSize(minimumWidth, widthMeasureSpec);
        float scale = width / ((float) bgBitmap.getWidth());
        height = (int) (bgBitmap.getHeight() * scale);
        setMeasuredDimension(width, height);

        //根据画布尺寸生成相同尺寸的背景图
        newBgBitmap = clipBitmap(bgBitmap, width, height);
        //根据新的背景图生成填充部分
        srcBitmap = createSmallBitmap(newBgBitmap);
    }

    private Bitmap createSmallBitmap(Bitmap var) {
        Bitmap bitmap = Bitmap.createBitmap(shadowSize, shadowSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);//将画东西到bitmap里面
        canvas.drawCircle(shadowSize / 2, shadowSize / 2, shadowSize / 2, paintSrc);

        //设置混合模式
        paintSrc.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //在指定范围随机生成空缺部分坐标，保证空缺部分出现在view右侧
        int min = width / 3;
        int max = width - shadowSize / 2 - padding;
        Random random = new Random();
        shadowLeft = random.nextInt(max) % (max - min + 1) + min;
        Rect rect = new Rect(shadowLeft, (height - shadowSize) / 2, shadowLeft + shadowSize, (height + shadowSize) / 2);
        RectF rectF = new RectF(0, 0, shadowSize, shadowSize);
        /**
         * rect 对原图片的裁剪区域
         * rectF 将裁剪完的原图片绘制到view控件上的区域
         */
        canvas.drawBitmap(var, rect, rectF, paintSrc);
        paintSrc.setXfermode(null);
        return bitmap;
    }

    private Bitmap clipBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = defaultSize;
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = size;
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        curX = event.getRawX();//相对屏幕的位置
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int) (curX - lastX);
                srcLeft = dx + padding;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                boolean isSuccess = Math.abs(srcLeft-shadowLeft)<8;
                if (isSuccess) {
                    Log.d("w","check success!");
                }else{
                    Log.d("w","验证失败！");
                    srcBitmap = createSmallBitmap(newBgBitmap);
                    srcLeft =padding;
                    invalidate();
                }
                if (mListener!=null) {
                    mListener.onResult(isSuccess);
                }
        }
        return true;
    }

    public interface VerifyListener {
        void onResult(boolean result);
    }

    private VerifyListener mListener;

    public void setVerifyListener(VerifyListener listener){
        this.mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, width, height);
        //画背景图
        canvas.drawBitmap(newBgBitmap, null, rectF, paintSrc);

        bgPaint.setColor(Color.parseColor("#000000"));
        //画空缺部分周围阴影
        canvas.drawCircle(shadowLeft + shadowSize / 2, height / 2, shadowSize / 2, bgPaint);
        //画空缺部分
        canvas.drawCircle(shadowLeft + shadowSize / 2, height / 2, shadowSize / 2, paintShadow);

        Rect rect = new Rect(srcLeft, (height - shadowSize) / 2, srcLeft + shadowSize, (height + shadowSize) / 2);
        bgPaint.setColor(Color.parseColor("#FFFFFF"));
        //画填充部分周围的阴影
        canvas.drawCircle(srcLeft + shadowSize / 2, height / 2, shadowSize / 2, bgPaint);
        //画填充部分
        canvas.drawBitmap(srcBitmap, null, rect, paintSrc);
    }

    public static int dp2px(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }
}
