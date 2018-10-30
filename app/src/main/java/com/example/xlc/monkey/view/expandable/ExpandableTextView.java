package com.example.xlc.monkey.view.expandable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.xlc.monkey.R;

/**
 * @author:xlc
 * @date:2018/9/26
 * @descirbe: 支持展开 收起 网页链接和 @用户 点击识别的textview
 */
public class ExpandableTextView extends AppCompatTextView {

    private static final int DEF_MAX_LINE = 4;
    public static String TEXT_CONTRACT = "收起";
    public static String TEXT_EXPEND = "展开";
    public static final String Space = " ";
    public static String TEXT_TARGET = "网页链接";
    public static final String IMAGE_TARGET = "窗";
    public static final String TARGET = IMAGE_TARGET + TEXT_TARGET;
    public static final String DEFAULT_CONTENT = "";
    private int mLimitLines;
    private boolean mNeedExpand;
    private boolean mNeedContract;
    private boolean mNeedAnimation;
    private boolean mNeedSelf;
    private boolean mNeedMention;
    private boolean mNeedLink;
    private String mContractString;
    private String mExpandString;
    private int mExpandTextColor;
    private int mEndExpandTextColor;
    private int mContractTextColor;
    private int mLinkTextColor;
    private int mSelfTextColor;
    private int mMentionTextColor;
    private Drawable mLinkDrawable;
    private int currentLines;
    private Context mContext;
    private TextPaint mPaint;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TEXT_CONTRACT = context.getString(R.string.social_contract);
        TEXT_EXPEND = context.getString(R.string.social_expand);
        TEXT_TARGET = context.getString(R.string.social_text_target);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyleAttr, 0);
            mLimitLines = a.getInt(R.styleable.ExpandableTextView_ep_max_line, DEF_MAX_LINE);
            mNeedExpand = a.getBoolean(R.styleable.ExpandableTextView_ep_need_expand, true);
            mNeedContract = a.getBoolean(R.styleable.ExpandableTextView_ep_need_contract, false);
            mNeedAnimation = a.getBoolean(R.styleable.ExpandableTextView_ep_need_animation, true);
            mNeedSelf = a.getBoolean(R.styleable.ExpandableTextView_ep_need_self, false);
            mNeedMention = a.getBoolean(R.styleable.ExpandableTextView_ep_need_mention, true);
            mNeedLink = a.getBoolean(R.styleable.ExpandableTextView_ep_need_link, true);
            mContractString = a.getString(R.styleable.ExpandableTextView_ep_contract_text);
            mExpandString = a.getString(R.styleable.ExpandableTextView_ep_expand_text);

            if (TextUtils.isEmpty(mExpandString)) {
                mExpandString = TEXT_EXPEND;
            }

            if (TextUtils.isEmpty(mContractString)) {
                mContractString = TEXT_CONTRACT;
            }

            mExpandTextColor = a.getColor(R.styleable.ExpandableTextView_ep_expand_color, Color.parseColor("#999999"));
            mEndExpandTextColor = a.getColor(R.styleable.ExpandableTextView_ep_end_color, Color.parseColor("#999999"));
            mContractTextColor = a.getColor(R.styleable.ExpandableTextView_ep_contract_color, Color.parseColor("#999999"));
            mLinkTextColor = a.getColor(R.styleable.ExpandableTextView_ep_link_color, Color.parseColor("#FF6200"));
            mSelfTextColor = a.getColor(R.styleable.ExpandableTextView_ep_self_color, Color.parseColor("#FF6200"));
            mMentionTextColor = a.getColor(R.styleable.ExpandableTextView_ep_mention_color, Color.parseColor("#FF6200"));

            int resourceId = a.getResourceId(R.styleable.ExpandableTextView_ep_link_res, R.mipmap.link);
            mLinkDrawable = getResources().getDrawable(resourceId);
            currentLines = mLimitLines;
            a.recycle();
        }else{
            mLinkDrawable = context.getResources().getDrawable(R.mipmap.link);
        }

        mContext = context;
        mPaint = getPaint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinkDrawable.setBounds(0,0,30,30);
    }
}
