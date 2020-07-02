package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Area {
    /**
     * 地区id
     */
    @Id
    private String id;

    /**
     * 行政区域编码
     */
    private String code;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 上级地区
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 地理区域范围
     */
    private String path;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer ordinal;

}