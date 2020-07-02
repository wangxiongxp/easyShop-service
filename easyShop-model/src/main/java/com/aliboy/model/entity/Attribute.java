package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Attribute {
    @Id
    private Integer id;

    @Column(name = "attribute_category_id")
    private Integer attributeCategoryId;

    private String name;

    @Column(name = "input_type")
    private Boolean inputType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    private String value;

}