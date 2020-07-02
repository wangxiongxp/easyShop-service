package com.aliboy.service;

import com.aliboy.model.entity.UserAddress;
import com.aliboy.model.dto.UserAddressDto;
import com.aliboy.model.vo.UserAddressVO;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-04 10:39
 */
public interface AddressService {
    /**
     * 根据用户id查询用户的收货地址列表
     * @param userId
     * @return
     */
    List<UserAddressVO> queryAllByUserId(Integer userId);

    /**
     * 用户新增地址
     * @param userAddressDto
     */
    Boolean addNewUserAddress(UserAddressDto userAddressDto);

    /**
     * 用户修改地址
     * @param userAddressDto
     */
    Boolean updateUserAddress(UserAddressDto userAddressDto);

    /**
     * 根据地址id，删除对应的用户地址信息
     * @param addressId
     */
    Boolean deleteUserAddress(Integer addressId);

    /**
     * 修改默认地址
     * @param addressId
     */
    Boolean updateUserAddressToDefault(Integer userId, Integer addressId);

    /**
     * 根据地址id，查询具体的用户地址对象信息
     * @param addressId
     * @return
     */
    UserAddress queryUserAddressById(Integer addressId);
}
