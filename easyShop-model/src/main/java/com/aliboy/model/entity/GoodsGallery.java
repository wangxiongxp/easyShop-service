package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "goods_gallery")
public class GoodsGallery {
    @Id
    private Integer id;

    @Column(name = "goods_id")
    private Integer goodsId;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "img_desc")
    private String imgDesc;

    @Column(name = "sort_order")
    private Integer sortOrder;

}