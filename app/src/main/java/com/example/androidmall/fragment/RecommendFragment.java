package com.example.androidmall.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidmall.MyApplication;
import com.example.androidmall.R;
import com.example.androidmall.activity.ViewActivity;
import com.example.androidmall.bean.Good;
import com.example.androidmall.databinding.FragmentRecommendBinding;
import com.example.androidmall.databinding.GoodItemBinding;
import com.example.androidmall.databinding.RecommendGoodItemBinding;
import com.example.androidmall.util.CartUtil;
import com.example.androidmall.util.Global;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RecommendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentRecommendBinding fragmentRecommendBinding;

    public RecommendFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<Good> list = new ArrayList<>();

    //初始化商品列表
    public void initGoods() {
        new Thread(() -> {
            list = Global.myDB.goodDao().findAll();//所有商品
            Collections.shuffle(list);//随机打乱
            fragmentRecommendBinding.list.post(() -> {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                fragmentRecommendBinding.list.setLayoutManager(layoutManager);
                fragmentRecommendBinding.list.setAdapter(new Adapter());
                fragmentRecommendBinding.swipeRefreshLayout.setRefreshing(false);
                initBanner();
            });
        }).start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //下拉刷新
        fragmentRecommendBinding.swipeRefreshLayout.setOnRefreshListener(this);
        initGoods();
    }

    private void initBanner() {
        //banner
        List<Integer> banners = new ArrayList<>();
        banners.add(R.drawable.banner1);
        banners.add(R.drawable.banner2);
        banners.add(R.drawable.banner3);
        fragmentRecommendBinding.banner.setAdapter(new BannerImageAdapter<Integer>(banners) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                //图片加载自己实现
                holder.imageView.setImageResource(data);
            }
        }).setIndicator(new CircleIndicator(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRecommendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend, container, false);
        return fragmentRecommendBinding.getRoot();
    }

    @Override
    public void onRefresh() {
        fragmentRecommendBinding.swipeRefreshLayout.setRefreshing(true);
        initGoods();
    }

    class Holder extends RecyclerView.ViewHolder {
        public RecommendGoodItemBinding recommendGoodItemBinding;

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public Holder(@NonNull View itemView, RecommendGoodItemBinding recommendGoodItemBinding) {
            super(itemView);
            this.recommendGoodItemBinding = recommendGoodItemBinding;
            ViewGroup.LayoutParams params = recommendGoodItemBinding.getRoot().getLayoutParams();
            params.height = params.height + new Random().nextInt(200);
            recommendGoodItemBinding.getRoot().setLayoutParams(params);
        }
    }

    //  推荐商品列表适配器，瀑布流
    class Adapter extends RecyclerView.Adapter<Holder> implements View.OnClickListener {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecommendGoodItemBinding goodItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.recommend_good_item, parent, false);
            return new Holder(goodItemBinding.getRoot(), goodItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.recommendGoodItemBinding.description.setText(list.get(position).description);
            holder.recommendGoodItemBinding.title.setText(list.get(position).title);
            holder.recommendGoodItemBinding.price.setText(list.get(position).price + "元");
            holder.recommendGoodItemBinding.image.setImageResource(list.get(position).image);
            //设置缓存
            holder.recommendGoodItemBinding.addCard.setTag(list.get(position));
            holder.recommendGoodItemBinding.more.setTag(list.get(position));
            //设置监听事件
            holder.recommendGoodItemBinding.addCard.setOnClickListener(addCart);
            holder.recommendGoodItemBinding.more.setOnClickListener(more);
            holder.recommendGoodItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onClick(View v) {
            int position = fragmentRecommendBinding.list.getChildAdapterPosition(v);
            //查看
            Intent intent = new Intent(getActivity(), ViewActivity.class);
            intent.putExtra("uuid", list.get(position).uuid);
            startActivity(intent);
        }
    }

    private AddCart addCart = new AddCart();//添加购物车事件
    private More more = new More();//更多事件

    //添加购物车单机事件
    private class AddCart implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Good good = (Good) v.getTag();
            new Thread(() -> {
                //添加购物车
                CartUtil.addCart(good);
                MyApplication.tip("添加购物车成功");
            }).start();
        }
    }

    //更多单击事件
    private class More implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Good good = (Good) v.getTag();
        }
    }
}