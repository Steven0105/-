package com.example.androidmall.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidmall.bean.User;

import java.util.List;

@Dao
public interface UserDao {
    //新增用户
    @Insert
    void insert(User... users);

    //查询用户名是否可用
    @Query("select * from users where username=:username")
    List<User> findByName(String username);

    //登录验证
    @Query("select * from users where username=:username and password=:passowrd")
    User login(String username, String passowrd);
}
