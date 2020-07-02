package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "ad_position")
public class AdPosition {
    @Id
    private Integer id;

    private String code;

    private String name;

    private Integer width;

    private Integer height;

    private String desc;

}