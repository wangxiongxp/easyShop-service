package com.aliboy.service;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.model.dto.UserAddressDto;
import com.aliboy.model.entity.UserAddress;
import com.aliboy.model.vo.UserAddressVO;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-04 10:39
 */
public interface CollectService {
    /**
     * 查询我的收藏分页列表
     * @return
     */
    PagedGridResult queryCollectList(QueryParams queryParams);




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

}
