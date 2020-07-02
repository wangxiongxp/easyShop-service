package com.aliboy.service.impl;

import com.aliboy.mapper.AreaMapper;
import com.aliboy.mapper.UserAddressMapper;
import com.aliboy.model.entity.Area;
import com.aliboy.model.entity.UserAddress;
import com.aliboy.model.dto.UserAddressDto;
import com.aliboy.model.vo.UserAddressVO;
import com.aliboy.service.AddressService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-04 10:41
 */

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<UserAddressVO> queryAllByUserId(Integer userId) {
        List<UserAddressVO> returnList = Lists.newArrayList();

        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        List<UserAddress> userAddressList = userAddressMapper.select(ua);
        userAddressList.forEach(item->{
            UserAddressVO vo = new UserAddressVO();
            BeanUtils.copyProperties(item, vo);
            //填充
            Area province = areaMapper.selectByPrimaryKey(item.getProvince());
            Area city = areaMapper.selectByPrimaryKey(item.getCity());
            Area distinct = areaMapper.selectByPrimaryKey(item.getDistrict());
            vo.setProvinceName(province.getName());
            vo.setCityName(city.getName());
            vo.setDistrictName(distinct.getName());

            returnList.add(vo);
        });
        return returnList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean addNewUserAddress(UserAddressDto userAddressDto) {

        // todo 如果前台传入默认地址，需要将其他默认地址清空，将该地址置为默认地址
        // 1. 判断当前用户是否存在地址，如果没有，则新增为‘默认地址’
        boolean isDefault = false;
        UserAddress ua = new UserAddress();
        ua.setUserId(userAddressDto.getUserId());
        List<UserAddress> addressList = userAddressMapper.select(ua);
        if (ObjectUtils.isEmpty(addressList)) {
            isDefault = true;
        }

        // 2. 保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressDto, newAddress);

        newAddress.setIsDefault(isDefault);

        userAddressMapper.insert(newAddress);

        return Boolean.TRUE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateUserAddress(UserAddressDto userAddressDto) {

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressDto, pendingAddress);

        pendingAddress.setId(userAddressDto.getId());
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);

        return Boolean.TRUE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean deleteUserAddress(Integer addressId) {

        UserAddress address = new UserAddress();
        address.setId(addressId);

        userAddressMapper.delete(address);
        return Boolean.TRUE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateUserAddressToDefault(Integer userId, Integer addressId) {

        // 1. 查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(true);
        List<UserAddress> list  = userAddressMapper.select(queryAddress);
        for (UserAddress ua : list) {
            ua.setIsDefault(false);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(true);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);

        return Boolean.TRUE;
    }

    @Override
    public UserAddress queryUserAddressById(Integer addressId) {

        UserAddress singleAddress = new UserAddress();
        singleAddress.setId(addressId);

        return userAddressMapper.selectOne(singleAddress);
    }

}
