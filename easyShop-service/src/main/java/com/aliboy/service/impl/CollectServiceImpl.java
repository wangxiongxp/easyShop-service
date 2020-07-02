package com.aliboy.service.impl;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.mapper.CollectMapper;
import com.aliboy.mapper.GoodsMapper;
import com.aliboy.mapper.UserAddressMapper;
import com.aliboy.model.dto.UserAddressDto;
import com.aliboy.model.entity.Collect;
import com.aliboy.model.entity.Goods;
import com.aliboy.model.entity.UserAddress;
import com.aliboy.model.vo.CollectInfoVO;
import com.aliboy.service.CollectService;
import com.aliboy.service.impl.center.BaseService;
import com.github.pagehelper.PageHelper;
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
public class CollectServiceImpl extends BaseService implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public PagedGridResult queryCollectList(QueryParams queryParams) {
        List<CollectInfoVO> returnList = Lists.newArrayList();

        PageHelper.startPage(queryParams.getPageIndex(), queryParams.getPageSize());

        List<Collect> list = collectMapper.queryListPage(convertQueryParams(queryParams));

        PagedGridResult<Collect> pagedGridResult = setPagedGrid(list, queryParams.getPageIndex());
        pagedGridResult.getData().forEach(item->{
            CollectInfoVO vo = new CollectInfoVO();
            BeanUtils.copyProperties(item, vo);

            Goods goods = goodsMapper.selectByPrimaryKey(item.getValueId());

            vo.setName(goods.getName());
            vo.setGoodsBrief(goods.getGoodsBrief());
            vo.setListPicUrl(goods.getListPicUrl());
            vo.setRetailPrice(goods.getRetailPrice());
            returnList.add(vo);
        });

        return PagedGridResult.of(returnList,pagedGridResult.getPagination());
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

}
