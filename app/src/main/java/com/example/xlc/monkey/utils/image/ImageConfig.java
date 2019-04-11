package com.example.xlc.monkey.utils.image;

import android.widget.ImageView;

/**
 * @author:xlc
 * @date:2019/4/11
 * @descirbe:图片加载框架的配置类
 */
public class ImageConfig {

    int defaultRes;//默认占位符
    int failRes;//失败占位图
    int radius;//圆角
    ImageView.ScaleType scaleType;//图片展示样式
    int width =-1;//图片宽
    int height = -1;//图片搞

    public ImageConfig(int defaultRes,int failRes,int radius,int width,int height,ImageView.ScaleType scaleType){
        this.defaultRes = defaultRes;
        this.failRes =failRes;
        this.radius = radius;
        this.width =width;
        this.height = height;
        this.scaleType = scaleType;
    }


    public ImageConfig(int defaultRes,int failRes,int radius,int width,int height){
        this(defaultRes,failRes,radius,width,height,ImageView.ScaleType.FIT_CENTER);
    }


    public ImageConfig(int defaultRes,int failRes,int width,int height){
        this(defaultRes,failRes,0,width,height);
    }

    public ImageConfig(int defaultRes,int failRes,int radius){
        this(defaultRes,failRes,radius,-1,-1);
    }

    public ImageConfig(int defaultRes,int failRes){
        this(defaultRes,failRes,0);
    }

    public ImageConfig(int defaultRes){
        this(defaultRes,-1);
    }

    public int getDefaultRes() {
        return defaultRes;
    }

    public void setDefaultRes(int defaultRes) {
        this.defaultRes = defaultRes;
    }

    public int getFailRes() {
        return failRes;
    }

    public void setFailRes(int failRes) {
        this.failRes = failRes;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
