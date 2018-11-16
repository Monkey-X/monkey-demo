package com.example.xlc.monkey.bean;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xlc
 * @date:2018/11/15
 * @descirbe: 显示九宫格图片的实体类
 */
public class Image {

    private String thumb;
    private String href;
    private String name;
    private int type;
    private int w;
    private int h;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public static Image create(String path) {
        Image image = new Image();
        image.setHref(path);
        image.setHref(path);
        return image;
    }

    public List<String> getImagePath(List<Image> image) {
        if (image == null || image.size() ==0)
            return null;
        List<String> paths = new ArrayList<>();
        for (Image image1 : image) {
            if (check(image1)) {
                paths.add(image1.href);
            }
        }
        return paths;
    }

    public static boolean check(Image image) {
        return image != null
                && !TextUtils.isEmpty(image.getThumb())
                && !TextUtils.isEmpty(image.getHref());
    }
}
