package com.aliboy.service;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.model.entity.CommentPicture;
import com.aliboy.common.utils.PagedGridResult;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface GoodsCommentService {
    /**
     * 查询评论分页列表
     * @return
     */
    PagedGridResult queryCommentList(QueryParams queryParams);

    /**
     * 根据评论id查询图片列表
     * @param commentId
     * @return
     */
    List<CommentPicture> queryCommentPictureList(Integer commentId);
}
