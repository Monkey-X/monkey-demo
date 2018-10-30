package com.example.xlc.monkey.qqalbum;

/**
 * @author:xlc
 * @date:2018/10/29
 * @descirbe:相册数据对象
 */
public class AlbumBean {
    private String title;
    private String subId;
    private int url;
    public int value;//自己在实体中添加

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
