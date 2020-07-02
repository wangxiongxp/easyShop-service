package com.aliboy.model.dto;

import lombok.Data;

/**
 * 用户新增或修改地址的BO
 */
@Data
public class UserAddressDto {

    private Integer id;

    private Integer userId;

    private String receiver;
    private String mobile;
    private String country;
    private String province;
    private String city;
    private String district;
    private String detail;

}
