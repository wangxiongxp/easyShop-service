package com.aliboy.service;

import com.aliboy.model.dto.ShopCartDto;
import com.aliboy.model.entity.Cart;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface CartService {
    /**
     * 查询商品数量
     * @return
     */
    Integer queryGoodsCount(Integer userId);

    /**
     * 根据会员id查询购物车列表
     * @param userId
     * @return
     */
    List<Cart> queryCartList(Integer userId);

    /**
     * 修改
     * @param cart
     * @return
     */
    Boolean updateCart(Cart cart);

    /**
     * 添加购物车
     * @param shopCartDto
     * @return
     */
    Boolean addCart(ShopCartDto shopCartDto);

}
