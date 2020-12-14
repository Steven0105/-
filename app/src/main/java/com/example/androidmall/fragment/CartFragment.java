package com.example.androidmall.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmall.MyApplication;
import com.example.androidmall.R;
import com.example.androidmall.activity.ViewActivity;
import com.example.androidmall.bean.Cart;
import com.example.androidmall.bean.Good;
import com.example.androidmall.databinding.CartItemBinding;
import com.example.androidmall.databinding.FragmentCartBinding;
import com.example.androidmall.db.MyDB;
import com.example.androidmall.util.CartUtil;
import com.example.androidmall.util.Global;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements View.OnClickListener {
    private FragmentCartBinding fragmentCartBinding;

    public CartFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<Cart> list = new ArrayList<>();

    //初始化购物车列表
    public void initCart() {
        new Thread(() -> {
            list = Global.myDB.cartDao().findMyGoods(Global.user.username);
            fragmentCartBinding.list.post(() -> {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                fragmentCartBinding.list.setLayoutManager(layoutManager);
                fragmentCartBinding.list.setAdapter(new Adapter());
                calc();
            });
        }).start();
    }

    //计算购物车金额等
    private void calc() {
        int selectCount = 0;
        BigDecimal bigDecimal = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).select) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).count).multiply(new BigDecimal(list.get(i).good.price)));
                selectCount++;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String value = decimalFormat.format(bigDecimal.floatValue());
        fragmentCartBinding.txtTotal.setText(value + "元");
        //全选按钮状态
        fragmentCartBinding.chkAll.setChecked(selectCount != 0 && selectCount == list.size());
        //删除按钮状态
        fragmentCartBinding.btnDelete.setEnabled(selectCount != 0);
        //结算按钮状态
        fragmentCartBinding.btnConfirm.setEnabled(selectCount != 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //全选监听事件
        fragmentCartBinding.chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).select = fragmentCartBinding.chkAll.isChecked();
                        Global.myDB.cartDao().update(list.get(i));
                    }
                    //刷新购物车
                    fragmentCartBinding.list.post(() -> {
                        initCart();
                    });
                }).start();

            }
        });
        //按钮监听事件
        fragmentCartBinding.btnConfirm.setOnClickListener(this);
        fragmentCartBinding.btnDelete.setOnClickListener(this);
        initCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete: {
                //删除商品
                new Thread(() -> {
                    for (Cart cart : list) {
                        if (cart.select) {
                            Global.myDB.cartDao().delete(Global.user.username, cart.good_uuid);
                        }
                    }
                    //刷新购物车
                    fragmentCartBinding.list.post(this::initCart);
                }).start();

            }
            break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initCart();
    }

    class Holder extends RecyclerView.ViewHolder {
        public CartItemBinding cartItemBinding;

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public Holder(@NonNull View itemView, CartItemBinding cartItemBinding) {
            super(itemView);
            this.cartItemBinding = cartItemBinding;
        }
    }

    //  购物车列表适配器
    class Adapter extends RecyclerView.Adapter<Holder> implements View.OnClickListener {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CartItemBinding cartItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.cart_item, parent, false);
            return new Holder(cartItemBinding.getRoot(), cartItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.cartItemBinding.title.setText(list.get(position).good.title);
            holder.cartItemBinding.price.setText(list.get(position).good.price + "元");
            holder.cartItemBinding.image.setImageResource(list.get(position).good.image);
            holder.cartItemBinding.etCount.setInputType(8194);
            holder.cartItemBinding.etCount.setText(list.get(position).count + "");
            //设置缓存
            holder.cartItemBinding.btnMinus.setClickable(true);
            holder.cartItemBinding.btnPlus.setClickable(true);
            holder.cartItemBinding.etCount.setTag(list.get(position));
            holder.cartItemBinding.btnMinus.setTag(holder.cartItemBinding);
            holder.cartItemBinding.chkSelct.setTag(holder.cartItemBinding);
            holder.cartItemBinding.btnPlus.setTag(holder.cartItemBinding);
            //设置监听事件
            holder.cartItemBinding.btnPlus.setOnClickListener(plus);
            holder.cartItemBinding.btnMinus.setOnClickListener(minus);
            holder.cartItemBinding.chkSelct.setOnClickListener(select);
            holder.cartItemBinding.chkSelct.setChecked(list.get(position).select);
            holder.cartItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onClick(View v) {
            int position = fragmentCartBinding.list.getChildAdapterPosition(v);
            //上下文菜单
            Good good = list.get(position).good;
            String[] item = new String[]{"查看", "删除"};
            new AlertDialog.Builder(getActivity()).setItems(item, (dialog, which) -> {
                if (which == 0) {
                    //查看
                    Intent intent = new Intent(getActivity(), ViewActivity.class);
                    intent.putExtra("uuid", good.uuid);
                    startActivity(intent);
                } else {
                    new Thread(() -> {
                        //删除
                        Global.myDB.cartDao().delete(Global.user.username, good.uuid);
                        MyApplication.tip("删除成功");
                        fragmentCartBinding.list.post(CartFragment.this::initCart);
                    }).start();
                }

            }).show();
        }
    }

    private final Plus plus = new Plus();//添加
    private final Minus minus = new Minus();//删减
    private final Select select = new Select();//选中商品

    //商品选中事件
    private class Select implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CartItemBinding cartItemBinding = (CartItemBinding) v.getTag();
            Cart cart = (Cart) cartItemBinding.etCount.getTag();
            cart.select = ((CheckBox) v).isChecked();
            new Thread(() -> {
                Global.myDB.cartDao().update(cart);
                //刷新购物车
                fragmentCartBinding.list.post(CartFragment.this::initCart);
            }).start();
        }
    }

    //添加购物车单机事件
    private class Plus implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CartItemBinding cartItemBinding = (CartItemBinding) v.getTag();
            Cart cart = (Cart) cartItemBinding.etCount.getTag();
            if (cart.count == 99) {
                return;
            } else {
                new Thread(() -> {
                    //添加购物车
                    CartUtil.addCart(cart.good);
                    //刷新购物车
                    fragmentCartBinding.list.post(CartFragment.this::initCart);
                }).start();
            }

        }
    }

    //更多单击事件
    private class Minus implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CartItemBinding cartItemBinding = (CartItemBinding) v.getTag();
            Cart cart = (Cart) cartItemBinding.etCount.getTag();
            if (cart.count != 1) {
                new Thread(() -> {
                    //添加购物车
                    CartUtil.setCart(cart.good, cart.count - 1);
                    //刷新购物车
                    fragmentCartBinding.list.post(CartFragment.this::initCart);
                }).start();
            }
        }
    }
}