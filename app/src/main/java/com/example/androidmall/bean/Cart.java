package com.example.androidmall.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.androidmall.db.convert.GoodConvert;

@Entity(tableName = "carts", primaryKeys = {"username", "good_uuid"})
@TypeConverters({GoodConvert.class})
public class Cart {
    @NonNull
    public String username;//用户名
    @NonNull
    public String good_uuid;//商品id
    public int count;//数量
    public Good good;//添加的商品
    public long time;//添加时间
    public boolean select;//是否选中
}
