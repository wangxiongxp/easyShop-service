package com.aliboy.api;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.entity.Category;
import com.aliboy.model.vo.CategoryVO;
import com.aliboy.service.CategoryService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("${api}/catalog")
@Api(value = "分类", tags = "130000--商品分类信息相关接口")
public class CatalogController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类页面第一次获取分类列表，默认当前分类为列表第一个
     */
    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/index")
    public ResultDto index() {
        // 一级分类列表
        List<Category> list = categoryService.queryAllRootLevelCat();
        Map<String, Object> map = Maps.newHashMap();
        map.put("categoryList", list);

        CategoryVO categoryVO = new CategoryVO();
        if(!ObjectUtils.isEmpty(list)){
            // 默认第一个事当前分类
            BeanUtils.copyProperties(list.get(0),categoryVO);
            List<Category> subCatList = categoryService.querySubCatList(categoryVO.getId());
            categoryVO.setSubCategoryList(subCatList);
        }
        map.put("currentCategory", categoryVO);

        return getResultDto(map);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级分类id", dataType = "int", paramType = "query" )
    })
    @GetMapping("/current")
    public ResultDto current(@RequestParam Integer id) {

        if (id == null) {
            return getFailDto(ErrorCodes.ERROR_CATEGORY_NOT_EXIST);
        }

        Category category = categoryService.findCategoryById(id);
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        List<Category> subCatList = categoryService.querySubCatList(id);
        categoryVO.setSubCategoryList(subCatList);

        Map<String, Object> map = Maps.newHashMap();
        map.put("currentCategory", categoryVO);
        return getResultDto(map);
    }

}
