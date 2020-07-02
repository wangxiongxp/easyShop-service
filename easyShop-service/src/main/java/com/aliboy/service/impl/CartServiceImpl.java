package com.aliboy.service.impl;

import com.aliboy.common.security.PrincipalUtils;
import com.aliboy.mapper.CartMapper;
import com.aliboy.mapper.GoodsMapper;
import com.aliboy.mapper.ProductMapper;
import com.aliboy.model.dto.ShopCartDto;
import com.aliboy.model.entity.Cart;
import com.aliboy.model.entity.Goods;
import com.aliboy.model.entity.Product;
import com.aliboy.service.CartService;
import com.aliboy.service.impl.center.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:29
 */

@Service
public class CartServiceImpl extends BaseService implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer queryGoodsCount(Integer userId) {

        Cart condition = new Cart();
        condition.setUserId(userId);
        return cartMapper.selectCount(condition);
    }

    @Override
    public List<Cart> queryCartList(Integer userId) {
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);

        return cartMapper.selectByExample(example);
    }

    @Override
    public Boolean updateCart(Cart cart) {
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", cart.getId());
        criteria.andEqualTo("userId", cart.getUserId());
        cartMapper.updateByExampleSelective(cart, example);

        return Boolean.TRUE;
    }

    @Override
    public Boolean addCart(ShopCartDto shopCartDto) {

        Cart queryCart = new Cart();
        queryCart.setUserId(PrincipalUtils.getUserIdInt());
        queryCart.setGoodsId(shopCartDto.getGoodsId());
        queryCart.setProductId(shopCartDto.getProductId());
        Cart oldCart = cartMapper.selectOne(queryCart);
        if(!ObjectUtils.isEmpty(oldCart)){
            Integer newNumber = oldCart.getNumber() + shopCartDto.getNumber();
            oldCart.setNumber(newNumber);
            cartMapper.updateByPrimaryKeySelective(oldCart);
            return Boolean.TRUE;
        }

        Goods goods = goodsMapper.selectByPrimaryKey(shopCartDto.getGoodsId());
        Product product = productMapper.selectByPrimaryKey(shopCartDto.getProductId());

        Cart cart = new Cart();
        cart.setUserId(PrincipalUtils.getUserIdInt());
        cart.setSessionId("");
        cart.setGoodsId(shopCartDto.getGoodsId());
        cart.setGoodsName(goods.getName());
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setListPicUrl(goods.getListPicUrl());
        cart.setProductId(shopCartDto.getProductId());
        cart.setMarketPrice(goods.getCounterPrice());
        cart.setRetailPrice(product.getRetailPrice());
        cart.setNumber(shopCartDto.getNumber());
        cart.setGoodsSpecificationIds(product.getGoodsSpecificationIds());
        cart.setGoodsSpecificationValues(product.getGoodsSpecificationValues());
        cart.setChecked(true);

        cartMapper.insert(cart);
        return Boolean.TRUE;
    }
}
