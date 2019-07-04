package com.example.xlc.monkey.view.ormlite.dao;

import android.content.Context;

import com.example.xlc.monkey.view.ormlite.DBHelper;
import com.example.xlc.monkey.view.ormlite.bean.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * @author:xlc
 * @date:2019/6/28
 * @descirbe:
 */
public class UserDao {

    private Dao<User, Integer> userDaoOpe;
    private DBHelper helper;

    public UserDao(Context context) {
        try {
            helper = DBHelper.getHelper(context);
            userDaoOpe = helper.getDao(User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加用户
     * @param user
     */
    public void add(User user) {
        try {
            userDaoOpe.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
