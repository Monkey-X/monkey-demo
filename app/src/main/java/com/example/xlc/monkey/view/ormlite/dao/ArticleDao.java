package com.example.xlc.monkey.view.ormlite.dao;

import android.content.Context;

import com.example.xlc.monkey.view.ormlite.DBHelper;
import com.example.xlc.monkey.view.ormlite.bean.Article;
import com.example.xlc.monkey.view.ormlite.bean.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author:xlc
 * @date:2019/6/28
 * @descirbe:
 */
public class ArticleDao {

    private Dao<Article,Integer> articleDaoOpe;
    private DBHelper helper;

    public ArticleDao(Context context){
        try {
            helper =  DBHelper.getHelper(context);
            articleDaoOpe = helper.getDao(Article.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个Article
     * @param article
     */
    public void add(Article article){
        try {
            articleDaoOpe.create(article);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id得到一个article
     * @param id
     * @return
     */
    public Article getArticleWithUser(int id){
        Article article = null;
        try {
           article =  articleDaoOpe.queryForId(id);
           //能够拿到Article对象，且内部的user属性直接赋值
           helper.getDao(User.class).refresh(article.getUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }


    /**
     * 通过id得到一篇文章
     * @param id
     * @return
     */
    public Article get(int id){
        Article article =null;

        try {
            article = articleDaoOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    /**
     * 通过userId获取所有的文章
     * @param userId
     * @return
     */
    public List <Article> listByUserId(int userId){
        try {
            return articleDaoOpe.queryBuilder().where().eq("user_id",userId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
