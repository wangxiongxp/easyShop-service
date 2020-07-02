package com.aliboy.api;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.security.PrincipalUtils;
import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.dto.UserAddressDto;
import com.aliboy.model.vo.UserAddressVO;
import com.aliboy.service.AddressService;
import com.aliboy.service.CollectService;
import com.aliboy.web.store.UserStoreManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${api}/collect")
@RestController
@Api(value = "我的收藏", tags = "200000--个人中心-我的收藏")
public class CollectController extends BaseController {

    @Autowired
    private CollectService collectService;

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "我的收藏列表", notes = "我的收藏列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "模糊查询参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "typeId", value = "类型 0商品", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "分页，默认第一页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数,默认10条", dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序的字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "asc", value = "排序方式", dataType = "boolean", paramType = "query", defaultValue = "true")
    })
    @GetMapping("/list")
    public ResultDto list(@RequestParam(required = false) String q,
                          @RequestParam(required = false, defaultValue = "") Integer typeId,
                          @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false, defaultValue = "add_time") String sort,
                          @RequestParam(required = false, defaultValue = "true") Boolean asc) {

        QueryParams queryParams = new QueryParams(pageSize, pageIndex, q, sort, asc);
        queryParams.putIfNotNull("typeId",typeId);
        queryParams.putIfNotNull("userId",PrincipalUtils.getUserIdInt());

        PagedGridResult grid = collectService.queryCollectList(queryParams);

        return getResultDto(grid);
    }



    @ApiOperation(value = "用户保存地址", notes = "用户保存地址", httpMethod = "POST")
    @PostMapping("/save")
    public ResultDto save(@RequestBody UserAddressDto userAddressDto) {

        userAddressDto.setCountry("CN");
        userAddressDto.setUserId(PrincipalUtils.getUserIdInt());
        addressService.addNewUserAddress(userAddressDto);

        return getResultDto();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public ResultDto update(@RequestBody UserAddressDto userAddressDto) {

        addressService.updateUserAddress(userAddressDto);

        return getResultDto();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public ResultDto delete(@RequestParam Integer id) {

        addressService.deleteUserAddress(id);
        return getResultDto();
    }
}
