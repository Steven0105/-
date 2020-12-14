package com.example.androidmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;

import com.example.androidmall.MyApplication;
import com.example.androidmall.R;
import com.example.androidmall.bean.User;
import com.example.androidmall.databinding.ActivityLoginBinding;
import com.example.androidmall.util.Global;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setTitle("登录");
        if ((Global.user = getLocalUser()) != null) {
            //如果记住密码了，直接登录
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                //登录
                String username = activityLoginBinding.etUserName.getText().toString();
                String password = activityLoginBinding.etPassword.getText().toString();
                new Thread(() -> {
                    User user = Global.myDB.userDao().login(username, password);
                    if (user == null) {
                        MyApplication.tip("用户名或密码错误");
                    } else {
                        Global.user = user;
                        if (activityLoginBinding.chkPwd.isChecked()) {
                            //记住密码
                            saveLocalUser(user);
                        }
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                }).start();
            }
            break;
            case R.id.btnReg: {
                //注册
                startActivity(new Intent(this, RegActivity.class));
            }
            break;
        }
    }

    //记住密码
    private void saveLocalUser(User user) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString("username", user.username).apply();
        sharedPreferences.edit().putString("password", user.password).apply();
    }

    //获取记住密码的信息
    private User getLocalUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        if (username.equals("") || password.equals("")) {
            return null;
        } else {
            User user = new User();
            user.password = password;
            user.username = username;
            return user;
        }
    }
}