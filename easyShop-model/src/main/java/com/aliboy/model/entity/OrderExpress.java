package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "order_express")
public class OrderExpress {
    @Id
    private Integer id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "shipper_id")
    private Integer shipperId;

    /**
     * 物流公司名称
     */
    @Column(name = "shipper_name")
    private String shipperName;

    /**
     * 物流公司代码
     */
    @Column(name = "shipper_code")
    private String shipperCode;

    /**
     * 快递单号
     */
    @Column(name = "logistic_code")
    private String logisticCode;

    /**
     * 物流跟踪信息
     */
    private String traces;

    @Column(name = "is_finish")
    private Boolean isFinish;

    /**
     * 总查询次数
     */
    @Column(name = "request_count")
    private Integer requestCount;

    /**
     * 最近一次向第三方查询物流信息时间
     */
    @Column(name = "request_time")
    private Integer requestTime;

    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Integer addTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Integer updateTime;

}