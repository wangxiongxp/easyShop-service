package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "related_goods")
public class RelatedGoods {
    @Id
    private Integer id;

    @Column(name = "goods_id")
    private Integer goodsId;

    @Column(name = "related_goods_id")
    private Integer relatedGoodsId;

}