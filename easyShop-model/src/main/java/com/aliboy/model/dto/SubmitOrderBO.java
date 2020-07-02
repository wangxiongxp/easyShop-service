package com.aliboy.model.dto;

import lombok.Data;

/**
 * 用于创建订单的BO对象
 */
@Data
public class SubmitOrderBO {

    /**
     * 会员id
     */
    private Integer userId;

    /**
     * 规格，','号隔开
     */
    private String productIds;

    /**
     * 优惠券id
     */
    private Integer couponId;

    /**
     * 地址
     */
    private String addressId;

    private Integer payMethod;

    /**
     * 留言
     */
    private String remark;

}
