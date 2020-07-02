package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class Topic {

    @Id
    private Integer id;

    @Column(name = "topic_category_id")
    private Integer topicCategoryId;

    private String title;

    private String subtitle;

    private String content;

    @Column(name = "item_pic_url")
    private String itemPicUrl;

    @Column(name = "floor_price")
    private BigDecimal floorPrice;

    @Column(name = "read_count")
    private Integer readCount;

    @Column(name = "is_show")
    private Boolean isShow;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "scene_pic_url")
    private String scenePicUrl;

    @Column(name = "topic_template_id")
    private Integer topicTemplateId;

    @Column(name = "topic_tag_id")
    private Integer topicTagId;

    private String avatar;

}