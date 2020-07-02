package com.aliboy.service.impl;

import com.aliboy.mapper.ChannelMapper;
import com.aliboy.model.entity.Channel;
import com.aliboy.service.ChannelService;
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
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public List<Channel> queryChannelList() {

        Example example = new Example(Channel.class);
        // 排序使用列名
        example.setOrderByClause("sort_order asc");

        return channelMapper.selectByExample(example);
    }

}
