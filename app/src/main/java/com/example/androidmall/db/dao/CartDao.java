package com.example.androidmall.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidmall.bean.Cart;

import java.util.List;

@Dao
public interface CartDao {
    //新增购物车记录
    @Insert
    void insert(Cart... cart);

    //查找我的购物车列表
    @Query("select * from carts where username=:username order by time desc")
    List<Cart> findMyGoods(String username);

    //查询此商品是否加过购物车
    @Query("select * from carts where username=:username and good_uuid=:good_uuid")
    Cart findMyGood(String username, String good_uuid);

    //删除购物车记录
    @Query("delete from carts where username=:username")
    void delete(String username);

    //删除购物车某个商品
    @Query("delete from carts where username=:username and good_uuid=:good_id")
    void delete(String username, String good_id);

    //更新购物车记录
    @Update
    void update(Cart cart);
}
