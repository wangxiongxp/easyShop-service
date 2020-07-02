package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "order_goods")
public class OrderGoods {
    @Id
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

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
     * 规格
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 数量
     */
    private Integer number;

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
     * 规格id
     */
    @Column(name = "goods_specification_ids")
    private String goodsSpecificationIds;

    /**
     * 规格values
     */
    @Column(name = "goods_specification_values")
    private String goodsSpecificationValues;

    /**
     * 图片链接
     */
    @Column(name = "list_pic_url")
    private String listPicUrl;

}