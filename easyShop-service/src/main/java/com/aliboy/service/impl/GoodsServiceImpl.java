package com.aliboy.service.impl;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.mapper.*;
import com.aliboy.model.vo.AttributeVO;
import com.aliboy.model.vo.GoodsInfoVO;
import com.aliboy.model.dto.GoodsSimpleVO;
import com.aliboy.model.dto.SpecificationVO;
import com.aliboy.model.entity.*;
import com.aliboy.service.BrandService;
import com.aliboy.service.GoodsService;
import com.aliboy.service.impl.center.BaseService;
import com.aliboy.common.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:29
 */

@Service
public class GoodsServiceImpl extends BaseService implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsGalleryMapper goodsGalleryMapper;

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    private GoodsIssueMapper goodsIssueMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AttributeMapper attributeMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private BrandService brandService;

    @Override
    public Integer getActiveGoodsCount() {

        Goods condition = new Goods();
        condition.setIsDelete(false);
        return goodsMapper.selectCount(condition);
    }

    @Override
    public PagedGridResult queryGoodsList(QueryParams queryParams) {
        List<GoodsSimpleVO> returnList = Lists.newArrayList();

        PageHelper.startPage(queryParams.getPageIndex(), queryParams.getPageSize());

        List<Goods> list = goodsMapper.queryListPage(convertQueryParams(queryParams));

        PagedGridResult<Goods> pagedGridResult = setPagedGrid(list, queryParams.getPageIndex());
        pagedGridResult.getData().forEach(item->{
            GoodsSimpleVO vo = new GoodsSimpleVO();
            BeanUtils.copyProperties(item, vo);
            returnList.add(vo);
        });

        return PagedGridResult.of(returnList,pagedGridResult.getPagination());
    }

    @Override
    public GoodsInfoVO findGoodsInfoById(Integer id) {
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();

        Goods goods = queryGoodsById(id);
        goodsInfoVO.setInfo(goods);

        goodsInfoVO.setGallery(queryGoodsGalleryList(id));
        goodsInfoVO.setAttribute(queryGoodsAttributeList(id));
        goodsInfoVO.setSpecificationList(queryGoodsSpecList(id));
        goodsInfoVO.setIssue(queryGoodsIssueList(id));
        goodsInfoVO.setBrand(brandService.findBrandById(goods.getBrandId()));
        goodsInfoVO.setUserHasCollect(0);
        goodsInfoVO.setProductList(queryProductList(id));

        return goodsInfoVO;
    }


    @Override
    public Goods queryGoodsById(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public List<GoodsGallery> queryGoodsGalleryList(Integer goodsId) {
        Example goodsGalleryExp = new Example(GoodsGallery.class);
        Example.Criteria criteria = goodsGalleryExp.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);

        return goodsGalleryMapper.selectByExample(goodsGalleryExp);
    }

    @Override
    public List<SpecificationVO> queryGoodsSpecList(Integer goodsId) {
        List<SpecificationVO> returnList = Lists.newArrayList();

        Example goodsSpecExp = new Example(GoodsSpecification.class);
        Example.Criteria criteria = goodsSpecExp.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecExp);
        List<Integer> specIdList = goodsSpecifications.stream()
                .collect(Collectors.groupingBy(GoodsSpecification::getSpecificationId))
                .keySet().stream().collect(Collectors.toList());

        specIdList.forEach(item->{
            SpecificationVO vo = new SpecificationVO();
            vo.setSpecificationId(item);
            vo.setName(specificationMapper.selectByPrimaryKey(item).getName());
            List<GoodsSpecification> specList = goodsSpecifications.stream()
                    .filter(ite ->ite.getSpecificationId().equals(item))
                    .collect(Collectors.toList());
            vo.setValueList(specList);
            returnList.add(vo);
        });
        return returnList;
    }

    @Override
    public List<AttributeVO> queryGoodsAttributeList(Integer goodsId) {
        List<AttributeVO> returnList = Lists.newArrayList();

        Example goodsAttributeExp = new Example(GoodsAttribute.class);
        Example.Criteria criteria = goodsAttributeExp.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExp);
        goodsAttributes.forEach(item->{
            AttributeVO vo = new AttributeVO();

            Attribute attribute = attributeMapper.selectByPrimaryKey(item.getAttributeId());
            vo.setName(attribute.getName());
            vo.setValue(item.getValue());
            returnList.add(vo);
        });

        return returnList;
    }

    @Override
    public List<GoodsIssue> queryGoodsIssueList(Integer goodsId) {
        Example goodsIssueExp = new Example(GoodsIssue.class);
        Example.Criteria criteria = goodsIssueExp.createCriteria();
//        criteria.andEqualTo("goodsId", goodsId);

        return goodsIssueMapper.selectByExample(goodsIssueExp);
    }

    @Override
    public List<Product> queryProductList(Integer goodsId) {
        Example productExp = new Example(Product.class);
        Example.Criteria criteria = productExp.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);

        return productMapper.selectByExample(productExp);
    }

    @Override
    public List<GoodsSimpleVO> queryNewGoodsList() {

        List<GoodsSimpleVO> returnList = Lists.newArrayList();

        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isNew", true);
        List<Goods> goods = goodsMapper.selectByExampleAndRowBounds(example, new RowBounds(1,8));

        goods.forEach(item->{
            GoodsSimpleVO vo = new GoodsSimpleVO();
            BeanUtils.copyProperties(item, vo);
            returnList.add(vo);
        });

        return returnList;
    }

    @Override
    public List<GoodsSimpleVO> queryHotGoodsList() {
        List<GoodsSimpleVO> returnList = Lists.newArrayList();

        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isHot", true);
        List<Goods> goods = goodsMapper.selectByExampleAndRowBounds(example, new RowBounds(1,8));

        goods.forEach(item->{
            GoodsSimpleVO vo = new GoodsSimpleVO();
            BeanUtils.copyProperties(item, vo);
            returnList.add(vo);
        });

        return returnList;
    }
}
