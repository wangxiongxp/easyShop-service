package com.aliboy.api;

import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.vo.CategoryItemVO;
import com.aliboy.model.dto.GoodsSimpleVO;
import com.aliboy.model.entity.Ad;
import com.aliboy.model.entity.Brand;
import com.aliboy.model.entity.Channel;
import com.aliboy.model.entity.Topic;
import com.aliboy.service.*;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api}/index")
@Api(value = "首页", tags = "110000--首页展示的相关接口")
public class IndexController extends BaseController {

    @Autowired
    private AdService adService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TopicService topicService;

    @ApiOperation(value = "获取首页数据", notes = "获取首页数据", httpMethod = "GET")
    @GetMapping("")
    public ResultDto index() {

        Map<String, Object> map = Maps.newHashMap();

        List<Ad> bannerList = adService.queryAdListByPosId(1);
        map.put("banner", bannerList);

        List<Channel> channelList = channelService.queryChannelList();
        map.put("channel", channelList);

        List<Brand> brandList = brandService.queryHomeBrandList();
        map.put("brandList", brandList);

        List<GoodsSimpleVO> newGoodsList = goodsService.queryNewGoodsList();
        map.put("newGoodsList", newGoodsList);

        List<GoodsSimpleVO> hotGoodsList = goodsService.queryHotGoodsList();
        map.put("hotGoodsList", hotGoodsList);

        List<Topic> topicList = topicService.queryHomeTopicList();
        map.put("topicList", topicList);

        List<CategoryItemVO> categoryList = categoryService.queryHomeCategoryItemList();
        map.put("categoryList", categoryList);

        return getResultDto(map);
    }

}
