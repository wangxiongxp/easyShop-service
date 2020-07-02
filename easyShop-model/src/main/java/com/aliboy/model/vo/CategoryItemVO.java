package com.aliboy.model.vo;

import com.aliboy.model.dto.GoodsSimpleVO;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 分类VO
 */
@Data
public class CategoryItemVO {

    private Integer id;

    private String name;

    private List<GoodsSimpleVO> goodsList = Lists.newArrayList();

}
