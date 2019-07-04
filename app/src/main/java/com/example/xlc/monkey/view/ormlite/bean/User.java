package com.example.xlc.monkey.view.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

import androidx.annotation.NonNull;

/**
 * @author:xlc
 * @date:2019/6/28
 * @descirbe:创建数据库表
 */
@DatabaseTable(tableName = "tb_user")
public class User {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "name")
    private String name;


    public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
    }

    //关联一个集合
    @ForeignCollectionField
    private Collection<Article> articles;

    private User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name
                + "]";
    }
}
