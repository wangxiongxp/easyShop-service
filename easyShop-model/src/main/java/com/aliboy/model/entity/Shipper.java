package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Shipper {
    @Id
    private Integer id;

    /**
     * 快递公司名称
     */
    private String name;

    /**
     * 快递公司代码
     */
    private String code;

    /**
     * 排序
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

}