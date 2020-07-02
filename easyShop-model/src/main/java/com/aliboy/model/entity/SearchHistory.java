package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "search_history")
public class SearchHistory {
    @Id
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 搜索来源 1PC、2H5、3小程序、4APP
     */
    private Integer channel;

    /**
     * 搜索时间
     */
    @Column(name = "add_time")
    private Integer addTime;

}