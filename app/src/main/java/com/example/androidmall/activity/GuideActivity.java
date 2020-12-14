package com.example.androidmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.androidmall.R;
import com.example.androidmall.databinding.ActivityGuideBinding;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

//引导页
public class GuideActivity extends AppCompatActivity {
    private ActivityGuideBinding activityGuideBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGuideBinding = DataBindingUtil.setContentView(this, R.layout.activity_guide);
        if (PreferenceManager.getDefaultSharedPreferences(this).getString("guide", "").equals("1")) {
            //引导过了，直接转入登陆页面
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            //banner
            List<Integer> banners = new ArrayList<>();
            banners.add(R.drawable.guide0);
            banners.add(R.drawable.guide1);
            banners.add(R.drawable.guide2);
            activityGuideBinding.banner.setAdapter(new BannerImageAdapter<Integer>(banners) {
                @Override
                public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                    //图片加载自己实现
                    holder.imageView.setImageResource(data);
                }
            }).setIndicator(new CircleIndicator(this));
        }
    }

    public void onClick(View v) {
        save();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void save() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("guide", "1").apply();
    }
}