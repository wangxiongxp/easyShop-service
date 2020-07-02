package com.aliboy.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品的简单数据类型
 */
@Data
public class GoodsSimpleVO {

    private Integer id;

    private String name;

    private String listPicUrl;

    private BigDecimal retailPrice;

}
