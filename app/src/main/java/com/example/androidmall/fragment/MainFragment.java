package com.example.androidmall.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmall.MyApplication;
import com.example.androidmall.R;
import com.example.androidmall.activity.ViewActivity;
import com.example.androidmall.adapter.GoodAdapter;
import com.example.androidmall.bean.Cart;
import com.example.androidmall.bean.Good;
import com.example.androidmall.databinding.FragmentMainBinding;
import com.example.androidmall.databinding.GoodItemBinding;
import com.example.androidmall.util.CartUtil;
import com.example.androidmall.util.Global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainFragment extends Fragment {
    private FragmentMainBinding fragmentMainBinding;

    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<Good> list = new ArrayList<>();

    //初始化商品列表
    public void initGoods(String keyWords) {
        new Thread(() -> {
            if (keyWords == null) {
                list = Global.myDB.goodDao().findAll();
            } else {
                list = Global.myDB.goodDao().findAll(keyWords);
            }
            fragmentMainBinding.list.post(() -> {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                fragmentMainBinding.list.setLayoutManager(layoutManager);
                fragmentMainBinding.list.setAdapter(new Adapter());
            });
        }).start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGoods(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return fragmentMainBinding.getRoot();
    }

    class Holder extends RecyclerView.ViewHolder {
        public GoodItemBinding goodItemBinding;

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public Holder(@NonNull View itemView, GoodItemBinding goodItemBinding) {
            super(itemView);
            this.goodItemBinding = goodItemBinding;
        }
    }

    //  商品列表适配器
    class Adapter extends RecyclerView.Adapter<Holder> implements View.OnClickListener {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GoodItemBinding goodItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.good_item, parent, false);
            return new Holder(goodItemBinding.getRoot(), goodItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.goodItemBinding.description.setText(list.get(position).description);
            holder.goodItemBinding.title.setText(list.get(position).title);
            holder.goodItemBinding.price.setText(list.get(position).price + "元");
            holder.goodItemBinding.image.setImageResource(list.get(position).image);
            //设置缓存
            holder.goodItemBinding.addCard.setTag(list.get(position));
            holder.goodItemBinding.more.setTag(list.get(position));
            //设置监听事件
            holder.goodItemBinding.addCard.setOnClickListener(addCart);
            holder.goodItemBinding.more.setOnClickListener(more);
            holder.goodItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onClick(View v) {
            int position = fragmentMainBinding.list.getChildAdapterPosition(v);
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
            String[] item = new String[]{"查看", "加入购物车"};
            new AlertDialog.Builder(getActivity()).setItems(item, (dialog, which) -> {
                if (which == 0) {
                    //查看
                    Intent intent = new Intent(getActivity(), ViewActivity.class);
                    intent.putExtra("uuid", good.uuid);
                    startActivity(intent);
                } else {
                    new Thread(() -> {
                        //添加购物车
                        CartUtil.addCart(good);
                        MyApplication.tip("添加购物车成功");
                    }).start();
                }

            }).show();
        }
    }
}