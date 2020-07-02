package com.aliboy.api;

import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.security.PrincipalUtils;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.dto.ShopCartDto;
import com.aliboy.model.entity.Cart;
import com.aliboy.service.CartService;
import com.aliboy.service.GoodsService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 14:59
 */
@RestController
@RequestMapping("${api}/cart")
@Api(value = "购物车", tags = "140000--购物车相关接口")
public class CartController extends BaseController {

    @Autowired
    private CartService cartService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询购物车列表
     */
    @ApiOperation(value = "查询购物车列表", notes = "查询购物车列表", httpMethod = "GET")
    @GetMapping("/index")
    public ResultDto index() {
        Map<String, Object> map = Maps.newHashMap();
        Integer userId = PrincipalUtils.getUserIdInt();
        List<Cart> cartList = cartService.queryCartList(userId);
        map.put("cartList",cartList);

        Integer goodsCount = cartList.size();
        BigDecimal goodsAmount = new BigDecimal(0);
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0);

        for (Cart cart : cartList) {
            goodsAmount = goodsAmount.add(cart.getRetailPrice());
            if(cart.getChecked()){
                checkedGoodsCount ++ ;
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getRetailPrice());
            }
        }
        Map<String, Object> cartTotal = Maps.newHashMap();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        map.put("cartTotal", cartTotal);
        return getResultDto(map);
    }

    /**
     * 查询购物车数量
     */
    @ApiOperation(value = "查询购物车数量", notes = "查询购物车数量", httpMethod = "GET")
    @GetMapping("/goodscount")
    public ResultDto goodscount() {

        Integer userId = PrincipalUtils.getUserIdInt();
        Integer count = cartService.queryGoodsCount(userId);

        Map<String, Object> map = Maps.newHashMap();
        map.put("goodsCount",count);
        return getResultDto(map);
    }

    @ApiOperation(value = "修改商品勾选状态", notes = "修改商品勾选状态", httpMethod = "POST")
    @PostMapping("/checked")
    public ResultDto checked(@RequestParam Boolean isChecked, @RequestParam Integer id) {

        Integer userId = PrincipalUtils.getUserIdInt();
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUserId(userId);
        cart.setChecked(isChecked);
        cartService.updateCart(cart);

        return getResultDto();
    }

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public ResultDto add(@RequestBody ShopCartDto shopCartDto) {

        cartService.addCart(shopCartDto);

        // TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return getResultDto();
    }


//    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
//    @PostMapping("/del")
//    public IMOOCJSONResult del(
//            @RequestParam String userId,
//            @RequestParam String itemSpecId,
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//
//        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
//            return IMOOCJSONResult.errorMsg("参数不能为空");
//        }
//
//        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
//
//        return IMOOCJSONResult.ok();
//    }

}
