package com.aliboy.model.vo;

import com.aliboy.model.entity.Category;

import java.util.List;

/**
 * 分类VO
 */
public class CategoryVO extends Category {

    // 子分类 list
    private List<Category> subCategoryList;

    public List<Category> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<Category> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
