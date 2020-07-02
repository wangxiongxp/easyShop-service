package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Ad {
    @Id
    private Integer id;

    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "media_type")
    private Integer mediaType;

    private String name;

    private String link;

    @Column(name = "image_url")
    private String imageUrl;

    private String content;

    private Boolean enabled;

    @Column(name = "end_time")
    private Integer endTime;

}