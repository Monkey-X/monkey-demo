package com.example.xlc.monkey.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * @author:xlc
 * @date:2018/10/29
 * @descirbe:自动换行的Textview
 */
public class AutoTextView extends AppCompatTextView {

    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY//精确模式
                && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                && getWidth() > 0 && getHeight() > 0) {
            String newText = autoSplitText();
            if (!TextUtils.isEmpty(newText)) {
                setText(newText);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private String autoSplitText() {
        String rawText = getText().toString();//原始文本
        TextPaint tvPaint = getPaint();//paint 包含字体等信息
        float tvWidth = getWidth() - getPaddingLeft() - getPaddingRight();//控件可用的宽度
        //将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuffer sbNewText = new StringBuffer();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //整行宽度在控件的可用宽度之内，就不用处理了
                sbNewText.append(rawTextLine);
            }else{
                //如果整行宽度超过控件的可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth +=tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    }else{
                        sbNewText.append("\n");
                        lineWidth =0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length()-1);
        }
        return sbNewText.toString();

    }
}
