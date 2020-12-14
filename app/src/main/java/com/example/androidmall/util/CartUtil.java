package com.example.androidmall.util;

import com.example.androidmall.bean.Cart;
import com.example.androidmall.bean.Good;

public class CartUtil {

    //添加购物车
    public static void addCart(Good good) {
        Cart cart = Global.myDB.cartDao().findMyGood(Global.user.username, good.uuid);
        if (cart == null) {
            //不存在，增加记录
            cart = new Cart();
            cart.count = 1;
            cart.good = good;
            cart.good_uuid = good.uuid;
            cart.time = System.currentTimeMillis();
            cart.username = Global.user.username;
            Global.myDB.cartDao().insert(cart);
        } else {
            //已存在，增加数量
            //超过99，禁止加入
            if (cart.count == 99) {
                return;
            }
            cart.count++;
            Global.myDB.cartDao().update(cart);
        }
    }

    //设置购物车商品数量
    public static void setCart(Good good, int count) {
        if (count == 0) {
            //设置数量为0，从购物车删除商品
            Global.myDB.cartDao().delete(Global.user.username, good.uuid);
            return;
        }
        //超过99，禁止加入
        if (count == 99) {
            return;
        }
        Cart cart = Global.myDB.cartDao().findMyGood(Global.user.username, good.uuid);
        if (cart == null) {
            //不存在，增加记录
            cart = new Cart();
            cart.count = count;
            cart.good = good;
            cart.good_uuid = good.uuid;
            cart.time = System.currentTimeMillis();
            cart.username = Global.user.username;
            Global.myDB.cartDao().insert(cart);
        } else {
            //已存在，增加数量
            cart.count = count;
            Global.myDB.cartDao().update(cart);
        }
    }
}
