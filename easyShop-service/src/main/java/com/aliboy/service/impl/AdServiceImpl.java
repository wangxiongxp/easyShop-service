package com.aliboy.service.impl;

import com.aliboy.mapper.AdMapper;
import com.aliboy.model.entity.Ad;
import com.aliboy.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:29
 */

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;

    @Override
    public List<Ad> queryAdListByPosId(Integer posId) {
        Example example = new Example(Ad.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("positionId", posId);

        List<Ad> result =  adMapper.selectByExample(example);
        return result;
    }
}
