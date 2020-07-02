package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "topic_category")
public class TopicCategory {
    @Id
    private Integer id;

    private String title;

    @Column(name = "pic_url")
    private String picUrl;

}