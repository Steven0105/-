package com.example.androidmall.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goods")
public class Good {
    //ID
    @PrimaryKey
    @NonNull
    public String uuid;
    //商品标题
    public String title;
    //商品描述
    public String description;
    //商品价格
    public float price;
    //图片ID
    public int image;
    //发布时间
    public long time;
}
