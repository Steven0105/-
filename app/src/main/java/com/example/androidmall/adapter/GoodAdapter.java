package com.example.androidmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.androidmall.R;
import com.example.androidmall.bean.Good;
import com.example.androidmall.databinding.GoodItemBinding;

import java.util.List;

public class GoodAdapter extends BaseAdapter {
    private List<Good> list;
    private Context context;

    public GoodAdapter(Context context, List<Good> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodItemBinding goodItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.good_item, null, false);
        goodItemBinding.description.setText(list.get(position).description);
        goodItemBinding.price.setText(list.get(position).price + "");
        goodItemBinding.title.setText(list.get(position).title);
        goodItemBinding.image.setImageResource(list.get(position).image);
        return goodItemBinding.getRoot();
    }
}
