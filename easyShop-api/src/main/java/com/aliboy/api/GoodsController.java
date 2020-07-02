package com.aliboy.api;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.entity.Category;
import com.aliboy.model.vo.GoodsInfoVO;
import com.aliboy.service.CategoryService;
import com.aliboy.service.GoodsService;
import com.aliboy.common.utils.PagedGridResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 14:59
 */
@RestController
@RequestMapping("${api}/goods")
@Api(value = "商品接口", tags = "160000--商品信息相关接口")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询上架商品数量
     */
    @ApiOperation(value = "查询上架商品数量", notes = "查询上架商品数量", httpMethod = "GET")
    @GetMapping("/count")
    public ResultDto count() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("goodsCount", goodsService.getActiveGoodsCount());
        return getResultDto(map);
    }

    /**
     * 查询商品分页列表
     */
    @ApiOperation(value = "查询商品分页列表", notes = "查询商品分页列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "模糊查询参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "brandId", value = "brandId", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "categoryId", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "分页，默认第一页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数,默认10条", dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序的字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "asc", value = "排序方式", dataType = "boolean", paramType = "query", defaultValue = "false")
    })
    @GetMapping("/list")
    public ResultDto list(@RequestParam(required = false) String q,
                          @RequestParam(required = false, defaultValue = "") Integer brandId,
                          @RequestParam(required = false, defaultValue = "") Integer categoryId,
                          @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false, defaultValue = "sort_order") String sort,
                          @RequestParam(required = false, defaultValue = "false") Boolean asc) {

        QueryParams queryParams = new QueryParams(pageSize, pageIndex, q, sort, asc);
        queryParams.putIfNotNull("brandId",brandId);
        queryParams.putIfNotNull("categoryId",categoryId);

        PagedGridResult grid = goodsService.queryGoodsList(queryParams);

        return getResultDto(grid);
    }

    /**
     * 商品页面查询分类信息
     */
    @ApiOperation(value = "商品页面查询分类信息", notes = "商品页面查询分类信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @GetMapping("/category")
    public ResultDto category(@RequestParam Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_NOT_EXIST);
        }

        Map<String, Object> map = Maps.newHashMap();
        Category currentCategory = categoryService.findCategoryById(id);
        map.put("currentCategory", currentCategory);

        Category parentCategory = categoryService.findCategoryById(currentCategory.getParentId());
        map.put("parentCategory", parentCategory);

        List<Category> brotherCategory = categoryService.querySubCatList(ObjectUtils.isEmpty(parentCategory)?0:parentCategory.getId());
        map.put("brotherCategory", brotherCategory);

        return getResultDto(map);
    }

    /**
     * 商品详情页面信息
     */
    @ApiOperation(value = "商品详情页面信息", notes = "商品详情页面信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @GetMapping("/detail")
    public ResultDto detail(@RequestParam Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_NOT_EXIST);
        }

        GoodsInfoVO goodsInfoVO = goodsService.findGoodsInfoById(id);

        return getResultDto(goodsInfoVO);
    }

    /**
     * 关联商品列表
     */
    @ApiOperation(value = "关联商品列表", notes = "关联商品列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @GetMapping("/related")
    public ResultDto related(@RequestParam Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_NOT_EXIST);
        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("goodsList", Lists.newArrayList());

        return getResultDto(map);
    }


}
