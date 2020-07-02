package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class Brand {
    @Id
    private Integer id;

    private String name;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "list_pic_url")
    private String listPicUrl;

    @Column(name = "app_list_pic_url")
    private String appListPicUrl;

    @Column(name = "simple_desc")
    private String simpleDesc;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_show")
    private Boolean isShow;

    @Column(name = "floor_price")
    private BigDecimal floorPrice;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "new_pic_url")
    private String newPicUrl;

    @Column(name = "new_sort_order")
    private Integer newSortOrder;

}