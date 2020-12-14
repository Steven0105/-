package com.example.androidmall.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidmall.bean.Good;

import java.util.List;

@Dao
public interface GoodDao {
    //新增商品
    @Insert
    void insert(Good... goods);

    //查询所有商品，按时间倒序排列
    @Query("select * from goods order by time desc")
    List<Good> findAll();

    //查询商品
    @Query("select * from goods where uuid=:uuid")
    Good find(String uuid);

    //根据关键词查询商品，按时间倒序排列
    @Query("select * from goods where title like '%'|| :keywords|| '%' or description like '%'|| :keywords|| '%'")
    List<Good> findAll(String keywords);

    //删除所有商品
    @Query("delete from goods")
    void deleteAll();
}
