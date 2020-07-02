package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "user_level")
public class UserLevel {
    @Id
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String description;

}