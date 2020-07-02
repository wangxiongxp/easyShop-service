package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Collect {
    @Id
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 类型0 商品
     */
    @Column(name = "type_id")
    private Integer typeId;

    /**
     * 对应商品id
     */
    @Column(name = "value_id")
    private Integer valueId;

    @Column(name = "add_time")
    private Integer addTime;

}