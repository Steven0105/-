package com.example.androidmall.db.convert;

import androidx.room.TypeConverter;

import com.example.androidmall.bean.Good;
import com.google.gson.Gson;

public class GoodConvert {
    @TypeConverter
    public static Good fromString(String value) {
        return new Gson().fromJson(value, Good.class);
    }

    @TypeConverter
    public static String fromArrayList(Good good) {
        Gson gson = new Gson();
        return gson.toJson(good);
    }
}
