package com.example.xlc.monkey.view.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.xlc.monkey.view.ormlite.bean.Article;
import com.example.xlc.monkey.view.ormlite.bean.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:xlc
 * @date:2019/6/26
 * @descirbe:使用Orealm创建数据库
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "ormlite_text.db";//默认是在data/data/包名/databases/路径下
    private static final String DB_PATH= Environment.getExternalStorageDirectory()+"/text/"+DB_NAME;//指定路径
    private static final int DB_VERSION=1;

    private Map<String,Dao> daos = new HashMap<String,Dao>();

    private static  DBHelper instance;

    private DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    /**
     * 创建单例
     * @param context
     * @return
     */
    public static  synchronized DBHelper getHelper(Context context){
        Context applicationContext = context.getApplicationContext();
        if (instance ==null) {
            synchronized (DBHelper.class){
                if (instance == null) {
                    instance = new DBHelper(applicationContext);
                }
            }
        }

        return instance;
    }

    /**
     * 对每个bean创建一个XXXDao来处理当前bean的数据库操作，与数据库操作的对象，通过getDao获取
     * @param clazz
     * @return
     * @throws SQLException
     * 是一个泛型方法，根据传入的class对象创建Dao，Map保持所有的Dao对象，第一次获取时采取调用底层
     */
    @Override
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao ==null) {
            dao = super.getDao(clazz);
            daos.put(className,dao);
        }

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        //创建数据表
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Article.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        //整表的删除和创建
        try {
            TableUtils.dropTable(connectionSource,User.class,true);
            TableUtils.dropTable(connectionSource,Article.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
