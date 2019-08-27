package com.example.xlc.monkey.view.TapVerificationView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.xlc.monkey.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

/**
 * @author:xlc
 * @date:2019/7/9
 * @descirbe:自定义点选验证码
 * @url:https://mp.weixin.qq.com/s/DH1dW9jewesCh4OnA7YnCg
 */
public class TapVerificationView extends View {

    private final String TAG = "TapVerificationView";

    private Bitmap oldBitmap;
    private Bitmap bgBitmap;
    private int width;
    private int height;

    private Paint bgPaint;
    private Paint textPaint;
    private Paint selectPaint;
    private Paint selectTextPaint;
    private List<Region> regions = new ArrayList<Region>();//文字的区域

    private Random random;
    private RectF bgRectF;

    private String fonts = "";
    private int checkCode = 0;

    private boolean isInit = true;

    private List<Point> tapPoints = new ArrayList<>();
    private List<Integer> tapIndex = new ArrayList<>();
    private List<Point> textPoints = new ArrayList<>();
    private List<Integer> degrees = new ArrayList<>();


    public TapVerificationView(Context context) {
        this(context, null);
    }

    public TapVerificationView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TapVerificationView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        oldBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setFilterBitmap(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);//字体加粗
        textPaint.setColor(Color.parseColor("#AA000000"));
        textPaint.setShadowLayer(3, 2, 2, Color.RED);//添加阴影
        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));

        selectPaint = new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(Color.WHITE);

        selectTextPaint = new Paint();

        random = new Random();

        int temp = fonts.length() - 1;
        while (temp > -1) {
            checkCode += temp * Math.pow(10, temp);
            temp--;
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minimumHeight = getSuggestedMinimumHeight();
        int minimumWidth = getSuggestedMinimumWidth();
        width = measureSize(minimumWidth, widthMeasureSpec);
        height = width;
        bgBitmap = clipBitmap(oldBitmap, width, height);
        bgRectF = new RectF(0, 0, width, height);
        textPaint.setTextSize(width / 6);
        setMeasuredDimension(width, height);
    }

    /**
     * 绘制背景的大小
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     */
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (Region region : regions) {
                    if (region.contains(x, y)) {
                        isInit = false;
                        int index = regions.indexOf(region);
                        if (!tapIndex.contains(index)) {
                            tapIndex.add(index);
                            tapPoints.add(new Point(x, y));
                        }
                        if (tapIndex.size() == fonts.length()) {
                            StringBuilder s = new StringBuilder();
                            for (Integer i : tapIndex) {
                                s.append(i);
                            }
                            int result = Integer.parseInt(s.toString());
                            if (result == checkCode) {
                                handler.sendEmptyMessage(1);
                            } else {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        invalidate();
                    }
                }
        }
        return false;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int result = msg.what;
            switch (result) {
                case 1:
                    Log.d(TAG, "验证成功！");
                    if (mListener!=null) {
                        mListener.onResult(true);
                    }
                    break;
                default:
                    Log.d(TAG,"验证失败！");
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reDraw();
                            if (mListener!=null) {
                                mListener.onResult(false);
                            }
                        }
                    },1000);
                    break;

            }
        }
    };

    /**
     * 在处理点击的时候需要绘制点击的顺序，这时要判断是初始化验证码，还是用户再点击需要绘制点击的序号
     * if 是初始化验证码，就随机生成文字，绘制文字
     * if 点击绘制点击的顺序，这时不能重新随机生成坐标点，要让文字位置保持不动
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        regions.clear();
        canvas.drawBitmap(bgBitmap, null, bgRectF, bgPaint);
        if (isInit) {
            textPoints.clear();
            degrees.clear();
            tapIndex.clear();
            tapPoints.clear();
            for (int i = 0; i < fonts.length(); i++) {
                //这里吧文字倒着写是为了后面的验证方便
                String s = String.valueOf(fonts.charAt(fonts.length() - i - 1));
                int textSize = (int) textPaint.measureText(s);
                canvas.save();
                //在指定的范围随机生成坐标
                int x = random.nextInt(width - textSize);
                int y = random.nextInt(height - textSize);

                /**
                 * if 检测到点和文字区域有重合，则要重新随机生成点坐标
                 * 四个条件，分别是以（x,y）为绘制坐标的四个角的位置
                 */
                while (checkCover(x, y) || checkCover(x, y + textSize) || checkCover(x + textSize, y) || checkCover(x + textSize, y + textSize)) {
                    x = random.nextInt(width - textSize);
                    y = random.nextInt(height - textSize);
                }

                textPoints.add(new Point(x, y));
                canvas.translate(x, y);

                //随机生成一个30以内的整数，使文字倾斜一定的角度
                int degree = random.nextInt(30);
                degrees.add(degree);
                canvas.rotate(degree);
                canvas.drawText(s, 0, textSize, textPaint);
                regions.add(new Region(x, y, x + textSize, y + textSize));
                canvas.restore();

            }
        } else {
            for (int i = 0; i < fonts.length(); i++) {
                String s = String.valueOf(fonts.charAt(fonts.length() - 1 - i));
                int textSize = (int) textPaint.measureText(s);
                canvas.save();

                /**
                 * 点击文字出现序号显示点击的是第几个，而验证码文字没有变化，
                 * 实际上验证码文字在原来的位置也重新绘制了
                 */
                int x = textPoints.get(i).x;
                int y = textPoints.get(i).y;
                int degree = degrees.get(i);
                canvas.translate(x, y);
                canvas.rotate(degree);
                canvas.drawText(s, 0, textSize, textPaint);
                regions.add(new Region(x, y, textSize + x, textSize + y));
                canvas.restore();
            }

            //绘制点击的顺序
            for (Point tapPoint : tapPoints) {
                int index = tapPoints.indexOf(tapPoint) + 1;
                String s = index + "";
                int textSize = width / 6 / 3;
                selectTextPaint.setTextSize(textSize);
                canvas.drawCircle(tapPoint.x, tapPoint.y, textSize, selectPaint);

                Rect rect = new Rect();
                selectTextPaint.getTextBounds(s, 0, 1, rect);//获取字符串的宽高

                int textWidth = rect.width();
                int textHeight = rect.height();
                //绘制点击后的文字
                canvas.drawText(s, tapPoint.x - textWidth / 2, tapPoint.y + textHeight / 2, selectTextPaint);

            }
        }
    }

    /**
     * 重新绘制验证码界面
     */
    public void reDraw() {
        textPoints.clear();
        degrees.clear();
        tapPoints.clear();
        tapIndex.clear();

        isInit = true;

        invalidate();
    }


    /**
     * 检查点的范围
     *
     * @param x
     * @param y
     * @return
     */
    private boolean checkCover(int x, int y) {
        for (Region region : regions) {
            region.contains(x, y);
            return true;
        }
        return false;
    }

    public void setVerifyText(String s) {
        fonts = s;
        checkCode = 0;
        int temp = fonts.length() - 1;
        while (temp > -1) {
            checkCode += temp + Math.pow(10, temp);
            temp--;
        }
        invalidate();
    }




    public interface VerifyListener {
        void onResult(boolean result);
    }

    private VerifyListener mListener;

    public void setVerifyListener(VerifyListener listener){
        this.mListener = listener;
    }
}
