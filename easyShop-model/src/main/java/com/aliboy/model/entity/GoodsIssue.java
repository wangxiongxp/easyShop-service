package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "goods_issue")
public class GoodsIssue {
    @Id
    private Integer id;

    private String question;

    private String answer;

    @Column(name = "goods_id")
    private Integer goodsId;

}