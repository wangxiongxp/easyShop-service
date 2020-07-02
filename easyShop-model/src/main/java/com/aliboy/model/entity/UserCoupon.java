package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "user_coupon")
public class UserCoupon {
    @Id
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 优惠券id
     */
    @Column(name = "coupon_id")
    private Integer couponId;

    /**
     * 优惠券编码
     */
    @Column(name = "coupon_number")
    private String couponNumber;

    @Column(name = "used_time")
    private Integer usedTime;

    @Column(name = "order_id")
    private Integer orderId;

}