package com.example.androidmall.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidmall.bean.Cart;
import com.example.androidmall.bean.Good;
import com.example.androidmall.bean.User;
import com.example.androidmall.db.dao.CartDao;
import com.example.androidmall.db.dao.GoodDao;
import com.example.androidmall.db.dao.UserDao;

/**
 * 数据库操作类
 */
@Database(entities = {Good.class, User.class, Cart.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    public abstract GoodDao goodDao();

    public abstract UserDao userDao();

    public abstract CartDao cartDao();
}
