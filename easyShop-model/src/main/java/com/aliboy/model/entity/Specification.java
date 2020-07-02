package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Specification {
    @Id
    private Integer id;

    private String name;

    @Column(name = "sort_order")
    private Integer sortOrder;

}