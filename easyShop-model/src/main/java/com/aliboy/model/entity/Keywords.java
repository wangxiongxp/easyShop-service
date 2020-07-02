package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Keywords {
    @Id
    private Integer id;

    private Integer type;

    private String keyword;

    /**
     * 关键词的跳转链接
     */
    @Column(name = "`scheme _url`")
    private String schemeUrl;

    @Column(name = "is_hot")
    private Boolean isHot;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "is_show")
    private Boolean isShow;

    @Column(name = "sort_order")
    private Integer sortOrder;

}