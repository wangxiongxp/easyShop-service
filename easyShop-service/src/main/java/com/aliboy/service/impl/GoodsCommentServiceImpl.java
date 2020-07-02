package com.aliboy.service.impl;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.mapper.CommentPictureMapper;
import com.aliboy.mapper.GoodsCommentMapper;
import com.aliboy.model.entity.CommentPicture;
import com.aliboy.model.entity.GoodsComment;
import com.aliboy.model.vo.GoodsCommentVO;
import com.aliboy.service.GoodsCommentService;
import com.aliboy.service.impl.center.BaseService;
import com.aliboy.common.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:29
 */

@Service
public class GoodsCommentServiceImpl extends BaseService implements GoodsCommentService {

    @Autowired
    private GoodsCommentMapper goodsCommentMapper;

    @Autowired
    private CommentPictureMapper commentPictureMapper;

    @Override
    public PagedGridResult queryCommentList(QueryParams queryParams) {

        List<GoodsCommentVO> returnList = Lists.newArrayList();

        PageHelper.startPage(queryParams.getPageIndex(), queryParams.getPageSize());

        List<GoodsComment> list = goodsCommentMapper.queryListPage(convertQueryParams(queryParams));

        PagedGridResult<GoodsComment> pagedGridResult = setPagedGrid(list, queryParams.getPageIndex());

        pagedGridResult.getData().forEach(item->{
            List<CommentPicture> commentPictures = this.queryCommentPictureList(item.getId());
            GoodsCommentVO vo = new GoodsCommentVO();
            BeanUtils.copyProperties(item, vo);
            vo.setPicList(commentPictures);
            returnList.add(vo);
        });

        return PagedGridResult.of(returnList,pagedGridResult.getPagination());
    }

    @Override
    public List<CommentPicture> queryCommentPictureList(Integer commentId) {
        Example example = new Example(CommentPicture.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commentId", commentId);

        return commentPictureMapper.selectByExample(example);
    }
}
