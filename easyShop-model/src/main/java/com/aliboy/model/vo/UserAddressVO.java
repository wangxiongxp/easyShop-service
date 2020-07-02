package com.aliboy.model.vo;

import lombok.Data;

import javax.persistence.Column;

/**
 * 地址列表的VO
 */
@Data
public class UserAddressVO {

    /**
     * 地址主键id
     */
    private Integer id;

    /**
     * 关联用户id
     */
    private Integer userId;

    /**
     * 收件人姓名
     */
    private String receiver;

    /**
     * 收件人手机号
     */
    private String mobile;

    /**
     * 国家
     */
    private String country;

    /**
     * 国家
     */
    private String countryName;

    /**
     * 省份
     */
    private String province;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 城市
     */
    private String city;

    /**
     * 城市
     */
    private String cityName;

    /**
     * 区县
     */
    private String district;

    /**
     * 区县
     */
    private String districtName;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 是否默认地址
     */
    @Column(name = "is_default")
    private Boolean isDefault;

}
