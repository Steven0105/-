package com.example.androidmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.androidmall.R;
import com.example.androidmall.databinding.ActivityMainBinding;
import com.example.androidmall.fragment.CartFragment;
import com.example.androidmall.fragment.MainFragment;
import com.example.androidmall.fragment.RecommendFragment;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private ActivityMainBinding activityMainBinding;
    private Toolbar toolbar;//标题栏
    private int index = 0;//当前页
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("推荐");//设置主标题
        toolbar.inflateMenu(R.menu.menu);//溢出菜单
        toolbar.setOnMenuItemClickListener(this);
        fragmentManager = getFragmentManager();//fragment管理器
        loadRecommend();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search: {
                //搜索框
                activityMainBinding.llSearch.setVisibility(View.VISIBLE);
                if (index != 1) {
                    loadList();
                }
            }
            break;
            case R.id.menu_logout: {
                //注销
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                sharedPreferences.edit().putString("username", "").apply();
                sharedPreferences.edit().putString("password", "").apply();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
            break;
            case R.id.menu_exix: {
                //关闭程序
                finish();
            }
        }
        return false;
    }

    //查询商品
    private void initGoodList(String keyWords) {
        if (index == 1) {
            mainFragment.initGoods(keyWords);
        }
    }

    private void cancelSearch() {
        activityMainBinding.etSearch.setText("");
        initGoodList(null);
        activityMainBinding.llSearch.setVisibility(View.GONE);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancelSearch: {
                //取消搜索
                cancelSearch();
            }
            break;
            case R.id.btnSearch: {
                //取消搜索
                String keyWords = activityMainBinding.etSearch.getText().toString();
                initGoodList(keyWords);
            }
            break;
            case R.id.rb1: {
                //推荐
                toolbar.setTitle("推荐");
                loadRecommend();
            }
            break;
            case R.id.rb2: {
                //商品列表
                toolbar.setTitle("商品列表");
                loadList();

            }
            break;
            case R.id.rb3: {
                //购物车
                toolbar.setTitle("购物车");
                loadCart();
            }
            break;
        }
    }

    private RecommendFragment recommendFragment;

    private MainFragment mainFragment;

    private CartFragment cartFragment;

    //商品列表
    private void loadList() {
        index = 1;
        mainFragment = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment, mainFragment).commit();
        activityMainBinding.rb2.setChecked(true);
    }

    //购物车
    private void loadCart() {
        cancelSearch();
        index = 2;
        cartFragment = new CartFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment, cartFragment).commit();
        activityMainBinding.rb3.setChecked(true);
    }

    //推荐商品
    private void loadRecommend() {
        cancelSearch();
        index = 0;
        recommendFragment = new RecommendFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment, recommendFragment).commit();
        activityMainBinding.rb1.setChecked(true);
    }
}