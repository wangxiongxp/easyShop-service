package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Feedback {
    @Id
    @Column(name = "msg_id")
    private Integer msgId;

    /**
     * 会员id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 会员名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * email
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 标题
     */
    @Column(name = "msg_title")
    private String msgTitle;

    /**
     * 类型
     */
    @Column(name = "msg_type")
    private Integer msgType;

    /**
     * 状态 1已处理 0未处理
     */
    @Column(name = "msg_status")
    private Boolean msgStatus;

    /**
     * 时间
     */
    @Column(name = "msg_time")
    private Integer msgTime;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 内容
     */
    @Column(name = "msg_content")
    private String msgContent;

}