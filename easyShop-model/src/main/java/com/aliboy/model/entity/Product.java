package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class Product {
    @Id
    private Integer id;

    @Column(name = "goods_id")
    private Integer goodsId;

    @Column(name = "goods_specification_ids")
    private String goodsSpecificationIds;

    @Column(name = "goods_specification_values")
    private String goodsSpecificationValues;

    @Column(name = "goods_sn")
    private String goodsSn;

    @Column(name = "goods_number")
    private Integer goodsNumber;

    @Column(name = "retail_price")
    private BigDecimal retailPrice;

}