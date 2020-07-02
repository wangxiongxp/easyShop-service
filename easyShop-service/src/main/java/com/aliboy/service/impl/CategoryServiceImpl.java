package com.aliboy.service.impl;

import com.aliboy.mapper.CategoryMapper;
import com.aliboy.mapper.GoodsMapper;
import com.aliboy.model.vo.CategoryItemVO;
import com.aliboy.model.dto.GoodsSimpleVO;
import com.aliboy.model.entity.Category;
import com.aliboy.model.entity.Goods;
import com.aliboy.service.CategoryService;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:29
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("level", 1);

        List<Category> result =  categoryMapper.selectByExample(example);

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> querySubCatList(Integer parentId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", parentId);

        List<Category> result =  categoryMapper.selectByExample(example);
        return result;
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CategoryItemVO> queryHomeCategoryItemList() {
        List<CategoryItemVO> returnData = Lists.newArrayList();

        List<Category> categoryList = queryAllRootLevelCat();
        categoryList.forEach(item->{
            CategoryItemVO vo = new CategoryItemVO();
            vo.setId(item.getId());
            vo.setName(item.getName());

            Example example = new Example(Goods.class);
            example.setOrderByClause(" add_time desc ");
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("cateId1", item.getId().toString());

            List<Goods> goodsList = goodsMapper.selectByExampleAndRowBounds(example, new RowBounds(1, 7));
            goodsList.forEach(goods->{
                GoodsSimpleVO goodsSimpleVO = new GoodsSimpleVO();
                BeanUtils.copyProperties(goods, goodsSimpleVO);
                vo.getGoodsList().add(goodsSimpleVO);
            });

            returnData.add(vo);
        });

        return returnData;
    }
}
