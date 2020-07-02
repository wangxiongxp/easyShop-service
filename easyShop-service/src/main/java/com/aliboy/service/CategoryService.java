package com.aliboy.service;

import com.aliboy.model.vo.CategoryItemVO;
import com.aliboy.model.entity.Category;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据父级分类id查询子分类信息
     * @param parentId
     * @return
     */
    List<Category> querySubCatList(Integer parentId);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Category findCategoryById(Integer id);

    /**
     * 首页分类带商品
     * @return
     */
    List<CategoryItemVO> queryHomeCategoryItemList();

}
