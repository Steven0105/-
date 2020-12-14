package com.example.androidmall;

import android.app.Application;
import android.os.Handler;
import android.widget.Toast;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidmall.db.MyDB;
import com.example.androidmall.util.Global;
import com.example.androidmall.util.GoodUtil;

//主程序入口
public class MyApplication extends Application {
    private Handler handler = new Handler();
    public static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //初始化数据库
        Global.myDB = Room.databaseBuilder(this, MyDB.class, "database").build();
        //初始化商品
        new Thread(GoodUtil::initGood).start();
    }

    public void toast(String msg) {
        handler.post(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    //提示
    public static void tip(String msg) {
        myApplication.toast(msg);
    }
}
