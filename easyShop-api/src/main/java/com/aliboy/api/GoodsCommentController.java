package com.aliboy.api;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.web.BaseController;
import com.aliboy.service.GoodsCommentService;
import com.aliboy.common.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("${api}/comment")
@Api(value = "评论接口", tags = "180000--评论信息相关接口")
public class GoodsCommentController extends BaseController {

    @Autowired
    private GoodsCommentService goodsCommentService;

    /**
     * 查询评论分页列表
     */
    @ApiOperation(value = "查询评论分页列表", notes = "查询评论分页列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "模糊查询参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "typeId", value = "typeId", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "valueId", value = "valueId", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "分页，默认第一页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数,默认10条", dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序的字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "asc", value = "排序方式", dataType = "boolean", paramType = "query", defaultValue = "false")
    })
    @GetMapping("/list")
    public ResultDto list(@RequestParam(required = false) String q,
                              @RequestParam(required = false, defaultValue = "") Integer typeId,
                              @RequestParam(required = false, defaultValue = "") Integer valueId,
                              @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false, defaultValue = "add_time") String sort,
                              @RequestParam(required = false, defaultValue = "false") Boolean asc) {

        QueryParams queryParams = new QueryParams(pageSize, pageIndex, q, sort, asc);
        queryParams.putIfNotNull("typeId",typeId);
        queryParams.putIfNotNull("valueId",valueId);

        PagedGridResult grid = goodsCommentService.queryCommentList(queryParams);

        return getResultDto(grid);
    }

}
