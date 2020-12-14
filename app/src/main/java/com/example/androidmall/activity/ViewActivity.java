package com.example.androidmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.androidmall.MyApplication;
import com.example.androidmall.R;
import com.example.androidmall.bean.Good;
import com.example.androidmall.databinding.ActivityViewBinding;
import com.example.androidmall.util.CartUtil;
import com.example.androidmall.util.Global;

public class ViewActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private ActivityViewBinding activityViewBinding;
    private Good good;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_view);
        toolbar = activityViewBinding.toolbar;
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_view);//溢出菜单
        toolbar.setOnMenuItemClickListener(this);
        String uuid = getIntent().getStringExtra("uuid");
        new Thread(() -> {
            //查询商品
            good = Global.myDB.goodDao().find(uuid);
            if (good != null) {
                //填充页面
                activityViewBinding.title.post(() -> {
                    initView();
                });
            } else {
                finish();
                MyApplication.tip("商品不存在");
            }
        }).start();
    }

    private void initView() {
        toolbar.setTitle(good.title);
        activityViewBinding.description.setText(good.description);
        activityViewBinding.price.setText(good.price + "元");
        activityViewBinding.image.setImageResource(good.image);
        activityViewBinding.title.setText(good.title);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddCart: {
                //添加购物车
                new Thread(() -> {
                    //添加购物车
                    CartUtil.addCart(good);
                    MyApplication.tip("添加购物车成功");
                }).start();
            }
            break;
            case R.id.btnBack: {
                //返回
                finish();
            }
            break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_cart: {
                activityViewBinding.btnAddCart.performClick();
            }
            break;
            case R.id.menu_back: {
                activityViewBinding.btnBack.performClick();
            }
            break;
        }
        return false;
    }
}