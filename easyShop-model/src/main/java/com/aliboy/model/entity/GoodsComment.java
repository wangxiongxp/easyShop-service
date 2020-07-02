package com.aliboy.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "goods_comment")
@Data
public class GoodsComment {
    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "value_id")
    private Integer valueId;

    private Integer star;

    private String content;

    @Column(name = "new_content")
    private String newContent;

    private Integer status;

    @Column(name = "add_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;

    @Column(name = "new_add_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date newAddTime;

}