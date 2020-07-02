package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "goods")
public class Goods {
    @Id
    private Integer id;

    @Column(name = "cate_id_1")
    private Integer cateId1;

    @Column(name = "cate_id_2")
    private Integer cateId2;

    @Column(name = "goods_sn")
    private String goodsSn;

    private String name;

    private String keywords;

    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 商品主图
     */
    @Column(name = "primary_pic_url")
    private String primaryPicUrl;

    /**
     * 商品列表图
     */
    @Column(name = "list_pic_url")
    private String listPicUrl;

    @Column(name = "goods_brief")
    private String goodsBrief;

    @Column(name = "goods_desc")
    private String goodsDesc;

    @Column(name = "goods_number")
    private Integer goodsNumber;

    /**
     * 专柜价格
     */
    @Column(name = "counter_price")
    private BigDecimal counterPrice;

    /**
     * 附加价格
     */
    @Column(name = "extra_price")
    private BigDecimal extraPrice;

    /**
     * 零售价格
     */
    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    /**
     * 单位价格，单价
     */
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 商品单位
     */
    @Column(name = "goods_unit")
    private String goodsUnit;

    /**
     * APP专享价
     */
    @Column(name = "app_exclusive_price")
    private BigDecimal appExclusivePrice;

    @Column(name = "limited_number")
    private Integer limitedNumber;

    /**
     * 销售量
     */
    @Column(name = "sell_volume")
    private Integer sellVolume;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "is_hot")
    private Boolean isHot;

    /**
     * 是否是APP专属
     */
    @Column(name = "is_app_exclusive")
    private Boolean isAppExclusive;

    @Column(name = "is_on_sale")
    private Boolean isOnSale;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "add_time")
    private Integer addTime;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "attribute_category")
    private Integer attributeCategory;

    /**
     * 主sku　product_id
     */
    @Column(name = "primary_product_id")
    private Integer primaryProductId;

    @Column(name = "promotion_desc")
    private String promotionDesc;

    @Column(name = "promotion_tag")
    private String promotionTag;

}