package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "goods_attribute")
public class GoodsAttribute {
    @Id
    private Integer id;

    @Column(name = "goods_id")
    private Integer goodsId;

    @Column(name = "attribute_id")
    private Integer attributeId;

    private String value;

}