package com.aliboy.model.vo;

import com.aliboy.model.entity.CommentPicture;
import com.aliboy.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用于展示商品评价的VO
 */
@Data
public class GoodsCommentVO {

    private Integer id;

    private Byte typeId;

    private Integer valueId;

    private String content;

    private Byte status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;

    private User userInfo;

    private List<CommentPicture> picList;

}
