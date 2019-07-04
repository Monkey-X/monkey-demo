package com.example.xlc.monkey.view.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import androidx.annotation.NonNull;

/**
 * @author:xlc
 * @date:2019/6/28
 * @descirbe:创建数据表
 * 面向对象：Article属于某个User
 */
@DatabaseTable(tableName = "tb_article")
public class Article {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    /**
     * canBeNull 表示不能为null
     * foregin 表示一个外键
     * columnName 列名
     */
    //添加foreignAutoRefresh，当调用queryForid时，可以使拿到的Article对象则直接携带了user
//    @DatabaseField(canBeNull = true,foreign = true,columnName = "user_id",foreignAutoRefresh = true)
    @DatabaseField(canBeNull = true,foreign = true,columnName = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NonNull
    @Override
    public String toString() {
        return "Article [id=" + id + ", title=" + title + ", user=" + user
                + "]";

    }
}
