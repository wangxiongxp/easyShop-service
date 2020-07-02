package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class Cart {
    @Id
    private Integer id;

    /**
     * 会员id
     */
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "session_id")
    private String sessionId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 商品编码
     */
    @Column(name = "goods_sn")
    private String goodsSn;

    /**
     * 商品列表图片
     */
    @Column(name = "list_pic_url")
    private String listPicUrl;

    /**
     * 商品规格
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 市场价格
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 零售价格
     */
    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    /**
     * 数量
     */
    private Integer number;

    /**
     * product表对应的goods_specification_ids
     */
    @Column(name = "goods_specification_ids")
    private String goodsSpecificationIds;

    /**
     * 规格属性组成的字符串，用来显示用
     */
    @Column(name = "goods_specification_values")
    private String goodsSpecificationValues;

    /**
     * 生效
     */
    private Boolean checked;

}