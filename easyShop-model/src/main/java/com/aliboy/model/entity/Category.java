package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "category")
public class Category {
    @Id
    private Integer id;

    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    private Integer level;

    @Column(name = "front_name")
    private String frontName;

    @Column(name = "front_desc")
    private String frontDesc;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "wap_banner_url")
    private String wapBannerUrl;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "is_show")
    private Boolean isShow;

    @Column(name = "sort_order")
    private Integer sortOrder;

}