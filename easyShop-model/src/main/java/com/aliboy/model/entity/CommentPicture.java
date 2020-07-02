package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "comment_picture")
public class CommentPicture {
    @Id
    private Integer id;

    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "sort_order")
    private Integer sortOrder;

}