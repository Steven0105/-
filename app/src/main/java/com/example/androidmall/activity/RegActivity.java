package com.example.androidmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.androidmall.MyApplication;
import com.example.androidmall.R;
import com.example.androidmall.bean.User;
import com.example.androidmall.databinding.ActivityRegBinding;
import com.example.androidmall.db.MyDB;
import com.example.androidmall.util.Global;

public class RegActivity extends AppCompatActivity {
    private ActivityRegBinding activityRegBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegBinding = DataBindingUtil.setContentView(this, R.layout.activity_reg);
        setTitle("注册");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack: {
                //返回
                finish();
            }
            break;
            case R.id.btnReg: {
                //注册
                //获取输入的信息
                String username = activityRegBinding.etUserName.getText().toString();
                String password = activityRegBinding.etPassword.getText().toString();
                String password2 = activityRegBinding.etPassword2.getText().toString();
                //判断内容
                if (username.length() == 0 || password.length() == 0 || password2.length() == 0) {
                    MyApplication.tip("请将信息填写完整");
                    return;
                }
                if (!password.equals(password2)) {
                    MyApplication.tip("两次密码输入不一致");
                    return;
                }
                new Thread(() -> {
                    if (Global.myDB.userDao().findByName(username).size() > 0) {
                        MyApplication.tip("该用户名已存在，请换一个用户名");
                        return;
                    }
                    User user = new User();
                    user.password = password;
                    user.username = username;
                    //新增用户
                    Global.myDB.userDao().insert(user);
                    finish();
                    MyApplication.tip("注册成功");
                }).start();
            }
            break;
        }
    }
}