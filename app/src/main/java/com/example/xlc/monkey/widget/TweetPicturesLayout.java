package com.example.xlc.monkey.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.example.xlc.monkey.R;
import com.example.xlc.monkey.bean.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xlc
 * @date:2018/11/14
 * @descirbe:显示九宫格图片
 */
public class TweetPicturesLayout extends ViewGroup implements View.OnClickListener {

    private static final int SINGLE_MAX_W = 120;
    private static final int SINGLE_MAX_H = 180;
    private static final int SINGLE_MIN_W = 34;
    private static final int SINGLE_MIN_H = 34;

    private float mVerticalSpacing;
    private float mHorizontalSpacing;
    private int mColumn;
    private int mMaxPictureSize;
    private List<Image> mImages;

    public TweetPicturesLayout(Context context) {
        this(context, null);
    }

    public TweetPicturesLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TweetPicturesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TweetPicturesLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();
        Resources resources = context.getResources();
        float density = resources.getDisplayMetrics().density;//屏幕像素密度
        int vSpace = (int) (4 * density);
        int hSpace = vSpace;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TweetPicturesLayout, defStyleAttr, defStyleRes);
            hSpace = array.getDimensionPixelOffset(R.styleable.TweetPicturesLayout_horizontalSpace, hSpace);
            vSpace = array.getDimensionPixelOffset(R.styleable.TweetPicturesLayout_horizontalSpace, vSpace);
            int column = array.getInt(R.styleable.TweetPicturesLayout_column, 3);
            setColumn(column);
            int maxPictureSize = array.getDimensionPixelOffset(R.styleable.TweetPicturesLayout_maxPictureSize, 0);
            setMaxPictureSize(maxPictureSize);
            array.recycle();
        }

        setVerticalSpacing(vSpace);
        setHorizontalSpacing(hSpace);
    }

    private void setHorizontalSpacing(int hSpace) {
        mHorizontalSpacing = hSpace;
    }

    private void setVerticalSpacing(int vSpace) {
        mVerticalSpacing = vSpace;
    }

    private void setMaxPictureSize(int maxPictureSize) {
        if (maxPictureSize < 0)
            maxPictureSize = 0;
        mMaxPictureSize = maxPictureSize;
    }

    /**
     * 设置几列
     *
     * @param column
     */
    private void setColumn(int column) {
        if (column < 1) {
            column = 1;
        }
        if (column > 20) {
            column = 20;
        }
        mColumn = column;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();

            if (childCount == 1) {
                View childView = getChildAt(0);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                childView.layout(paddingLeft, paddingTop, paddingLeft + childWidth, paddingTop + childHeight);
            } else {
                int mWidth = r - l;
                int paddingRight = getPaddingRight();

                int lineHeight = 0;
                int childLeft = paddingLeft;
                int childTop = paddingTop;

                for (int i = 0; i < childCount; ++i) {
                    View childView = getChildAt(i);

                    if (childView.getVisibility() == View.GONE) {
                        continue;
                    }

                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();

                    lineHeight = Math.max(childHeight, lineHeight);

                    if (childLeft + childWidth + paddingRight > mWidth) {
                        childLeft = paddingLeft;
                        childTop += mVerticalSpacing + lineHeight;
                        lineHeight = childHeight;
                    }
                    childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    childLeft += childWidth + mHorizontalSpacing;
                }
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int selfWidth = resolveSize(paddingLeft + paddingRight, widthMeasureSpec);
        int wantedHeight = paddingBottom + paddingTop;
        final int childCount = getChildCount();
        if (childCount == 0) {
        } else if (childCount == 1) {
            Image image = mImages.get(0);
            if (check(image)) {
                int imageW = image.getW();
                int imageH = image.getH();
                imageW = imageW <= 0 ? 100 : imageW;
                imageH = imageH <= 0 ? 100 : imageH;
                float density = getResources().getDisplayMetrics().density;
                // Get max width and height
                float maxContentW = Math.min(selfWidth - paddingRight - paddingLeft, density * SINGLE_MAX_W);
                float maxContentH = density * SINGLE_MAX_H;

                int childW, childH;

                float hToW = imageH / (float) imageW;
                if (hToW > (maxContentH / maxContentW)) {
                    childH = (int) maxContentH;
                    childW = (int) (maxContentH / hToW);
                } else {
                    childW = (int) maxContentW;
                    childH = (int) (maxContentW * hToW);
                }
                // Check the width and height below Min values
                int minW = (int) (SINGLE_MIN_W * density);
                if (childW < minW)
                    childW = minW;
                int minH = (int) (SINGLE_MIN_H * density);
                if (childH < minH)
                    childH = minH;

                View child = getChildAt(0);
                if (child != null) {
                    child.measure(MeasureSpec.makeMeasureSpec(childW, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(childH, MeasureSpec.EXACTLY));
                    wantedHeight += childH;
                }
            }
        } else {
            // Measure all child
            final float maxContentWidth = selfWidth - paddingRight - paddingLeft - mHorizontalSpacing * (mColumn - 1);
            // Get child size
            final int childSize = getMaxChildSize((int) (maxContentWidth / mColumn));

            for (int i = 0; i < childCount; ++i) {
                View childView = getChildAt(i);
                childView.measure(MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.EXACTLY));
            }

            int lines = (int) (childCount / (float) mColumn + 0.9);
            wantedHeight += (int) (lines * childSize + mVerticalSpacing * (lines - 1));
        }
        setMeasuredDimension(selfWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    private int getMaxChildSize(int size) {
        if (mMaxPictureSize == 0)
            return size;
        else
            return Math.min(mMaxPictureSize, size);
    }

    private boolean check(Image image) {
        return image != null
                && !TextUtils.isEmpty(image.getThumb())
                && !TextUtils.isEmpty(image.getHref());
    }

    public void setImages(List<String> images){
        if (images ==null && images.size() ==0) {
            return ;
        }
        List<Image> images1 = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            images1.add(Image.create(images.get(i)));
        }
        setImage(images1);
    }

    //传入image对象
    public void setImage(List<Image> images) {
        if (mImages == images) {
            return;
        }
        removeAllImage();
        //过滤不合法的数据
        if (images != null) {
            List<Image> isOkImages = new ArrayList<>();
            for (Image image : images) {
                if (check(image))
                    isOkImages.add(image);
            }
            images = isOkImages;
        }
        //进行赋值
        mImages = images;
        if (mImages != null && mImages.size() > 0) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            RequestManager requestManager = Glide.with(getContext());
            for (int i = 0; i < mImages.size(); i++) {
                Image image = mImages.get(i);
                if (!check(image)) {
                    continue;
                }

                View view = inflater.inflate(R.layout.lay_image_item, this, false);
                view.setTag(i);
                view.setOnClickListener(this);
                String thumb = image.getThumb();
                RequestBuilder<Drawable> error = requestManager.load(thumb).centerCrop().error(R.mipmap.ic_split_graph);
                //                DrawableRequestBuilder<String> builder = requestManager.load(thumb).centerCrop().error(R.mipmap.ic_split_graph);
                if (thumb.toLowerCase().endsWith("gif")) {
                    view.findViewById(R.id.iv_is_gif).setVisibility(VISIBLE);
                }
                addView(view);
                error.into((ImageView) view.findViewById(R.id.iv_picture));
            }
            //有数据进行显示，改变可视状态
            if (getVisibility() ==VISIBLE) {
                requestLayout();
            }else{
                setVisibility(VISIBLE);
            }
        }else{
            setVisibility(GONE);
        }

    }

    private void removeAllImage() {
        removeAllViews();
        mImages = null;
    }

    @Override
    public void onClick(View v) {
        if (mImages == null || mImages.size() <=0) {
            return;
        }

        Object tag = v.getTag();
        if (tag == null || !(tag instanceof Integer)) {
            return;
        }
        int index = (int) tag;
        if (index<0) index =0;
        if (index >= mImages.size()) index = mImages.size()-1;
        Image image = mImages.get(index);
        if (!check(image)) {
           return;
        }
        List<String> paths = image.getImagePath(mImages);
        if (paths ==null || paths.size() <=0) {
            return;
        }

        //跳转图片预览的界面
        showImage(getContext(),paths,index);
    }

    /**
     * 跳转图片预览的界面，传递图片的url
     * @param context
     * @param paths
     * @param index
     */
    public void showImage(Context context, List<String> paths, int index) {

    }
}
