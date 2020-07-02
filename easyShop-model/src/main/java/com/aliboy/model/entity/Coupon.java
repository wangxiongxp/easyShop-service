package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class Coupon {
    @Id
    private Integer id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 固定金额
     */
    private BigDecimal money;

    /**
     * 发送类型
     */
    @Column(name = "send_type")
    private Integer sendType;

    /**
     * 最小金额
     */
    @Column(name = "min_amount")
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    /**
     * 发送开始时间
     */
    @Column(name = "send_start_date")
    private Integer sendStartDate;

    /**
     * 发送结束时间
     */
    @Column(name = "send_end_date")
    private Integer sendEndDate;

    /**
     * 使用开始时间
     */
    @Column(name = "use_start_date")
    private Integer useStartDate;

    /**
     * 使用结束时间
     */
    @Column(name = "use_end_date")
    private Integer useEndDate;

    /**
     * 最小商品金额限制
     */
    @Column(name = "min_goods_amount")
    private BigDecimal minGoodsAmount;

}