package com.aliboy.model.dto;

import com.aliboy.model.entity.GoodsSpecification;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-17 12:52
 */
@Data
public class SpecificationVO {

    private Integer specificationId;

    private String name;

    private List<GoodsSpecification> valueList;

}
