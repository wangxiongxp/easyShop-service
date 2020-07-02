package com.aliboy.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 我是收藏数据类型
 */
@Data
public class CollectInfoVO {

    private Integer id;

    private Integer userId;

    private Integer typeId;

    private Integer valueId;

    private Integer addTime;

    private String name;

    private String goodsBrief;

    private String listPicUrl;

    private BigDecimal retailPrice;

}
