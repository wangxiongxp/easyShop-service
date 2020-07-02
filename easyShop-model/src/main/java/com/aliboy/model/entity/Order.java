package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    @Id
    private String id;

    /**
     * 订单号
     */
    @Column(name = "order_sn")
    private String orderSn;

    /**
     * 会员id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 订单状态 10待付款,20已付款待发货,30已发货待收货,40交易成功,50交易关闭
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 物流状态
     */
    @Column(name = "shipping_status")
    private Boolean shippingStatus;

    /**
     * 支付状态
     */
    @Column(name = "pay_status")
    private Boolean payStatus;

    /**
     * 物流状态
     */
    @Column(name = "comment_status")
    private Boolean commentStatus;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 详细地址
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收件人
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 联系电话
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 支付方式
     */
    @Column(name = "pay_id")
    private Integer payId;

    /**
     * 支付名称
     */
    @Column(name = "pay_name")
    private String payName;

    /**
     * 运费
     */
    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    /**
     * 商品总价
     */
    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    /**
     * 订单总价，一般跟商品总价相同，下单后可以修改此价格
     */
    @Column(name = "order_price")
    private BigDecimal orderPrice;

    /**
     * 实际支付金额，订单总价减去优惠的金额
     */
    @Column(name = "actual_price")
    private BigDecimal actualPrice;

    /**
     * 订单创建时间;对应[10:待付款]状态
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 支付成功时间;对应[20:已付款，待发货]状态
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 发货时间;对应[30：已发货，待收货]状态
     */
    @Column(name = "deliver_time")
    private Date deliverTime;

    /**
     * 交易成功时间;对应[40：交易成功]状态
     */
    @Column(name = "confirm_time")
    private Date confirmTime;

    /**
     * 交易关闭时间;对应[50：交易关闭]状态
     */
    @Column(name = "close_time")
    private Date closeTime;

    /**
     * 使用优惠券id
     */
    @Column(name = "coupon_id")
    private Integer couponId;

    /**
     * 使用优惠券金额
     */
    @Column(name = "coupon_price")
    private BigDecimal couponPrice;

    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 备注
     */
    private String remark;

}