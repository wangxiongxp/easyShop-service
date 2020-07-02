package com.aliboy.api;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.utils.PagedGridResult;
import com.aliboy.common.web.BaseController;
import com.aliboy.model.entity.Topic;
import com.aliboy.service.TopicService;
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

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 14:59
 */
@RestController
@RequestMapping("${api}/topic")
@Api(value = "专题", tags = "120000--专题信息相关接口")
public class TopicController extends BaseController {

    @Autowired
    private TopicService topicService;

    /**
     * 查询专题分页列表
     */
    @ApiOperation(value = "查询专题分页列表", notes = "查询专题分页列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "模糊查询参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "分页，默认第一页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数,默认10条", dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "排序的字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "asc", value = "排序方式", dataType = "boolean", paramType = "query", defaultValue = "true")
    })
    @GetMapping("/list")
    public ResultDto list(@RequestParam(required = false) String q,
                              @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false, defaultValue = "sort_order") String sort,
                              @RequestParam(required = false, defaultValue = "true") Boolean asc) {

        QueryParams queryParams = new QueryParams(pageSize, pageIndex, q, sort, asc);

        PagedGridResult grid = topicService.queryTopicList(queryParams);

        return getResultDto(grid);
    }

    @ApiOperation(value = "查询专题详情", notes = "查询专题详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @GetMapping("/detail")
    public ResultDto detail(@RequestParam Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_NOT_EXIST);
        }

        Topic topic = topicService.findById(id);
        return getResultDto(topic);
    }

    @ApiOperation(value = "查询推荐专题", notes = "查询推荐专题", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query")
    })
    @GetMapping("/related")
    public ResultDto related(@RequestParam Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            return getFailDto(ErrorCodes.ERROR_ARGUMENT_NOT_EXIST);
        }

        List<Topic> topicList = topicService.queryRelatedList(id);
        return getResultDto(topicList);
    }

}
