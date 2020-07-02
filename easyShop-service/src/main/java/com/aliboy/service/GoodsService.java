package com.aliboy.service;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.model.vo.AttributeVO;
import com.aliboy.model.vo.GoodsInfoVO;
import com.aliboy.model.dto.GoodsSimpleVO;
import com.aliboy.model.dto.SpecificationVO;
import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.model.entity.Goods;
import com.aliboy.model.entity.GoodsGallery;
import com.aliboy.model.entity.GoodsIssue;
import com.aliboy.model.entity.Product;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface GoodsService {
    /**
     * 查询在线商品数量
     * @return
     */
    Integer getActiveGoodsCount();

    /**
     * 查询商品分页列表
     * @return
     */
    PagedGridResult queryGoodsList(QueryParams queryParams);

    /**
     * 查找商品详情
     * @param id
     * @return
     */
    GoodsInfoVO findGoodsInfoById(Integer id);

    /**
     * 根据商品ID查询详情
     * @param goodsId
     * @return
     */
    Goods queryGoodsById(Integer goodsId);

    /**
     * 根据商品id查询商品图片列表
     * @param goodsId
     * @return
     */
    List<GoodsGallery> queryGoodsGalleryList(Integer goodsId);

    /**
     * 根据商品id查询商品规格
     * @param goodsId
     * @return
     */
    List<SpecificationVO> queryGoodsSpecList(Integer goodsId);

    /**
     * 根据商品id查询商品规格
     * @param goodsId
     * @return
     */
    List<AttributeVO> queryGoodsAttributeList(Integer goodsId);

    /**
     * 根据商品id查询商品问题
     * @param goodsId
     * @return
     */
    List<GoodsIssue> queryGoodsIssueList(Integer goodsId);

    /**
     * 根据商品sku
     * @param goodsId
     * @return
     */
    List<Product> queryProductList(Integer goodsId);

    /**
     * 查询首页新品首发
     * @return
     */
    List<GoodsSimpleVO> queryNewGoodsList();

    /**
     * 查询首页人气推荐
     * @return
     */
    List<GoodsSimpleVO> queryHotGoodsList();

}
