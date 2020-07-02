package com.aliboy.model.vo;

import com.aliboy.model.dto.SpecificationVO;
import com.aliboy.model.entity.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情VO
 */
@Data
public class GoodsInfoVO {

    /**
     *
     */
    private Goods info;

    /**
     *
     */
    private List<GoodsGallery> gallery;

    /**
     *
     */
    private List<AttributeVO> attribute;

    /**
     *
     */
    private List<GoodsIssue> issue;

    /**
     *
     */
    private List<SpecificationVO> specificationList;

    /**
     *
     */
    private List<Product> productList;

    /**
     *
     */
    private Brand brand;

    /**
     *
     */
    private Map<String, Object> comment;

    /**
     *
     */
    private Integer userHasCollect;

    public Map<String, Object> getComment() {
        Map<String, Object> map = new HashMap<>();
        map.put("count",0);
        map.put("data",null);
        return map;
    }


}
