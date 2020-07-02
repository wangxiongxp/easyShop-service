package com.aliboy.api;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.entity.Brand;
import com.aliboy.service.BrandService;
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

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 14:59
 */
@RestController
@RequestMapping("${api}/brand")
@Api(value = "品牌接口", tags = "170000--品牌信息相关接口")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "查询品牌详情", notes = "查询品牌详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @GetMapping("/detail")
    public ResultDto detail(@RequestParam Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_NOT_EXIST);
        }

        Brand brand = brandService.findBrandById(id);
        return getResultDto(brand);
    }

}
