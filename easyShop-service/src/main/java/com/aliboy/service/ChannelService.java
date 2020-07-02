package com.aliboy.service;

import com.aliboy.model.entity.Channel;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface ChannelService {
    /**
     * 查询首页频道
     * @return
     */
    List<Channel> queryChannelList();

}
